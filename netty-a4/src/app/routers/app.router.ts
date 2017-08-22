import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';

// components
import { LoginComponent } from '../_components/login/login.component';
import { LoginEmailComponent } from '../_components/login-email/login-email.component';
import { SignupComponent } from '../_components/signup/signup.component';
import { MembersComponent } from '../_components/members/members.component';
import { IndexComponent } from '../_components/index/index.component';

// services
// import { AuthGurad } from '../services/auth.guard.service';

export const router: Routes = [
  { path: '', redirectTo: 'index', pathMatch: 'full' },
  { path: 'c/login', component: LoginComponent },
  { path: 'c/signup', component: SignupComponent },
  { path: 'c/login-email', component: LoginEmailComponent },
  // { path: 'members', component: MembersComponent, canActivate: [AuthGuard] },
  { path: '_c/members', component: MembersComponent },



  { path: '404', component: IndexComponent },
  { path: 'index', component: IndexComponent },
  { path: 'members', component: MembersComponent },
  { path: 'login', component: LoginComponent },
  { path: 'registry', component: SignupComponent },
  { path: 'login-email', component: LoginEmailComponent },
]

export const routers: ModuleWithProviders = RouterModule.forRoot(router);
