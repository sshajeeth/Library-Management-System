import { Component, OnInit } from '@angular/core';
import { EnrollmentService } from '../enrollment.service';

@Component({
  selector: 'app-generate-report',
  templateUrl: './generate-report.component.html',
  styleUrls: ['./generate-report.component.css']
})
export class GenerateReportComponent implements OnInit {
  public generateReport=[];

  constructor(private _enrollmentService: EnrollmentService) { }

  ngOnInit() {
    this.report();
  }

  report(){
    this._enrollmentService.report().subscribe(data=>{this.generateReport=data
      console.log(this.generateReport);
    console.log(data);});
  }

}
