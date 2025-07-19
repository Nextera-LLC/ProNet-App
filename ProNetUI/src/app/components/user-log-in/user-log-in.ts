import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { Password } from 'primeng/password';
import { UserAuth } from '../../model/user-auth';
import { LoginRequest } from '../../dto/login-request';
import { UserService } from '../../services/user-service';
import { HttpErrorResponse } from '@angular/common/http';
import { Jwt } from '../../dto/jwt';

@Component({
  selector: 'app-user-log-in',
  imports: [
    ButtonModule,
    InputTextModule,
    FormsModule,
    ToastModule,
    MessageModule,
  ],
  templateUrl: './user-log-in.html',
  styleUrl: './user-log-in.css',
})
export class UserLogIn {
  
  jwt : Jwt;

  userCredential : LoginRequest = {
    email : '',
    password : ''
  }

  constructor(private userService : UserService){}

  onSubmit(form: any) {
    if (form.valid) {
      this.userService.logInUser(this.userCredential).subscribe(
        (response : any)=>{
          this.jwt = response;
          localStorage.setItem("JwtToken",this.jwt.token);
          
          alert("Log in successfully!")
        },
        (error : HttpErrorResponse) =>{
          alert(error.message);
        }
      )
    }
  }
}
