import { Component, OnInit } from '@angular/core';
import { DVD } from '../dvd';
import { EnrollmentService } from '../enrollment.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-dvd',
  templateUrl: './add-dvd.component.html',
  styleUrls: ['./add-dvd.component.css']
})
export class AddDVDComponent implements OnInit {

  public dvdSpace: any;
  public dvdMessage: any;
  dvdAvailablity: String;

  sectors = ['Horror', 'Adventure', 'Fiction', 'Health', 'Fiction', 'Travel', 'Romance', 'Education'];
  sectorHasError: boolean = true;
  validateSector(value) {
    if (value == 'defaultSector') {
      this.sectorHasError = true;
    } else {
      this.sectorHasError = false;
    }

  }
  dvdDetails = new DVD('', '', '', '', '', 'defaultSector', '', '');

  constructor(private _enrollmentService: EnrollmentService,private _router:Router) { }

  checkDVDAvailablity() {
    this._enrollmentService.checkDVDAvailablity(this.dvdDetails.isbn).subscribe((dvdData: String) => {
      this.dvdAvailablity = dvdData;
      console.log(dvdData);
      if (dvdData != "ok") {
        alert(dvdData);
        location.reload();
      }
    });
  }

  addDVDForm() {
    if (this.dvdAvailablity == "ok") {
      if (this.dvdSpace <= 50) {
        //alert("You have " + this.dvdSpace + "Spaces to store dvd in library")
        return true;
      } else {
        alert("No Spaces Available for Books in Library!!")
      }


    }
  }

  onSubmit() {
    this._enrollmentService.enrollDVD(this.dvdDetails)
      .subscribe(data => console.log('Success!'))
    console.log(this.dvdDetails);
    alert(this.dvdDetails.title + " Added to library successfully!!");
    location.reload();
  }

  ngOnInit() {
    this.enrollDVDSpace();

  }
  enrollDVDSpace(): any {
    this._enrollmentService.enrollDVDSpace().subscribe((data: any) => {
      this.dvdSpace = data;
      console.log(data);
    });
  }


}
