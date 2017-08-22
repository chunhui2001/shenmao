import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/do';

import { UserService } from '../../_services/user.service';

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

  constructor(private route: ActivatedRoute, private userService: UserService) { }

  ngOnInit() {

    this.route.queryParams.subscribe(params => {
       this.userId = params.id;
    });

    if (this.userId) {
      this.userService.find(this.userId).subscribe(data => {
        this.user = data;
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

}
