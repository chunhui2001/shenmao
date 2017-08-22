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

interface UserEntity {
  userId: string,
  userName: string,
  userRoles: any,
  photo: string;
  createdAt: Date;
  gender: string;
  lastUpdated: Date;
  lastLogin: Date;
}

@Injectable()
export class UserService {

  constructor(private http: HttpClient) {

  }

  list(): Observable<UserEntity[]> {

    return this.http.get('/members.json')
      .map((response: RestResponse) => {

        if (response.error) {
          return response.message[0];
        }

        return response.data;
      });

  }

  find(userId): Observable<UserEntity[]> {

    return this.http.get('/members.json?id=' + userId)
      .map((response: RestResponse) => {

        if (response.error) {
          return response.message[0];
        }

        return response.data;
      });

  }

}
