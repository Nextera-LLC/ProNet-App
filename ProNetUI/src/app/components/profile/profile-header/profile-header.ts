import { NgIf, NgForOf } from '@angular/common';
import { Component, HostListener } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../services/user-service';

@Component({
  selector: 'app-profile-header',
  standalone: true,
  imports: [NgIf, FormsModule, NgForOf],
  templateUrl: './profile-header.html',
  styleUrl: './profile-header.css',
})
export class ProfileHeader {
  // Photo modal
  photoModalOpen = false;
  selectedFile: File | null = null;
  uploading = false;

  // Edit modal
  editModalOpen = false;

  // User / data
  userId = 1; // TODO: replace with actual user ID from auth/route
  photoSrc = '';
  // Basic info
  firstName = 'Umer';
  lastName = 'Abubeker';
  headline = 'Software Engineer · Angular • Spring Boot • Postgres';
  // Location
  country = 'United States';
  state = 'Minnesota';
  city = 'Minneapolis';
  // Contact
  phone = '';
  email = '';
  linkedin = '';

  constructor(private http: HttpClient, private userService: UserService) {
    // FIX: use /users not /user
    this.photoSrc = `http://localhost:8080/users/${this.userId}/profile-picture`;
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
      this.userService.deleteProfilePicture(this.userId).subscribe({
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

    this.userService.uploadProfilePicture(this.userId, formData).subscribe({
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
    // TODO: call your backend to persist these fields
    // Example payload:
    // const payload = {
    //   firstName: this.firstName,
    //   lastName: this.lastName,
    //   headline: this.headline,
    //   location: { country: this.country, state: this.state, city: this.city },
    //   contact: { phone: this.phone, email: this.email, linkedin: this.linkedin }
    // };
    // this.userService.updateProfileHeader(this.userId, payload).subscribe(...);

    this.closeEditModal();
    alert('Profile info saved (stub). Wire this to your backend next.');
  }
}
