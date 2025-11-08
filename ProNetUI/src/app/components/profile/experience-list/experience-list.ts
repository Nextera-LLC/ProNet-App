import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Experience } from '../../../model/profile/experience';
import { ExperienceService } from '../../../services/user-profile-service/experience-service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-experience-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './experience-list.html',
  styleUrls: ['./experience-list.css']
})
export class ExperienceList implements OnInit {

  modalOpen = false;
  isEditMode = false;               // 👈 to know if we are adding or editing
  userId = 1;

  experiences: Experience[] = [];   // initialize
  model: Experience = this.createEmptyExperience();

  constructor(private experienceService: ExperienceService) {}

  @ViewChild('modalRoot') modalRoot?: ElementRef<HTMLDivElement>;
  @ViewChild('firstInput') firstInput?: ElementRef<HTMLInputElement>;

  ngOnInit(): void {
    this.loadExperiences();
  }

  private loadExperiences(): void {
    this.experienceService.getAllExperiences(this.userId).subscribe(
      (response: Experience[]) => {
        this.experiences = response;
        console.log('experiences', response);
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  private createEmptyExperience(): Experience {
    return {
      experienceId: null as any,   // or undefined depending on your model
      userId: this.userId,
      companyName: '',
      title: '',
      startDate: '',
      endDate: '',
      location: '',
      isCurrent: false,
      description: ''
    };
  }

  /* ========== ADD ========== */
  openAdd(): void {
    this.isEditMode = false;
    this.model = this.createEmptyExperience();
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
  openEdit(selectedExprience: Experience): void {
    this.isEditMode = true;
    // clone so we don't mutate list item before save
    this.model = { ...selectedExprience };
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

  onToggleCurrent(): void {
    if (!this.model) return;
    if (this.model.isCurrent) {
      this.model.endDate = '';
    }
  }

  canSave(): boolean {
    if (!this.model) return false;
    if (!this.model.companyName?.trim()) return false;
    if (!this.model.title?.trim()) return false;
    if (!this.model.startDate) return false;
    if (!this.model.isCurrent && this.model.endDate) {
      return this.model.endDate >= this.model.startDate;
    }
    return true;
  }

  /* ========== SAVE (ADD or UPDATE) ========== */
  saveExperience(): void {
    if (!this.canSave()) return;

    const payload: Experience = {
      experienceId: this.model.experienceId,
      userId: this.userId, // make sure userId is sent
      companyName: this.model.companyName.trim(),
      title: this.model.title.trim(),
      startDate: this.model.startDate,
      endDate: this.model.isCurrent ? null : (this.model.endDate || null),
      location: this.model.location?.trim() || null,
      isCurrent: this.model.isCurrent,
      description: this.model.description?.trim() || null
    };

    // EDIT
    if (this.isEditMode && payload.experienceId) {
      this.experienceService.updateExperience(payload, payload.experienceId).subscribe(
        (response: Experience) => {
          // update list locally or reload
          this.loadExperiences();
          this.cancelEdit();
        },
        (error: HttpErrorResponse) => {
          console.log(error.message);
        }
      );
    } else {
      // ADD
      this.experienceService.addExperience(payload).subscribe(
        (response: Experience) => {
          // push new item or reload
          this.loadExperiences();
          this.cancelEdit();
        },
        (error: HttpErrorResponse) => {
          console.log(error.message);
        }
      );
    }
  }

  /* ========== DELETE ========== */
  onDelete(): void {
    if (!this.model?.experienceId) {
      this.cancelEdit();
      return;
    }
    const canDelete = confirm('Are you sure you want to delete this experience?');
    if (canDelete) {
      this.experienceService.deleteExperience(this.model.experienceId).subscribe(
        () => {
          this.loadExperiences();
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
