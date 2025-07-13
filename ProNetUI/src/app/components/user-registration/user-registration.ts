import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { PasswordModule } from 'primeng/password';
import { UserRegister } from '../../classes/user-register';

@Component({
  selector: 'app-user-registration',
  imports: [ ButtonModule, InputTextModule,FormsModule,ToastModule, MessageModule, PasswordModule],
  templateUrl: './user-registration.html',
  styleUrl: './user-registration.css'
})
export class UserRegistration {

  user : UserRegister= {
    fullName : '',
    email: '',
    password: '',
    confirmPassword: '',
    role: 'user'
  };

  onSubmit(form: any) {
    if (form.valid) {
        // this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Form Submitted', life: 3000 });
        if(this.user.password !== this.user.confirmPassword){
          console.log("not thesame")
        }
        // form.resetForm()
    }
}
}
