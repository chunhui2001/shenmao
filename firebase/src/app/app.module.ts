import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';


// components
import { AppComponent } from './app.component';
import { AppHeaderComponent } from './app-header.component';

// modules
import { AuthModule } from './auth';
import { FirebaseModule } from './firebase';
import { TasksModule } from './tasks';

import { LoginComponent } from './login/login.component';


@NgModule({
  declarations: [
    AppComponent,
    AppHeaderComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot([], {useHash: false}),
    FormsModule,
    HttpModule,

    AuthModule,
    FirebaseModule,
    TasksModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
