import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import 'rxjs/add/operator/map';

import { UserEntity, RestResponse } from '../_entities/_index';

@Injectable()
export class BillService {

  constructor(private http: HttpClient) {

  }

  list(): Observable<UserEntity[]> {

    return this.http.get('/bills.json')
      .map((response: RestResponse) => {

        if (response.error) {
          return response.message[0];
        }

        return response.data;
      });

  }


}
