import { Routes } from '@angular/router';
import { UserLogIn } from './components/user-log-in/user-log-in';
import { UserRegistration } from './components/user-registration/user-registration';
import { HomePage } from './components/home-page/home-page';
import { Profile } from './components/profile/profile-page/profile-page';
import { authGuard } from './auth.guard';


export const routes: Routes = [
    { path: 'login', component: UserLogIn },
    { path: "sign-up", component: UserRegistration },
  
    { path: '',
    canActivate: [authGuard],
    children: [
    {path: "home", component: HomePage},
    {path: "profile", component: Profile}
    ]
    },
    { path: '', redirectTo: 'login', pathMatch: 'full' }



];
