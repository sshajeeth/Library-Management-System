import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddBookComponent } from './add-book/add-book.component';
import { AddDVDComponent } from './add-dvd/add-dvd.component';
import { DeleteItemComponent } from './delete-item/delete-item.component';
import { BorrowItemComponent } from './borrow-item/borrow-item.component';
import { ReturnItemComponent } from './return-item/return-item.component';
import { GenerateReportComponent } from './generate-report/generate-report.component';
import { DisplayItemsComponent } from './display-items/display-items.component';
import { RegisterComponent } from './register/register.component';
import { ReserveComponent } from './reserve/reserve.component';


const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'addBook', component: AddBookComponent },
  { path: 'addDVD', component: AddDVDComponent},
  { path: 'deleteItem', component: DeleteItemComponent },
  { path: 'borrowItem', component: BorrowItemComponent },
  { path: 'returnItem', component: ReturnItemComponent },
  { path: 'generateReport', component: GenerateReportComponent },
  { path: 'displayItems', component: DisplayItemsComponent },
  { path: 'reserve', component: ReserveComponent }
  

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [ReserveComponent,DisplayItemsComponent,RegisterComponent, AddBookComponent,AddDVDComponent, DeleteItemComponent, BorrowItemComponent, ReturnItemComponent, GenerateReportComponent]

