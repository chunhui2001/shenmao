import { CanActivate, Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';

import 'rxjs/add/operator/take';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/map';

import { AuthenticationService } from '../_services/_index';

@Injectable()
export class AuthGuard implements  CanActivate {

  user: any;

  // constructor(private auth: AuthenticationService, private router: Router) {
  //   debugger;
  //   this.user = this.auth.loginUser();
  // }



  // constructor( private router: Router) {
  //
  //   // this.user = this.auth.loginUser();
  // }

  /*canActivate(): Observable<boolean> {

    const currentUser = this.user;
debugger;
    return Observable.from( [1] ).take(1).map(state => state != null).do(authenticated => {

      if (!authenticated) {
        // window.location.href = '/index';
      }
    });

  }*/

  constructor() {

  }

  // canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot)
  canActivate(): Observable<boolean> | Promise<boolean> | boolean {

    return true;

  }


}
