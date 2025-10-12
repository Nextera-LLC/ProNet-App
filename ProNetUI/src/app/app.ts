import { Component, inject } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {

  constructor(private router: Router) {}
  
  isAuthRoute(): boolean {
    return ['/login', '/sign-up','/'].includes(this.router.url);
  }
}
