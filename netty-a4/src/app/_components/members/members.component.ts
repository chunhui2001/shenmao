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
  selector: 'app-members',
  templateUrl: './members.component.html',
  styleUrls: ['./members.component.css']
})
export class MembersComponent implements OnInit {

  userId: string;
  user: any;
  userList: any;

  dateFormatPipeFilter: any;

  constructor(private route: ActivatedRoute, private userService: UserService) {
    this.dateFormatPipeFilter = new DateFormatPipe();
  }

  ngOnInit() {

    this.route.queryParams.subscribe(params => {
       this.userId = params.id;
    });

    if (this.userId) {
      this.userService.find(this.userId).subscribe(data => {
        if (!data) { return; }
        this.user = data;
        this.user.createdAt = this.dateFormatPipeFilter.transform(this.user.createdAt, 'f1');
      });
      return;
    }

    this.userService.list().subscribe(data => {

      // Observable.from(data).subscribe(user => {
      //   console.log(user.userId);
      //   console.log(user.userName);
      //   console.log(user.photo);
      //   console.log(user.createdAt);
      // });

      this.userList = data;

    });
  }

  alert(_event) {
    alert($(_event.target).html());
  }

}
