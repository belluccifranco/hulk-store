import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {ProductsComponent} from './components/products/products.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule, MatDialogModule, MatIconModule, MatInputModule, MatListModule} from '@angular/material';
import {HttpClientModule} from '@angular/common/http';
import {FlexLayoutModule} from '@angular/flex-layout';
import {ProductDialogComponent} from './components/product-dialog/product-dialog.component';
import {ConfirmationDialogComponent} from './components/confirmation-dialog/confirmation-dialog.component';
import {PurchaseDialogComponent} from './components/purchase-dialog/purchase-dialog.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@NgModule({
  declarations: [
    ProductsComponent,
    ProductDialogComponent,
    ConfirmationDialogComponent,
    PurchaseDialogComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MatButtonModule,
    MatListModule,
    MatIconModule,
    MatDialogModule,
    FormsModule,
    MatInputModule,
    ReactiveFormsModule
  ],
  providers: [],
  entryComponents: [
    ConfirmationDialogComponent,
    PurchaseDialogComponent,
    ProductDialogComponent
  ],
  bootstrap: [ProductsComponent]
})
export class AppModule {
}
