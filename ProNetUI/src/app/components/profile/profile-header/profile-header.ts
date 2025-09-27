import { NgIf } from '@angular/common';
import { Component, HostListener } from '@angular/core';

@Component({
  selector: 'app-profile-header',
  imports: [NgIf],
  templateUrl: './profile-header.html',
  styleUrl: './profile-header.css',
})
export class ProfileHeader {
  photoModalOpen = false;
  photoSrc = 'assets/images/profile-pic.jpg';

  openPhotoModal() {
    this.photoModalOpen = true;
    document.body.style.overflow = 'hidden'; // prevent background scroll
  }
  closePhotoModal() {
    this.photoModalOpen = false;
    document.body.style.overflow = '';
  }

  @HostListener('document:keydown', ['$event'])
  handleEsc(e: KeyboardEvent) {
    if (e.key === 'Escape' && this.photoModalOpen) this.closePhotoModal();
  }

  onDelete() {
    /* TODO: delete logic */
  }
  onSave() {
    /* TODO: persist changes */
  }

  /* TODO: open file picker or emit event */
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files || input.files.length === 0) return;

    const file = input.files[0];
    const reader = new FileReader();

    reader.onload = () => {
      // Update preview image (base64)
      this.photoSrc = reader.result as string;

      // Optionally store the file for later upload
      // this.selectedFile = file;
    };

    reader.readAsDataURL(file);
  }
}
