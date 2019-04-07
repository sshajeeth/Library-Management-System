import { Component, OnInit } from '@angular/core';
import { Borrow } from '../borrow';
import { EnrollmentService } from '../enrollment.service';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-borrow-item',
  templateUrl: './borrow-item.component.html',
  styleUrls: ['./borrow-item.component.css']
})
export class BorrowItemComponent implements OnInit {
   
 

  borrowDetails = new Borrow('','');

  borrowNameAvailablity : String;
  borrowItemAvailablity : String;
  borrowForm : boolean;



  constructor(private _enrollmentService: EnrollmentService, private _activatedRoute:ActivatedRoute, private _router:Router) { }
  
  checkBorrowName(){
    this._enrollmentService.borrowNameCheck(this.borrowDetails.fullName).subscribe((borrowNameData: String) => {
      this.borrowNameAvailablity = borrowNameData;
      console.log(borrowNameData);
      if(borrowNameData !="please register"){
      if (borrowNameData != "can borrow") {
        alert(borrowNameData);
        location.reload();
      }else{
        return this.borrowForm=true;
      }
    }else{
      alert("Please Register First")
      this._router.navigate(['/register']);
    }
    });
  }

  onSubmit(){
    this._enrollmentService.borrowItemCheck(this.borrowDetails.isbn).subscribe((borrowItemData: String) => {
      this.borrowItemAvailablity = borrowItemData;
      console.log(borrowItemData);

      if (borrowItemData != "Item Already Borrowed!!!") {
        this._enrollmentService.enrollBorrow(this.borrowDetails)
        .subscribe( data=> console.log('Success!' ) )
        console.log(this.borrowDetails);
        alert("Hi "+this.borrowDetails.fullName+" "+this.borrowDetails.isbn+" "+ borrowItemData+ " Borrowed Successfully");
        location.reload();
       
      }else{
        alert(this.borrowDetails.isbn+ " is already borrowed\nPlease reserve your Item!");
        this._router.navigate(['/register']);
       
      }
    });

  }

  
 
  ngOnInit() {
    
  }
}

