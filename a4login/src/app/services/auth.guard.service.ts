import { CanActivate, Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';

import 'rxjs/add/operator/take';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/map';

@Injectable()
export class AuthGurad implements  CanActivate {

  constructor(private router: Router) {

  }

  canActivate(): Observable<boolean> {

    return Observable.from( [false] ).take(1).map(state => !!state).do(authenticated => {

      if (!authenticated) {
        this.router.navigate(['/login']);
      }
    });
  }

}
