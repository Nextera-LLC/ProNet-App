import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

import { Education } from '../../../model/profile/education';
import { EducationService } from '../../../services/user-profile-service/education-service';
import { User } from '../../../model/user';
import { UserService } from '../../../services/user-service/user-service';

@Component({
  selector: 'app-education-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './education-list.html',
  styleUrls: ['./education-list.css']
})
export class EducationList implements OnInit {

  modalOpen = false;
  isEditMode = false;
  currentUser : User;

  educations: Education[] = [];
  model: Education = this.createEmptyEducation();

  constructor(private educationService: EducationService,
              private userService : UserService
  ) {}

  @ViewChild('modalRoot') modalRoot?: ElementRef<HTMLDivElement>;
  @ViewChild('firstInput') firstInput?: ElementRef<HTMLInputElement>;

  ngOnInit(): void {
    this.getCurrentUser();
  }

  /* ===== Get Logged in user ===== */
  getCurrentUser(){
    this.userService.getCurrentUser().subscribe({
      next : (loggedInUser) =>{
        this.currentUser = loggedInUser;
        this.loadEducations(this.currentUser.userId);
      },
      error : (err) =>{
        console.log(err.message);
      }
    })
  }

  /* ========== LOAD LIST ========== */
  private loadEducations(userId : number): void {
    this.educationService.getAllEducations(userId).subscribe(
      (response: Education[]) => {
        this.educations = response;
        console.log('educations', response);
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  /* ========== EMPTY MODEL ========== */
  private createEmptyEducation(): Education {
    return {
      educationId: null as any,    // or undefined depending on your model
      userId: this.currentUser?.userId,
      institution: '',
      degree: '',
      fieldOfStudy: '',
      startDate: '',
      endDate: '',
      grade: '',
      description: ''
    };
  }

  /* ========== ADD ========== */
  openAdd(): void {
    this.isEditMode = false;
    this.model = this.createEmptyEducation();
    this.modalOpen = true;

    // lock scroll
    document.documentElement.style.overflow = 'hidden';
    document.body.style.overflow = 'hidden';

    // focus
    setTimeout(() => {
      this.modalRoot?.nativeElement?.focus();
      this.firstInput?.nativeElement?.focus();
    }, 0);
  }

  /* ========== EDIT ========== */
  openEdit(selectedEducation: Education): void {
    this.isEditMode = true;
    this.model = { ...selectedEducation };  // shallow clone
    this.modalOpen = true;

    // lock scroll
    document.documentElement.style.overflow = 'hidden';
    document.body.style.overflow = 'hidden';

    setTimeout(() => {
      this.modalRoot?.nativeElement?.focus();
      this.firstInput?.nativeElement?.focus();
    }, 0);
  }

  /* ========== CLOSE ========== */
  cancelEdit(): void {
    this.modalOpen = false;

    // unlock scroll
    document.documentElement.style.overflow = '';
    document.body.style.overflow = '';
  }

  onBackdropClick(e: MouseEvent): void {
    if ((e.target as HTMLElement).classList.contains('modal-backdrop')) {
      this.cancelEdit();
    }
  }

  /* ========== VALIDATION ========== */
  canSaveEducation(): boolean {
    if (!this.model) return false;
    if (!this.model.institution?.trim()) return false;

    // optional date ordering check
    if (this.model.startDate && this.model.endDate) {
      return this.model.endDate >= this.model.startDate;
    }
    return true;
  }

  /* ========== SAVE (ADD or UPDATE) ========== */
  saveEducation(): void {
    if (!this.canSaveEducation()) return;

    const payload: Education = {
      educationId: this.model.educationId,
      userId: this.currentUser?.userId,
      institution: this.model.institution.trim(),
      degree: this.model.degree?.trim() || '',
      fieldOfStudy: this.model.fieldOfStudy?.trim() || '',
      startDate: this.model.startDate || null,
      endDate: this.model.endDate || null,
      grade: this.model.grade?.trim() || '',
      description: this.model.description?.trim() || ''
    };

    if (this.isEditMode && payload.educationId) {
      // UPDATE
      this.educationService.updateEducation(payload, payload.educationId).subscribe(
        (response: Education) => {
          this.loadEducations(this.currentUser.userId);
          this.cancelEdit();
          alert("Education updated!")
        },
        (error: HttpErrorResponse) => {
          console.log(error.message);
        }
      );
    } else {
      // ADD
      this.educationService.addEducation(payload).subscribe(
        (response: Education) => {
          this.loadEducations(this.currentUser.userId);
          this.cancelEdit();
          alert("Education added!")
        },
        (error: HttpErrorResponse) => {
          console.log(error.message);
        }
      );
    }
  }

  /* ========== DELETE ========== */
  onDelete(): void {
    if (!this.model?.educationId) {
      this.cancelEdit();
      return;
    }

    const canDelete = confirm('Are you sure you want to delete this education entry?');
    if (canDelete) {
      this.educationService.deleteEducation(this.model.educationId).subscribe(
        () => {
          this.loadEducations(this.currentUser.userId);
          this.cancelEdit();
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
          this.cancelEdit();
        }
      );
    } else {
      this.cancelEdit();
    }
  }
}
