import { Component, OnInit } from '@angular/core';
import { Register } from '../register';
import { EnrollmentService } from '../enrollment.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerDetails = new Register('', '', '');
  registerName: String;
  registerForm: boolean;

  constructor(private _enrollmentService: EnrollmentService,private _router:Router) { }

  checkName() {
    this._enrollmentService.registerNameCheck(this.registerDetails.fullName).subscribe((registerNameData: String) => {
      this.registerName = registerNameData;
      console.log(registerNameData);

      if (registerNameData != "can register") {
        alert("You are already registered!!");
        location.reload();
      } else {
        this.registerForm = true;
      }
    });
  }
  onSubmit() {
    this._enrollmentService.enrollRegister(this.registerDetails)
      .subscribe(data => console.log('Success!', data))
    console.log(this.registerDetails);
    alert("Hi " + this.registerDetails.fullName + ",\nWelcome to Westminster Library!!!!")
    this._router.navigate(['/borrowItem']);


  }



  ngOnInit() {

  }



}
