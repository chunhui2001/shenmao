import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { moveIn, fallIn } from '../../routers/router.animations';

@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css'],
  animations: [ moveIn(), fallIn() ],
  host: { '[@moveIn]': '' }
})
export class EmailComponent implements OnInit {

  state: String = '';
  error: any;

  constructor( private router: Router ) {

  }

  onSubmit(formData) {

    if (!formData.valid) {
      return;
    }

    if (formData.controls.email._value !== 'chunhui2001@gmail.com') {
      this.error = 'Invalid email formatter';
      return;
    }

    this.router.navigateByUrl('/members');

  }

  ngOnInit() {
  }

}
