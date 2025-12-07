import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environments';
import { HttpClient } from '@angular/common/http';
import { UserSkill } from '../../model/profile/user-skill';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SkillService {

 
  // You can move the base URL into environments if you like
  private BASE_URL  = environment.apiBaseUrl;


  constructor(private http: HttpClient) {}

  /* ========== GET ALL BY USER ID ========== */
  getAllUserSkills(userId: number): Observable<UserSkill[]> {
    return this.http.get<UserSkill[]>(`${this.BASE_URL}/api/users/${userId}/skills`);
  }

  /* ========== GET ONE BY ID ========== */
  getUserSkillById(userSkillId: number, userId : number): Observable<UserSkill> {
    return this.http.get<UserSkill>(`${this.BASE_URL}/api/users/${userId}/skills/${userSkillId}`);
  }

  /* ========== ADD (CREATE) ========== */
  addUserSkill(userSkill: UserSkill, userId : number): Observable<UserSkill> {
    return this.http.post<UserSkill>(`${this.BASE_URL}/api/users/${userId}/skills`, userSkill);
  }

  /* ========== UPDATE ========== */
  updateUserSkill(userSkill: UserSkill, userId: number): Observable<UserSkill> {
    return this.http.put<UserSkill>(`${this.BASE_URL}/api/users/${userId}/skills/${userSkill.userSkillId}`, userSkill);
  }

  /* ========== DELETE ========== */
  deleteUserSkill( userSkill : UserSkill,userId: number): Observable<void> {
    return this.http.delete<void>(`${this.BASE_URL}/api/users/${userId}/skills/${userSkill.userSkillId}`);
  }
}
