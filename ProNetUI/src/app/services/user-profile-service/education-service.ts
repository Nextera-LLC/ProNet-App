import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Education } from '../../model/profile/education';
import { environment } from '../../../environments/environments';

@Injectable({
  providedIn: 'root'
})
export class EducationService {


  private BASE_URL  = environment.apiBaseUrl;;

  constructor(private http: HttpClient) {}

  /* ========== GET ALL BY USER ========== */
  getAllEducations(userId: number): Observable<Education[]> {
    return this.http.get<Education[]>(`${this.BASE_URL}/users/educations?userId=${userId}`);
  }

  /* ========== GET ONE BY ID ========== */
  getEducationById(educationId: number): Observable<Education> {
    return this.http.get<Education>(`${this.BASE_URL}/users/educations/${educationId}`);
  }

  /* ========== ADD (CREATE) ========== */
  addEducation(education: Education): Observable<Education> {
    // backend will ignore educationId or treat null as new
    return this.http.post<Education>(`${this.BASE_URL}/users/educations`, education);
  }

  /* ========== UPDATE ========== */
  updateEducation(education: Education, educationId: number): Observable<Education> {
    return this.http.put<Education>(`${this.BASE_URL}/users/educations/${educationId}`, education);
  }

  /* ========== DELETE ========== */
  deleteEducation(educationId: number): Observable<void> {
    return this.http.delete<void>(`${this.BASE_URL}/users/educations/${educationId}`);
  }
}