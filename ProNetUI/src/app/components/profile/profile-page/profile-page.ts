import { Component, signal } from '@angular/core';
import { NgFor, NgIf } from '@angular/common';
import { CardModule } from 'primeng/card';
import { DividerModule } from 'primeng/divider';
import { ButtonModule } from 'primeng/button';
import { AvatarModule } from 'primeng/avatar';
import { ChipModule } from 'primeng/chip';
import { TagModule } from 'primeng/tag';
import { ProfileHeader } from '../profile-header/profile-header';
import { AboutCard } from '../about-card/about-card';
import { ExperienceList } from '../experience-list/experience-list';
import { EducationList } from '../education-list/education-list';
import { ProjectList } from '../project-list/project-list';
import { SkillsList } from '../skills-list/skills-list';

@Component({
  selector: 'app-profile',
  imports: [ProfileHeader, AboutCard, ExperienceList, EducationList, ProjectList, SkillsList],
  templateUrl: './profile-page.html',
  styleUrl: './profile-page.css'
})
export class Profile {
 
}


