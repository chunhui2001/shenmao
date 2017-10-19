import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/do';

declare var jquery: any;
declare var $: any;

import { DateFormatPipe } from '../../_pipelines/_index';
import { BillService } from '../../_services/_index';

import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-bills',
  // template: `<h1>Bills List </h1>`,
  templateUrl: './bills.component.html',
  styleUrls: ['./bills.component.css']
})
export class BillsComponent implements OnInit {

  userId: string;
  user: any;
  billList: any;

  dateFormatPipeFilter: any;

  constructor(private billService: BillService) {

  }

  ngOnInit() {

    this.billService.list().subscribe(data => {

      // Observable.from(data).subscribe(user => {
      //   console.log(user.userId);
      //   console.log(user.userName);
      //   console.log(user.photo);
      //   console.log(user.createdAt);
      // });

      this.billList = data;

    });

  }


}
