import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';

// components
import { LoginComponent } from '../components/login/login.component';
import { EmailComponent } from '../components/email/email.component';
import { SignupComponent } from '../components/signup/signup.component';
import { MembersComponent } from '../components/members/members.component';

// services
import { AuthGurad } from '../services/auth.guard.service';

export const router: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'login-email', component: EmailComponent },
  { path: 'members', component: MembersComponent }
]

export const routers: ModuleWithProviders = RouterModule.forRoot(router);
