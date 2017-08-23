import { CanActivate, Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';

import 'rxjs/add/operator/take';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/map';

import { AuthenticationService } from '../_services/authentication.service';

@Injectable()
export class AuthGuard implements  CanActivate {

  user: any;

  constructor(private auth: AuthenticationService, private router: Router) {

    this.user = this.auth.loginUser();
  }

  canActivate(): Observable<boolean> {

    const currentUser = this.user;

    return Observable.from( [currentUser] ).take(1).map(state => state != null).do(authenticated => {

      if (!authenticated) {
        // window.location.href = '/index';
      }
    });
  }

}
