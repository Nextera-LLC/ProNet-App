import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { Password } from 'primeng/password';
import { UserAuth } from '../../classes/user-auth';

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
  
  userCredential : UserAuth = {
    email : '',
    password : ''
  }
  onSubmit(form: any) {
    if (form.valid) {
      // this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Form Submitted', life: 3000 });
      // form.resetForm()
    }
  }
}
