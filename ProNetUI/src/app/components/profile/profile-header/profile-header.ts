import { NgIf } from '@angular/common';
import { Component, HostListener, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../../../services/user-service';
import { ConfirmDialog } from 'primeng/confirmdialog';

@Component({
  selector: 'app-profile-header',
  standalone: true,
  imports: [NgIf],
  templateUrl: './profile-header.html',
  styleUrl: './profile-header.css',
})
export class ProfileHeader{
  photoModalOpen = false;
  selectedFile: File | null = null;
  uploading = false;
  userId = 1; // Replace with actual user ID
  photoSrc = '';

  constructor(private http: HttpClient, private userService : UserService) {
    this.photoSrc = `http://localhost:8080/user/${this.userId}/profile-picture`;
  }


  openPhotoModal() {
    this.photoModalOpen = true;
    document.body.style.overflow = 'hidden';
  }

  closePhotoModal() {
    this.photoModalOpen = false;
    document.body.style.overflow = '';
    this.selectedFile = null;
  }

  @HostListener('document:keydown', ['$event'])
  handleEsc(e: KeyboardEvent) {
    if (e.key === 'Escape' && this.photoModalOpen) this.closePhotoModal();
  }

  onDelete() {
  
   if(confirm("Are You sure you want to delete your profile image?")){
    this.userService.deleteProfilePicture(this.userId)
    .subscribe({
      next: () => {
        this.useDefalutPhoto();
        this.selectedFile = null;       
        this.closePhotoModal();
        alert('Photo deleted successfully');
      },
      error: err => {
        console.error(err);
        alert('Delete failed');
      }
    });
  }
  }

  onSave() {
    if (!this.selectedFile || !this.userId) return;

    const formData = new FormData();
    formData.append('file', this.selectedFile);

    this.uploading = true;

    this.userService.uploadProfilePicture(this.userId, formData)
      .subscribe({
        next: () => {
          this.uploading = false;
          this.closePhotoModal();
          alert('Photo uploaded successfully');
        },
        error: err => {
          this.uploading = false;
          console.error(err);
          alert('Upload failed');
        }
      });
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files || input.files.length === 0) return;

    this.selectedFile = input.files[0];

    const reader = new FileReader();
    reader.onload = () => {
      this.photoSrc = reader.result as string;
    };
    reader.readAsDataURL(this.selectedFile);
  }

  triggerFileInput(inputEl: HTMLInputElement) {
    inputEl.click();
  }

  useDefalutPhoto(){
    this.photoSrc = 'assets/images/default-avatar.jpg';
  }
}
