import { Component, inject, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { PasswordModule } from 'primeng/password';
import { User } from '../../model/user';
import { UserService } from '../../services/user-service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-user-registration',
  imports: [ ButtonModule, InputTextModule,FormsModule,ToastModule, MessageModule, PasswordModule],
  templateUrl: './user-registration.html',
  styleUrl: './user-registration.css'
})
export class UserRegistration implements OnInit{

  user : User= {
    firstName : '',
    lastName: '',
    email: '',
    password: '',
    confirmPassword: '',
    role: 'USER'
  };

  doesPasswordMatch = true;
  registeredUser : User;
  users : User[];
  constructor(private userService : UserService){}


  ngOnInit(): void {
    this.userService.getUsers().subscribe(
      (response : User[]) =>{
        this.users = response;
        console.log(this.users);
      },
      (error : HttpErrorResponse)=>{
        console.error(error.message);
      }
    )
  }
  onSubmit(form: any) {
    if (form.valid) {
      //check if passwords match
        if(this.user.password !== this.user.confirmPassword){
          this.doesPasswordMatch =false;
        }
        else{
          this.doesPasswordMatch = true;

        // sending post request to register user
        this.userService.registerUser(this.user).subscribe(
          (response : User)=>{
            this.registeredUser = response;
            alert("Your account created successfully!")
          },
          (error : HttpErrorResponse) =>{
            alert(error.message);
          }
        )

      }
    
    


    }
}
}
