import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Book } from './book';
import { DVD } from './dvd';

import { Delete } from './delete';
import { Borrow } from './borrow';
import { Return } from './return';
import { Register } from './register'
import { Observable } from 'rxjs';



@Injectable({
  providedIn: 'root'
})



export class EnrollmentService {

  URL = 'http://localhost:9000'
  urlRegister = 'http://localhost:9000/register'
  urlAddBook = 'http://localhost:9000/books'
  urlAddDVD = 'http://localhost:9000/dvd'
  urlDeleteItem = 'http://localhost:9000/delete'
  urlBorrowItem = 'http://localhost:9000/borrow'
  urlReturnItem = 'http://localhost:9000/'
  bookSpaceURL = 'http://localhost:9000/bookRemain'
  dvdSpaceURL = 'http://localhost:9000/dvdRemain'
  displayItemsURL = 'http://localhost:9000/displayBook'
  displayDVDURL = 'http://localhost:9000/displayDVD'
  reportURL = 'http://localhost:9000/report'
  reserve = 'http://localhost:9000/reserve'


  constructor(private _http: HttpClient) { }

  enrollRegister(item: Register) {
    return this._http.post<any>(this.urlRegister, item);

  }

  enrollBook(item: Book) {
    return this._http.post<any>(this.urlAddBook, item);

  }



  enrollDVD(item: DVD) {
    return this._http.post<any>(this.urlAddDVD, item);

  }

  enrollBorrow(item: Borrow) {
    return this._http.post<any>(this.urlBorrowItem, item);

  }


  enrollBookSpace() {
    return this._http.get(this.bookSpaceURL);
  }
  enrollDVDSpace() {
    return this._http.get(this.dvdSpaceURL);
  }




  checkAvailablity(isbn:String){
    return this._http.get(`${this.URL}/checkAvailablity/${isbn}`);
  }

  checkDVDAvailablity(isbn:String){
    return this._http.get(`${this.URL}/checkDVDAvailablity/${isbn}`);
  }

  delete(isbn:String){
    return this._http.get(`${this.URL}/delete/${isbn}`);
  }

  borrowNameCheck(readerName:String){
    return this._http.get(`${this.URL}/borrowNameCheck/${readerName}`);
  }

  borrowItemCheck(isbn:String){
    return this._http.get(`${this.URL}/borrowItemCheck/${isbn}`);
  }

  return(isbn:String){
    return this._http.get(`${this.URL}/return/${isbn}`);
  }

  registerNameCheck(fullName:String){
    return this._http.get(`${this.URL}/registerNameCheck/${fullName}`);
  }

  displayItems():Observable<Object[]> {
    return this._http.get<Object[]>(this.displayItemsURL);
  }
  displayDVD():Observable<Object[]> {
    return this._http.get<Object[]>(this.displayDVDURL);
  }

  report():Observable<Object[]> {
    return this._http.get<Object[]>(this.reportURL);
  }

  enrollreserve(isbn:String){
    return this._http.get(`${this.URL}/reserve/${isbn}`);
  }
}
