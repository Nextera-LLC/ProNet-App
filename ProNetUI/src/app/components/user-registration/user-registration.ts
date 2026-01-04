import { Component, inject, OnInit } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { PasswordModule } from 'primeng/password';
import { User } from '../../model/user';
import { UserService } from '../../services/user-service/user-service';
import { HttpErrorResponse } from '@angular/common/http';
import { RegisterRequest } from '../../dto/register-request';
import { Router } from '@angular/router';
import { Jwt } from '../../dto/jwt';

@Component({
  selector: 'app-user-registration',
  imports: [ ButtonModule, InputTextModule,FormsModule,ToastModule, MessageModule, PasswordModule],
  templateUrl: './user-registration.html',
  styleUrl: './user-registration.css'
})
export class UserRegistration implements OnInit{

  user : RegisterRequest= {
    firstName : '',
    lastName: '',
    email: '',
    password: '',
    confirmPassword: '',
    role: 'USER'
  };

  jwt : Jwt;
  doesPasswordMatch = true;
  registeredUser : User;
  users : User[];
  errorMessage: string = '';

  constructor(private userService : UserService, private router : Router){}


  ngOnInit(): void {
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
        this.userService.registerUser(this.user).subscribe({
          next: (response) => { 
            localStorage.setItem("JwtToken",response.token);
            this.router.navigate(['/home']);
            this.errorMessage = '';
           },
          error: err => {
            if (err.status === 409) {
              this.errorMessage = err.error.message;
            }
        }
        });
      

      }
   
  }




    }
}

