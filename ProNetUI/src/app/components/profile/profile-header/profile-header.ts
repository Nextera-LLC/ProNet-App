import { NgIf, NgForOf } from '@angular/common';
import { Component, HostListener, OnInit } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../services/user-service/user-service';
import { UserProfile } from '../../../services/user-profile-service/user-profile';
import { ProfileHeaderDto } from '../../../dto/profile-header-dto';
import { User } from '../../../model/user';

@Component({
  selector: 'app-profile-header',
  standalone: true,
  imports: [NgIf, FormsModule, NgForOf],
  templateUrl: './profile-header.html',
  styleUrl: './profile-header.css',
})
export class ProfileHeader implements OnInit{
  // Photo modal
  photoModalOpen = false;
  selectedFile: File | null = null;
  uploading = false;

  // Edit modal
  editModalOpen = false;

  // User / data
  photoSrc = '';

  // ProfileHeaderDto
  profileHeaderDto : ProfileHeaderDto;

  // current logged in user
  currentUser : User;

  constructor(private http: HttpClient, private userProfile: UserProfile,
              private userService : UserService
  ) {
  }

  ngOnInit(): void {
  this.getCurrentUser();
  }

  /* ===== Get Logged in user ===== */

  getCurrentUser(){
    this.userService.getCurrentUser().subscribe({
      next : (loggedInUser) =>{
        this.currentUser = loggedInUser;

        // getting user's profile header info
        this.getProfileHeaderInfo(this.currentUser.userId);

        // getting user profile pic
        this.photoSrc = `http://localhost:8080/users/${this.currentUser.userId}/profile-picture`;
      },
      error : (err) =>{
        console.log(err.message);
      }
    })
  }
  /* ===== Get user's profile header info ===== */

getProfileHeaderInfo(userId : number){
  this.userProfile.getProfileHeaderInfo(userId).subscribe(
    (response : ProfileHeaderDto)=>{
      this.profileHeaderDto = response;
      console.log(this.profileHeaderDto);
      // this.profileHeaderDto.headline = 'Software Engineer || Angular || Spring Boot || Postgres';
    },
    (error : HttpErrorResponse) =>{
    console.log(error.message)
    }
  )
}

  /* ===== Photo modal ===== */
  openPhotoModal() {
    this.photoModalOpen = true;
    document.body.style.overflow = 'hidden';
  }
  closePhotoModal() {
    this.photoModalOpen = false;
    document.body.style.overflow = '';
    // Do not clear preview here; allow user to re-open and see current
  }

  @HostListener('document:keydown', ['$event'])
  handleEsc(e: KeyboardEvent) {
    if (e.key === 'Escape') {
      if (this.photoModalOpen) this.closePhotoModal();
      if (this.editModalOpen) this.closeEditModal();
    }
  }

  onDelete() {
    if (confirm('Are you sure you want to delete your profile image?')) {
      this.userProfile.deleteProfilePicture(this.currentUser.userId).subscribe({
        next: () => {
          this.useDefaultPhoto();
          this.selectedFile = null;
          this.closePhotoModal();
          alert('Photo deleted successfully');
        },
        error: (err) => {
          console.error(err);
          alert('Delete failed');
        },
      });
    }
  }

  onSave() {
    if (!this.selectedFile || !this.currentUser.userId) return;

    const formData = new FormData();
    formData.append('file', this.selectedFile);

    this.uploading = true;

    this.userProfile.uploadProfilePicture(this.currentUser.userId, formData).subscribe({
      next: () => {
        this.uploading = false;
        this.closePhotoModal();
        // refresh to backend URL so future loads hit server image (bust preview)
        this.photoSrc = `http://localhost:8080/users/${this.currentUser.userId}/profile-picture?ts=${Date.now()}`;
        alert('Photo uploaded successfully');
      },
      error: (err) => {
        this.uploading = false;
        console.error(err);
        alert('Upload failed');
      },
    });
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files || input.files.length === 0) return;

    this.selectedFile = input.files[0];

    // Preview selected image
    const reader = new FileReader();
    reader.onload = () => {
      this.photoSrc = reader.result as string;
    };
    reader.readAsDataURL(this.selectedFile);
  }

  triggerFileInput(inputEl: HTMLInputElement) {
    inputEl.click();
  }

  useDefaultPhoto() {
    this.photoSrc = 'assets/images/default-avatar.jpg';
  }

  /* ===== Edit profile modal ===== */
  openEditModal() {
    this.editModalOpen = true;
    document.body.style.overflow = 'hidden';
  }
  closeEditModal() {
    this.editModalOpen = false;
    document.body.style.overflow = '';
  }

   /* ========== VALIDATION ========== */
   canSaveEducation(): boolean {
    const dto = this.profileHeaderDto;

    const firstName = dto?.firstName?.trim() ?? '';
    const lastName  = dto?.lastName?.trim() ?? '';
    const country   = dto?.location?.country?.trim() ?? '';
    const email     = dto?.contact?.email?.trim() ?? '';

    return !!(firstName && lastName && country && email);
  }

  saveProfileHeader(){

    if(!this.canSaveEducation) return;
    this.userProfile.saveProfileHeaderInfo(this.currentUser.userId, this.profileHeaderDto).subscribe(
      (response : ProfileHeaderDto)=>{
        this.profileHeaderDto = response;
        console.log("Profile info saved");
            this.closeEditModal();
            alert('Profile info saved.');
      },
      (error : HttpErrorResponse) =>{
                  alert(error.message);

      }
    );

  }
}
