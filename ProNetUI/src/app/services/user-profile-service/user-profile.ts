import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environments';
import { ProfileHeaderDto } from '../../dto/profile-header-dto';

@Injectable({
  providedIn: 'root'
})
export class UserProfile {

  private BASE_URL  = environment.apiBaseUrl;

  constructor(private http : HttpClient) { }

  uploadProfilePicture(userId: number, formData : FormData): Observable<string> {
    return this.http.post<string>(`${this.BASE_URL}/users/${userId}/profile-picture`, formData, {
      responseType: 'text' as 'json'
    });
  }

  deleteProfilePicture(userId: number): Observable<string> {
    return this.http.delete<string>(`${this.BASE_URL}/users/${userId}/profile-picture`,{
      responseType: 'text' as 'json'
    });
  }

  getProfileHeaderInfo(userId : number) : Observable<any>{
    return this.http.get<any>(`${this.BASE_URL}/users/${userId}/profile-header`);
  }

  saveProfileHeaderInfo(userId : number, profileHeaderDto : ProfileHeaderDto) : Observable<ProfileHeaderDto>{
    return this.http.put<ProfileHeaderDto>(`${this.BASE_URL}/users/${userId}/profile-header`, profileHeaderDto);

  }

  saveProfileBio(userId : number, bio : string) : Observable<string>{
    return this.http.put<string>(`${this.BASE_URL}/users/${userId}/profile-bio`, bio,{
      responseType: 'text' as 'json'
    });
  
  }

  getProfileBio(userId : number) : Observable<string>{
    return this.http.get<string>(`${this.BASE_URL}/users/${userId}/profile-bio`,{
      responseType: 'text' as 'json'
    });
  }

}
