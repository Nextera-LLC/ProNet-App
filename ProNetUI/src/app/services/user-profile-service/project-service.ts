import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environments';
import { Project } from '../../model/profile/project';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  // You can move the base URL into environments if you like
  private BASE_URL  = environment.apiBaseUrl;


  constructor(private http: HttpClient) {}

  /* ========== GET ALL BY USER ========== */
  getAllProjects(userId: number): Observable<Project[]> {
    return this.http.get<Project[]>(`${this.BASE_URL}/users/projects?userId=${userId}`);
  }

  /* ========== GET ONE BY ID ========== */
  getProjectById(projectId: number): Observable<Project> {
    return this.http.get<Project>(`${this.BASE_URL}/users/projects/${projectId}`);
  }

  /* ========== ADD (CREATE) ========== */
  addProject(project: Project): Observable<Project> {
    return this.http.post<Project>(`${this.BASE_URL}/users/projects`, project);
  }

  /* ========== UPDATE ========== */
  updateProject(project: Project, projectId: number): Observable<Project> {
    return this.http.put<Project>(`${this.BASE_URL}/users/projects/${projectId}`, project);
  }

  /* ========== DELETE ========== */
  deleteProject(projectId: number): Observable<void> {
    return this.http.delete<void>(`${this.BASE_URL}/users/projects/${projectId}`);
  }
}
