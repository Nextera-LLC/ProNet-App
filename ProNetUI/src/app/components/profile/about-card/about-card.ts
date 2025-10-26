import { Component, ElementRef, ViewChild, AfterViewInit, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UserService } from '../../../services/user-service/user-service';
import { User } from '../../../model/user';
import { HttpErrorResponse } from '@angular/common/http';
import { UserProfile } from '../../../services/user-profile-service/user-profile';

@Component({
  selector: 'app-about-card',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './about-card.html',
  styleUrls: ['./about-card.css']
})
export class AboutCard implements AfterViewInit, OnInit {
  isExpanded = false;

  aboutText = '';

  editVisible = false;
  tempAbout= '';
  userId = 1;

  @ViewChild('aboutTextarea') aboutTextarea?: ElementRef<HTMLTextAreaElement>;
  @ViewChild('modalRoot') modalRoot?: ElementRef<HTMLDivElement>;

  constructor(private userService : UserService, private userProfileService : UserProfile){}

  ngOnInit(): void {
      this.userProfileService.getProfileBio(this.userId).subscribe(
        (response : string)=>{
          console.log(response);
          this.aboutText = response;
        },
        (error : HttpErrorResponse)=>{
          alert(error.message);
        }
      )
  }

  ngAfterViewInit(): void {
    // no-op; focus handled on open
  }

  openEdit() {
    this.tempAbout = this.aboutText;
    this.editVisible = true;

    // Wait for modal to render, then focus textarea
    setTimeout(() => {
      this.modalRoot?.nativeElement?.focus();
      this.aboutTextarea?.nativeElement?.focus();
    }, 0);
  }

  cancelEdit() {
    this.editVisible = false;
  }

  saveAbout() {
    // if (!this.canSave()) return;
    this.aboutText = this.tempAbout.trim();
    this.userProfileService.saveProfileBio(1, this.aboutText).subscribe(
      {
        next: (response) => {
          this.aboutText = response;
        alert("Bio is updated")
        },
        error: (err) => {
          alert(err.message);

        },
      });
    this.editVisible = false;
  }

  // canSave(): boolean {
  //   const v = (this.tempAbout ?? '').trim();
  //   return v.length >= 10 && v.length <= 1000;
  // }

  // Close only when clicking outside the modal panel
  backdropClick(event: MouseEvent) {
    const target = event.target as HTMLElement;
    if (target.classList.contains('modal-backdrop')) {
      this.cancelEdit();
    }
  }
}
