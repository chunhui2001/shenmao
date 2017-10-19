import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/do';

declare var jquery: any;
declare var $: any;

import { DateFormatPipe } from '../../_pipelines/_index';
import { UserService } from '../../_services/_index';

import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-bills',
  template: `<h1>Bills List</h1>`,
  // styleUrls: ['./members.component.css']
})
export class BillsComponent implements OnInit {

  userId: string;
  user: any;
  userList: any;

  dateFormatPipeFilter: any;

  constructor() {

  }

  ngOnInit() {

  }


}
