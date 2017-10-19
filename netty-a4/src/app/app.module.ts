import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MdIconModule, MdToolbarModule, MdMenuModule, MdCardModule, MdButtonModule } from '@angular/material';

// services
import { AuthGuard, AuthenticationService, UserService } from './_services/_index';
// import { AuthenticationService, UserService } from './_services/_index';

// directives
import { UserInfoCardDirective } from './_directives/_index';

// components
import { AppComponent } from './app.component';
import { LoginComponent, SignupComponent,
         LoginEmailComponent, MembersComponent,
         BillsComponent, IndexComponent } from './_components/_index';


import { routers } from './routers/app.router';

import { DateFormatPipe } from './_pipelines/_index';

@NgModule({
  declarations: [

    UserInfoCardDirective,

    AppComponent,
    LoginComponent,
    SignupComponent,
    LoginEmailComponent,
    MembersComponent,
    IndexComponent,
    BillsComponent,

    DateFormatPipe
  ],
  imports: [
    BrowserModule,
    HttpClientModule, FormsModule, BrowserAnimationsModule,
    MdIconModule, MdToolbarModule, MdMenuModule, MdCardModule, MdButtonModule,
    routers
  ],
  providers: [
    AuthGuard,
    AuthenticationService, UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
