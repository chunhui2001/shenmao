import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MdIconModule, MdToolbarModule, MdMenuModule, MdCardModule, MdButtonModule } from '@angular/material';

import { AuthenticationService } from './_services/authentication.service';
import { UserService } from './_services/user.service';


import { AppComponent } from './app.component';
import { LoginComponent } from './_components/login/login.component';
import { SignupComponent } from './_components/signup/signup.component';
import { LoginEmailComponent } from './_components/login-email/login-email.component';
import { MembersComponent } from './_components/members/members.component';

import { routers } from './routers/app.router';
import { IndexComponent } from './_components/index/index.component';

import { DateFormatPipe } from './_pipelines/dateformat.pipe';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    LoginEmailComponent,
    MembersComponent,
    IndexComponent,

    DateFormatPipe
  ],
  imports: [
    BrowserModule,
    HttpClientModule, FormsModule, BrowserAnimationsModule,
    MdIconModule, MdToolbarModule, MdMenuModule, MdCardModule, MdButtonModule,
    routers
  ],
  providers: [
    AuthenticationService, UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
