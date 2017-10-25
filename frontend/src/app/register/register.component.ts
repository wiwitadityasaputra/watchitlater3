import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { NgForm } from '@angular/forms';

import { ServerService } from '../app-util/server.service';
import { UserModel } from '../app-util/server.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  @ViewChild('f') registerForm: NgForm;
  userModel: UserModel;

  constructor(private serverService: ServerService, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe((params: Params) => {
      this.userModel = new UserModel(params['emailAddress'], params['fullName'], params['password']);
      console.log(this.userModel);
    });
  }

  onSubmit() {
    console.log()
    // this.registerForm.controls['rePassword'].setErrors({});
  }

}
