import {Component} from '@angular/core';
import {MatDialogRef} from '@angular/material';
import {Product} from '../../models/product';
import {Purchase} from '../../models/purchase';
import {ProductsService} from '../../services/products.service';

@Component({
  selector: 'app-purchase-dialog',
  templateUrl: './purchase-dialog.component.html',
  styleUrls: ['./purchase-dialog.component.scss']
})
export class PurchaseDialogComponent {

  title = '';
  amount = 1;
  product: Product;
  showError = false;
  errorMessage: string;

  constructor(private dialogRef: MatDialogRef<PurchaseDialogComponent>,
              private productService: ProductsService) {
  }

  minusOne(amount) {
    if (amount > 1) {
      this.amount = parseFloat(amount) - 1;
    } else {
      this.amount = 1;
    }
  }

  plusOne(amount) {
    this.amount = parseFloat(amount) + 1;
  }

  confirm() {
    const purchase: Purchase = {
      productId: this.product.productId,
      amount: this.amount
    };
    this.productService.purchase(purchase)
      .subscribe(
        data => {
          this.dialogRef.close(true);
        },
        err => {
          this.showError = true;
          this.errorMessage = err.error;
        }
      );
  }

}
