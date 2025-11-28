import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

import { Project } from '../../../model/profile/project';
import { ProjectService } from '../../../services/user-profile-service/project-service';

@Component({
  selector: 'app-project-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './project-list.html',
  styleUrls: ['./project-list.css']
})
export class ProjectList implements OnInit {

  modalOpen = false;
  isEditMode = false;
  userId = 1;

  projects: Project[] = [];
  model: Project = this.createEmptyProject();

  constructor(private projectService: ProjectService) {}

  @ViewChild('modalRoot') modalRoot?: ElementRef<HTMLDivElement>;
  @ViewChild('firstInput') firstInput?: ElementRef<HTMLInputElement>;

  ngOnInit(): void {
    this.loadProjects();
  }

  /* ========== LOAD LIST ========== */
  private loadProjects(): void {
    this.projectService.getAllProjects(this.userId).subscribe(
      (res: Project[]) => {
        this.projects = res;
        console.log('projects', res);
      },
      (err: HttpErrorResponse) => {
        console.log(err.message);
      }
    );
  }

  /* ========== EMPTY MODEL ========== */
  private createEmptyProject(): Project {
    return {
      projectId: null as any,   // or undefined depending on your model
      userId: this.userId,
      title: '',
      description: '',
      url: '',
      startDate: '',
      endDate: '',
      createdAt: undefined,
      updatedAt: undefined
    };
  }

  /* ========== OPEN MODAL (ADD) ========== */
  openAdd(): void {
    this.isEditMode = false;
    this.model = this.createEmptyProject();
    this.openModal();
  }

  /* ========== OPEN MODAL (EDIT) ========== */
  openEdit(selectedProject: Project): void {
    this.isEditMode = true;
    this.model = { ...selectedProject }; // clone
    this.openModal();
  }

  private openModal(): void {
    this.modalOpen = true;

    // lock background scroll
    document.documentElement.style.overflow = 'hidden';
    document.body.style.overflow = 'hidden';

    // focus modal + first input
    setTimeout(() => {
      this.modalRoot?.nativeElement?.focus();
      this.firstInput?.nativeElement?.focus();
    }, 0);
  }

  /* ========== CLOSE MODAL ========== */
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
  canSaveProject(): boolean {
    if (!this.model) return false;
    if (!this.model.title?.trim()) return false;

    if (this.model.startDate && this.model.endDate) {
      return this.model.endDate >= this.model.startDate;
    }
    return true;
  }

  /* ========== SAVE (ADD or UPDATE) ========== */
  saveProject(): void {
    if (!this.canSaveProject()) return;

    const payload: Project = {
      projectId: this.model.projectId,
      userId: this.userId,
      title: this.model.title.trim(),
      description: this.model.description?.trim() || '',
      url: this.model.url?.trim() || '',
      startDate: this.model.startDate || null,
      endDate: this.model.endDate || null,
      createdAt: this.model.createdAt,
      updatedAt: this.model.updatedAt
    };

    if (this.isEditMode && payload.projectId) {
      // UPDATE
      this.projectService.updateProject(payload, payload.projectId).subscribe(
        () => {
          this.loadProjects();
          this.cancelEdit();
          alert("Project updated!")

        },
        (err: HttpErrorResponse) => {
          console.log(err.message);
        }
      );
    } else {
      // ADD
      this.projectService.addProject(payload).subscribe(
        () => {
          this.loadProjects();
          this.cancelEdit();
          alert("Project added!")
        },
        (err: HttpErrorResponse) => {
          console.log(err.message);
        }
      );
    }
  }

  /* ========== DELETE ========== */
  onDelete(): void {
    if (!this.model?.projectId) {
      this.cancelEdit();
      return;
    }

    const canDelete = confirm('Are you sure you want to delete this project?');
    if (canDelete) {
      this.projectService.deleteProject(this.model.projectId).subscribe(
        () => {
          this.loadProjects();
          this.cancelEdit();
        },
        (err: HttpErrorResponse) => {
          alert(err.message);
          this.cancelEdit();
        }
      );
    } else {
      this.cancelEdit();
    }
  }
}
