import { Component, OnInit } from '@angular/core';
import { EnrollmentService } from '../enrollment.service';

@Component({
  selector: 'app-display-items',
  templateUrl: './display-items.component.html',
  styleUrls: ['./display-items.component.css']
})
export class DisplayItemsComponent implements OnInit {
  public itemsList=[];
  public dvdsList=[];
  constructor(private _enrollmentService: EnrollmentService) { }

  ngOnInit() {
    this.displayItems();
    
  }
  displayItems(){
    this._enrollmentService.displayItems().subscribe(data=>{this.itemsList=data
      console.log(this.itemsList);
    console.log(data);});

    this._enrollmentService.displayDVD().subscribe(data=>{this.dvdsList=data
      console.log(this.dvdsList);
    console.log(data);});
   
   
  }



  
}
