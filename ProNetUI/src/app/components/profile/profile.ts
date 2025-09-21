import { Component, signal } from '@angular/core';
import { NgFor, NgIf } from '@angular/common';
import { CardModule } from 'primeng/card';
import { DividerModule } from 'primeng/divider';
import { ButtonModule } from 'primeng/button';
import { AvatarModule } from 'primeng/avatar';
import { ChipModule } from 'primeng/chip';
import { TagModule } from 'primeng/tag';

interface ExperienceItem {
  title: string; company: string; location?: string; start: string; end?: string; bullets?: string[];
}
interface EducationItem { school: string; degree?: string; start?: string; end?: string; }
interface ProjectItem { name: string; summary?: string; linkLabel?: string; linkUrl?: string; tech?: string[]; }
interface ProfileModel {
  id: string; name: string; headline: string; location?: string; avatarUrl?: string; about?: string;
  experiences: ExperienceItem[]; education: EducationItem[]; projects: ProjectItem[]; skills: string[];
}

@Component({
  selector: 'app-profile',
  imports: [],
  templateUrl: './profile.html',
  styleUrl: './profile.css'
})
export class Profile {
  isExpanded = false;
  aboutText = `Full‑stack engineer building ProNet — a professional networking platform.
  I specialize in Angular, Spring Boot, PostgreSQL, and JWT. Clean architecture and user-first UI.
  Full‑stack engineer building ProNet — a professional networking platform.
  I specialize in Angular, Spring Boot, PostgreSQL, and JWT. Clean architecture and user-first UI.
  Full‑stack engineer building ProNet — a professional networking platform.
  I specialize in Angular, Spring Boot, PostgreSQL, and JWT. Clean architecture and user-first UI.
  Full‑stack engineer building ProNet — a professional networking platform.
  I specialize in Angular, Spring Boot, PostgreSQL, and JWT. Clean architecture and user-first UI.`;
  
}


