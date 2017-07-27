import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

// import { moveIn, fallIn, moveInLeft } from '../../router.animations.ts';

@Component({
  selector: 'app-members',
  templateUrl: './members.component.html',
  styleUrls: ['./members.component.css']
  // animations: [ moveIn(), fallIn(), moveInLeft() ],
  // host: { '[@moveIn]': '' }
})
export class MembersComponent implements OnInit {

  state: String = '';
  name: any;

  constructor( private router: Router) {  }

  logOut() {
    console.log('member component logOut clicked');
    this.router.navigateByUrl('/login');
  }
  ngOnInit() {
  }

}
