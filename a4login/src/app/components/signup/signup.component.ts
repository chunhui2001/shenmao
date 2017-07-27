import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

// import { moveIn } from '../../router.animations.ts';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
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
