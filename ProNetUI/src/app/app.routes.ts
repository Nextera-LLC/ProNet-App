import { Routes } from '@angular/router';
import { UserLogIn } from './components/user-log-in/user-log-in';
import { UserRegistration } from './components/user-registration/user-registration';
import { HomePage } from './components/home-page/home-page';


export const routes: Routes = [
    {path:"", component:UserLogIn},
    {path: "login", component: UserLogIn},
    {path: "sign-up", component: UserRegistration},
    {path: "home", component: HomePage}

];
