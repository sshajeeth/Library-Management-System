import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms'
import { AppRoutingModule, routingComponents } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { HttpClientModule } from '@angular/common/http';
import { ReserveComponent } from './reserve/reserve.component';








@NgModule({
  declarations: [
    AppComponent,
    routingComponents,
    HeaderComponent,
    ReserveComponent,


 

  
   
    
    
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,

  ],
  
  providers: [],

  bootstrap: [AppComponent]
})
export class AppModule { }
