import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

// import { moveIn } from '../../routers/router.animations';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  // animations: [ moveIn() ],
  // host: { '[@moveIn]': '' }
})

export class LoginComponent implements OnInit {

  error: any;

  constructor( private router: Router ) { }

  loginWithGoogle() {
    console.log('loginWithGoogle clicked');
  }

  loginWithFacebook() {
    console.log('loginWithFacebook clicked');
  }

  loginWithTwitter() {
    console.log('loginWithTwitter clicked');
  }

  loginWithEmail() {
    console.log('loginWithEmail clicked');
  }

  ngOnInit() {
  }

}
