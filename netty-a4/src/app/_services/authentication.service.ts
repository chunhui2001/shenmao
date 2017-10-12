import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import 'rxjs/add/operator/map';



import { UserEntity, RestResponse } from '../_entities/_index';

@Injectable()
export class AuthenticationService {

  public token: string;
  public user: UserEntity;

  constructor(private http: HttpClient) {

  }

  login(username: string, password: string): Observable<RestResponse> {

    return this.http.post('/login.json', { username: username, password: password })
      .map((response: RestResponse) => {
debugger;
          if (response.error) {
            return response;
          }

          this.user = response.data;

          // save user info to localStorage
          localStorage.setItem('user', JSON.stringify(this.user));
          return response;
      });

  }

  loginUser(): UserEntity {
    // get user info from localStorage
    this.user = JSON.parse(localStorage.getItem('user'));
    return this.user;
  }

  logout(): void {
    this.token = null;
    // localStorage.removeItem('currentUser');
  }

}
