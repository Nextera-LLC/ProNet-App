import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environments';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PostModal } from '../../model/posts/post-modal';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  
  private BASE_URL  = environment.apiBaseUrl;;

  constructor(private http: HttpClient) {}

/* ========== ADD (CREATE) ========== */
addPost(post : PostModal, userId : number) : Observable<PostModal> {
  return this.http.post<PostModal>(`${this.BASE_URL}/api/users/${userId}/post`, post);
}

/* ========== GET (ALL POSTS) ========== */
getAllPosts() : Observable<PostModal[]> {
  return this.http.get<PostModal[]>(`${this.BASE_URL}/api/users/posts`);
}

}
