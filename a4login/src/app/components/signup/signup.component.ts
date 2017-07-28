import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import {fallIn, moveIn} from '../../routers/router.animations';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
  animations: [ moveIn(), fallIn() ],
  host: { '[@moveIn]': '' }
})
export class SignupComponent implements OnInit {

  state: String = '';
  error: any;

  constructor( private router: Router ) { }

  onSubmit(formData) {

    if (!formData.valid) {
      return;
    }

    console.log('sign up component submit clicked');

  }

  ngOnInit() {
  }

}
