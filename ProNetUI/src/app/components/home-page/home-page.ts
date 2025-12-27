import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user-service/user-service';
import { User } from '../../model/user';
import { HttpErrorResponse } from '@angular/common/http';
import { Post } from '../posts/post/post';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home-page',
  imports: [Post],
  templateUrl: './home-page.html',
  styleUrl: './home-page.css'
})
export class HomePage implements OnInit{

  currentUser : User;
  profilePic : string;

  constructor(private userService : UserService, private router : Router){}
  
  ngOnInit(): void {
   this.userService.getCurrentUser().subscribe(
    (response : User)=>{
      this.currentUser = response;
this.profilePic = `http://localhost:8080/users/${this.currentUser.userId}/profile-picture`;
    },
    (error : HttpErrorResponse) =>{
    }
   )
  }

  handleLogOut(){
    console.log('before => ' + localStorage.getItem('JwtToken'))
    // remove jwt token 
    localStorage.removeItem('JwtToken');
    console.log('gdyeetdeee');

    // redirect to login page
    this.router.navigate(['/login']);
    }

    useDefaultPhoto(){
      this.profilePic = 'assets/images/default-avatar.jpg';
    }
}
