import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user-service/user-service';
import { User } from '../../model/user';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-home-page',
  imports: [],
  templateUrl: './home-page.html',
  styleUrl: './home-page.css'
})
export class HomePage implements OnInit{

  currentUser : User;

  constructor(private userService : UserService){}
  
  ngOnInit(): void {
   this.userService.getCurrentUser().subscribe(
    (response : User)=>{
      this.currentUser = response;
      console.log(this.currentUser)
    },
    (error : HttpErrorResponse) =>{
    console.log(error.message)
    }
   )
  }

}
