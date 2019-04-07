import { Component, OnInit } from '@angular/core';
import { Delete } from '../delete'
import { EnrollmentService } from '../enrollment.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-delete-item',
  templateUrl: './delete-item.component.html',
  styleUrls: ['./delete-item.component.css']
})
export class DeleteItemComponent implements OnInit {

  types = ['Book', 'DVD'];
  typeHasError: boolean = true;
  delete: String;
  validateType(value) {
    if (value == 'defaultType') {
      this.typeHasError = true;
    } else {
      this.typeHasError = false;
    }
  }

  deleteDetails = new Delete('');

  constructor(private _enrollmentService: EnrollmentService,private _router:Router) { }

  onSubmit() {
    this._enrollmentService.delete(this.deleteDetails.isbn).subscribe((deleteData: String) => {
      this.delete = deleteData;
      console.log(deleteData);
      if (deleteData != "invalid") {
        alert(deleteData);
        location.reload();
      } else {
        alert("Invalid ISBN Number");
        location.reload();
      }
    });
  }

  ngOnInit() {

  }


}
