import { Injectable } from '@angular/core';
import { environment } from '../../environments/environments';
import { HttpClient } from '@angular/common/http';
import { User} from '../model/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private BASE_URL  = environment.apiBaseUrl;

  constructor(private http : HttpClient) { }


  getUsers() : Observable<User[]>{
    return this.http.get<User[]>(`${this.BASE_URL}/user/all`);
  } 
}
