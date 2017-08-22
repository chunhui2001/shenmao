import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import 'rxjs/add/operator/map';


interface RestResponse {
  error: boolean,
  code: string,
  message: any,
  data: any
}


@Injectable()
export class AuthenticationService {

  public token: string;

  constructor(private http: HttpClient) {

  }

  login(username: string, password: string): Observable<String> {

    return this.http.post('/login', { username: username, password: password })
      .map((response: RestResponse) => {

          if (response.error) {
            return response.message[0];
          }

          return null;
      });

  }

  logout(): void {
    this.token = null;
    // localStorage.removeItem('currentUser');
  }

}
