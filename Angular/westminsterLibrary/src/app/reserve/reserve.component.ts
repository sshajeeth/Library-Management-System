import { Component, OnInit } from '@angular/core';
import { EnrollmentService } from '../enrollment.service';
import { Reserve } from '../reserve';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reserve',
  templateUrl: './reserve.component.html',
  styleUrls: ['./reserve.component.css']
})

export class ReserveComponent implements OnInit {
  reserve: String;

reserveDetails = new Reserve('');
  constructor(private _enrollmentService: EnrollmentService,private _router:Router) { }
  onSubmit() {
    this._enrollmentService.enrollreserve(this.reserveDetails.isbn).subscribe((reserveData: String) => {
      this.reserve = reserveData;
      console.log(reserveData);
      if (reserveData != "success") {
        alert("Invalid ISBN Number");
        location.reload();
      } else {
        alert(reserveData);
        this._router.navigate(['/borrowItem']);
      }
    });
  }

  ngOnInit() {
  }

}
