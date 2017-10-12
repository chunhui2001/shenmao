import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { moveIn, fallIn } from '../../routers/router.animations';

import { AuthenticationService } from '../../_services/_index';

@Component({
  selector: 'app-email',
  templateUrl: './login-email.component.html',
  styleUrls: ['./login-email.component.css'],
  animations: [ moveIn(), fallIn() ],
  // host: { '[@moveIn]': '' }
})
export class LoginEmailComponent implements OnInit {

  state: String = '';
  error: any;

  constructor( private auth: AuthenticationService, private router: Router ) {

  }

  onSubmit(formData) {

    if (!formData.valid) {
      return;
    }

    this.auth.login(formData.controls.email._value, formData.controls.password._value ).subscribe(
      response => {

        if (response.error) {
          this.error = response.message[0];
          return;
        }

        window.location.href = response.message.length > 0 ? response.message[0] : 'index';

      }
    );

  }

  ngOnInit() {
  }

}
