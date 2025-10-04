import { Injectable } from '@angular/core';
import { environment } from '../../environments/environments';
import { HttpClient} from '@angular/common/http';
import { User} from '../model/user';
import { Observable } from 'rxjs';
import { RegisterRequest } from '../dto/register-request';
import { LoginRequest } from '../dto/login-request';
import { Jwt } from '../dto/jwt';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private BASE_URL  = environment.apiBaseUrl;

  constructor(private http : HttpClient) { }


  getUsers() : Observable<User[]>{
    return this.http.get<User[]>(`${this.BASE_URL}/user/all`);
  } 

  registerUser(user : RegisterRequest) : Observable<User>{
    return this.http.post<User>(`${this.BASE_URL}/auth/register`,user);
  }

  logInUser(userCredential : LoginRequest) : Observable<Jwt>{
    return this.http.post<Jwt>(`${this.BASE_URL}/auth/login`,userCredential);
  }

  getCurrentUser() : Observable<User>{
    return this.http.get<User>(`${this.BASE_URL}/user/current`)
  }

  uploadProfilePicture(userId: number, formData : FormData): Observable<string> {
    return this.http.post<string>(`${this.BASE_URL}/user/${userId}/profile-picture`, formData, {
      responseType: 'text' as 'json'
    });
  }

  deleteProfilePicture(userId: number): Observable<string> {
    return this.http.delete<string>(`${this.BASE_URL}/user/${userId}/profile-picture`,{
      responseType: 'text' as 'json'
    });
  }

}
