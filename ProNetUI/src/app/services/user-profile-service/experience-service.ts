import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environments';
import { HttpClient } from '@angular/common/http';
import { Experience } from '../../model/profile/experience';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExperienceService {

  private BASE_URL  = environment.apiBaseUrl;

  constructor(private http : HttpClient) { }

  getAllExperiences(userId : number) : Observable<Experience[]>{
    return this.http.get<Experience[]>(`${this.BASE_URL}/users/experiences?userId=${userId}`);
  } 

  updateExperience(updatedxperience : Experience, experienceId : number) : Observable<Experience>{
    return this.http.put<Experience>(`${this.BASE_URL}/users/experiences/${experienceId}`,updatedxperience);
  }

  deleteExperience(experienceId : number) : Observable<void>{
    return this.http.delete<void>(`${this.BASE_URL}/users/experiences/${experienceId}`);
  }

  addExperience(newExperience : Experience) : Observable<Experience>{
    return this.http.post<Experience>(`${this.BASE_URL}/users/experiences`, newExperience);
  }
}
