import { Component, OnInit } from '@angular/core';
import { Book } from '../book';
import { EnrollmentService } from '../enrollment.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.css']
})
export class AddBookComponent implements OnInit {

  sectors = ['Adventure', 'Horror', 'Health', 'Fiction', 'Travel', 'Romance', 'Education'];
  sectorHasError: boolean = true;
  bookAvailablity: String;

  bookDetails = new Book('', '', '', '', '', 0, 'defaultSector');


  validateSector(value) {
    if (value == 'defaultSector') {
      this.sectorHasError = true;
    } else {
      this.sectorHasError = false;
    }

  }

  public bookSpace: any;
  public bookMessage: any;

  constructor(private _enrollmentService: EnrollmentService,private _router:Router) { }

  onSubmit() {
    this._enrollmentService.enrollBook(this.bookDetails)
      .subscribe(data => console.log('Success!'))
    console.log(this.bookDetails);
    alert(this.bookDetails.title + " successfully added to library!!");
    location.reload();
  }

  checkAvailablity() {
    this._enrollmentService.checkAvailablity(this.bookDetails.isbn).subscribe((data: String) => {
      this.bookAvailablity = data;
      console.log(data);
      if (data != "ok") {
        alert(data);
        location.reload();
      }
    });
  }

  addBookForm() {
    if (this.bookAvailablity == "ok") {
      if (this.bookSpace <= 100) {

        //alert("You have "  + " spaces available to store books in library!!")
        return true;

      } else {

        alert("No Spaces Available for Books in Library!!")
      }


    }
  }

  ngOnInit() {
    this.enrollBookSpace();

  }
  enrollBookSpace(): any {
    this._enrollmentService.enrollBookSpace().subscribe((data: any) => {
      this.bookSpace = data;
      console.log(data);

    });

  }



}

