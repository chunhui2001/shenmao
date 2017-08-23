import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import 'rxjs/add/operator/map';



import { UserEntity } from '../_entities/user.entity';
import { RestResponse } from '../_entities/rest.response.entity';

@Injectable()
export class AuthenticationService {

  public token: string;
  public user: UserEntity;

  constructor(private http: HttpClient) {

  }

  login(username: string, password: string): Observable<String> {

    return this.http.post('/login', { username: username, password: password })
      .map((response: RestResponse) => {

          if (response.error) {
            return response.message[0];
          }

          this.user = response.data;

          // save user info to localStorage
          localStorage.setItem('user', JSON.stringify(this.user));
          return null;
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
