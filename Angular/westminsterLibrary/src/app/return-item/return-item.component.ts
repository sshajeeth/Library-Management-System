import { Component, OnInit } from '@angular/core';
import { Return } from '../return'
import { EnrollmentService } from '../enrollment.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-return-item',
  templateUrl: './return-item.component.html',
  styleUrls: ['./return-item.component.css']
})
export class ReturnItemComponent implements OnInit {
  returnIsbn:String;
  ngOnInit(): void {
   
  }
  returnDetails = new Return('');

  constructor(private _enrollmentService: EnrollmentService,private _router:Router) { }

  onSubmit() {
    this._enrollmentService.return(this.returnDetails.isbn).subscribe((returnData: String) => {
      this.returnIsbn = returnData;
      console.log(returnData);
      alert(returnData);
      location.reload();
  });

  
  }
}
