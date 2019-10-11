import {Component, OnInit} from '@angular/core';
import {ProductsService} from '../../services/products.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MatDialogRef} from '@angular/material';
import {Product} from '../../models/product';

@Component({
  selector: 'app-product',
  templateUrl: './product-dialog.component.html',
  styleUrls: ['./product-dialog.component.scss']
})
export class ProductDialogComponent implements OnInit {

  productForm: FormGroup;
  title = '';
  showError = false;
  errorMessage: string;
  product: Product;
  editMode = false;

  constructor(private dialogRef: MatDialogRef<ProductDialogComponent>,
              private formBuilder: FormBuilder,
              private productsService: ProductsService) {
    this.createForm();
  }

  ngOnInit() {
    if (this.editMode && this.product) {
      this.loadForm();
    }
  }

  createForm() {
    this.productForm = this.formBuilder.group({
      description: ['', Validators.required],
      amount: ['', Validators.required],
      price: ['', Validators.required]
    });
  }

  loadForm() {
    this.productForm.setValue({
      description: this.product.description,
      amount: this.product.amount,
      price: this.product.price
    });
  }

  confirm() {
    if (this.productForm.valid) {
      if (this.editMode) {
        const editedProduct: Product = this.productForm.value;
        editedProduct.productId = this.product.productId;
        this.productsService.update(editedProduct).subscribe(
          product => this.dialogRef.close(true),
          err => this.handleErrors(err));
      } else {
        const newProduct: Product = this.productForm.value;
        this.productsService.saveNew(newProduct).subscribe(
          product => this.dialogRef.close(true),
          err => this.handleErrors(err));
      }
    }
  }

  handleErrors(err) {
    this.showError = true;
    this.errorMessage = err.error;
  }

}
