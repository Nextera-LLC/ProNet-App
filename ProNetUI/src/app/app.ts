import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { Password } from 'primeng/password';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ButtonModule, InputTextModule,FormsModule,ToastModule, MessageModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {

  messageService = inject(MessageService);

    user = {
        email: '',
        Password: ''
    };

    onSubmit(form: any) {
        if (form.valid) {
            this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Form Submitted', life: 3000 });
            form.resetForm()
        }
    }
}
