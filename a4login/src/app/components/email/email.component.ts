import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

// import { moveIn } from '../../router.animations.ts';

@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css']
  // animations: [ moveIn(), fallIn() ],
  // host: { '[@moveIn]': '' }
})
export class EmailComponent implements OnInit {

  state: String = '';
  error: any;

  constructor( private router: Router ) { }

  onSubmit(formData) {

    if (!formData.valid) {
      return;
    }

    console.log('email component submit clicked');

  }

  ngOnInit() {
  }

}
