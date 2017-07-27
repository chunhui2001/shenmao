import { Component, OnInit, HostBinding } from '@angular/core';

import { AngularFireModule } from 'angularfire2';
import { Router } from '@angular/router';
import { firebase } from '../firebase';
// import { moveIn } from '../router.animations';


import { AuthService } from '../auth';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  // animations: [ moveIn()],
  host: { '[@moveIn]': '' }
})
export class LoginComponent implements OnInit {


  constructor(private auth: AuthService, private router: Router) {

  }

  loginGoogle(): firebase.Promise<any> {
    return this.auth.signIn(new firebase.auth.GoogleAuthProvider());
  }

  ngOnInit() {

  }

}
