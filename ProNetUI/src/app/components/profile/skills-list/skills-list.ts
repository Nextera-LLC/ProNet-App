// src/app/skills-list/skills-list.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserSkill } from '../../../model/profile/user-skill';
import { SkillService } from '../../../services/user-profile-service/skill-service';


@Component({
  selector: 'app-skills-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './skills-list.html',
  styleUrls: ['./skills-list.css']
})
export class SkillsList {
  // TODO: replace with real logged-in user ID
  userId: number = 1;

  skills: UserSkill[] = [];
  primarySkills: UserSkill[] = [];
  moreSkills: UserSkill[] = [];

  // Modal state
  showModal = false;
  modalTitle = 'Add skill';
  isEditMode = false;

  // Form fields bound with [(ngModel)]
  currentSkillName = '';
  currentSkillLevel: UserSkill['level'] = '';
  currentSkillYears: number | null = null;

  // Currently editing skill
  editingSkill: UserSkill | null = null;

  constructor(private skillService: SkillService) {
    this.loadSkills();
  }

  get totalSkillsCount(): number {
    return this.skills.length;
  }

  /* ========== LOAD SKILLS ========== */
  private loadSkills(): void {
    this.skillService.getAllUserSkills(this.userId).subscribe({
      next: (skills) => {
        this.skills = skills || [];
        this.splitSkills();
      },
      error: (err) => {
        console.error('Error loading skills', err);
      }
    });
  }

  // First 4 as primary, rest as "more"
  private splitSkills(): void {
    this.primarySkills = this.skills.slice(0, 4);
    this.moreSkills = this.skills.slice(4);
  }

  /* ========== MODAL HANDLERS ========== */
  openAddModal(): void {
    this.showModal = true;
    this.isEditMode = false;
    this.modalTitle = 'Add skill';
    this.editingSkill = null;

    this.currentSkillName = '';
    this.currentSkillLevel = '';
    this.currentSkillYears = null;
  }

  openEditModal(skill: UserSkill, index: number, listType: 'primary' | 'more'): void {
    this.showModal = true;
    this.isEditMode = true;
    this.modalTitle = 'Edit skill';
    this.editingSkill = skill;

    this.currentSkillName = skill.name;
    this.currentSkillLevel = skill.level;
    this.currentSkillYears =
      skill.yearsExperience !== undefined && skill.yearsExperience !== null
        ? skill.yearsExperience
        : null;
  }

  closeModal(): void {
    this.showModal = false;
  }

  /* ========== SAVE (ADD / UPDATE) ========== */
  saveSkill(): void {
    const payload: UserSkill = {
      ...(this.editingSkill || {}),
      name: this.currentSkillName.trim(),
      level: this.currentSkillLevel,
      yearsExperience: this.currentSkillYears
    };

    if (!this.isEditMode) {
      // CREATE
      this.skillService.addUserSkill(payload, this.userId).subscribe({
        next: (created) => {
          this.skills.push(created);
          this.splitSkills();
          this.closeModal();
        },
        error: (err) => {
          console.error('Error creating skill', err);
        }
      });
    } else if (this.editingSkill) {
      // UPDATE
      this.skillService.updateUserSkill(payload, this.userId).subscribe({
        next: (updated) => {
          const idx = this.skills.findIndex(
            (s) => s.userSkillId === updated.userSkillId
          );
          if (idx !== -1) {
            this.skills[idx] = updated;
          }
          this.splitSkills();
          this.closeModal();
        },
        error: (err) => {
          console.error('Error updating skill', err);
        }
      });
    }
  }

  /* ========== DELETE ========== */
  deleteSkill(): void {
    if (!this.editingSkill) return;

    this.skillService.deleteUserSkill(this.editingSkill, this.userId).subscribe({
      next: () => {
        this.skills = this.skills.filter(
          (s) => s.userSkillId !== this.editingSkill!.userSkillId
        );
        this.splitSkills();
        this.closeModal();
      },
      error: (err) => {
        console.error('Error deleting skill', err);
      }
    });
  }
}
