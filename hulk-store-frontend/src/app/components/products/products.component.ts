import {Component, OnInit} from '@angular/core';
import {ProductsService} from '../../services/products.service';
import {Observable} from 'rxjs';
import {Product} from '../../models/product';
import {MatDialog} from '@angular/material';
import {ConfirmationDialogComponent} from '../confirmation-dialog/confirmation-dialog.component';
import {PurchaseDialogComponent} from '../purchase-dialog/purchase-dialog.component';
import {ProductDialogComponent} from '../product-dialog/product-dialog.component';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit {

  products$: Observable<any>;

  constructor(private productsService: ProductsService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.loadAllProducts();
  }

  loadAllProducts() {
    this.products$ = this.productsService.getAll();
  }

  delete(product: Product) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent);
    dialogRef.componentInstance.title = 'Are you sure?';
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.productsService.delete(product.productId).subscribe(
          data => this.products$ = this.productsService.getAll()
        );
      }
    });
  }

  showProductDialogForEdit(product: Product) {
    const dialogRef = this.dialog.open(ProductDialogComponent);
    dialogRef.componentInstance.title = `Edit Product #${product.productId}`;
    dialogRef.componentInstance.editMode = true;
    dialogRef.componentInstance.product = product;
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadAllProducts();
      }
    });
  }

  showProductDialogForAddNew() {
    const dialogRef = this.dialog.open(ProductDialogComponent);
    dialogRef.componentInstance.title = 'Add new Product';
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadAllProducts();
      }
    });
  }

  showPurchaseDialog(product: Product) {
    const dialogRef = this.dialog.open(PurchaseDialogComponent);
    dialogRef.componentInstance.title = 'Get this Product!';
    dialogRef.componentInstance.product = product;
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadAllProducts();
      }
    });
  }
}
