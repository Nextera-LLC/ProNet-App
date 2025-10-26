import { NgIf, NgForOf } from '@angular/common';
import { Component, HostListener, OnInit } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../services/user-service/user-service';
import { UserProfile } from '../../../services/user-profile-service/user-profile';
import { ProfileHeaderDto } from '../../../dto/profile-header-dto';

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
  userId = 1; // TODO: replace with actual user ID from auth/route
  photoSrc = '';

  // ProfileHeaderDto
  profileHeaderDto : any;

  constructor(private http: HttpClient, private userProfile: UserProfile) {
    // FIX: use /users not /user
    this.photoSrc = `http://localhost:8080/users/${this.userId}/profile-picture`;
  }

  ngOnInit(): void {
   this. profileHeaderDto = ProfileHeaderDto;
    this.userProfile.getProfileHeaderInfo(this.userId).subscribe(
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
      this.userProfile.deleteProfilePicture(this.userId).subscribe({
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
    if (!this.selectedFile || !this.userId) return;

    const formData = new FormData();
    formData.append('file', this.selectedFile);

    this.uploading = true;

    this.userProfile.uploadProfilePicture(this.userId, formData).subscribe({
      next: () => {
        this.uploading = false;
        this.closePhotoModal();
        // refresh to backend URL so future loads hit server image (bust preview)
        this.photoSrc = `http://localhost:8080/users/${this.userId}/profile-picture?ts=${Date.now()}`;
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

  saveProfileHeader() {
    
    this.userProfile.saveProfileHeaderInfo(this.userId, this.profileHeaderDto).subscribe(
      (response : ProfileHeaderDto)=>{
        this.profileHeaderDto = response;
        this.profileHeaderDto.headline = 'Software Engineer || Angular || Spring Boot || Postgres';
      },
      (error : HttpErrorResponse) =>{
      console.log(error.message)
      }
    );
    console.log(this.profileHeaderDto);
    this.closeEditModal();
    alert('Profile info saved.');
  }
}
