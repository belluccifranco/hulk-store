import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Product} from '../models/product';
import {Purchase} from '../models/purchase';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  url = environment.apiUrl + '/api/v1/products';

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Product> {
    return this.http.get<Product>(this.url);
  }

  delete(productId: number) {
    return this.http.delete(`${this.url}/${productId}`);
  }

  saveNew(newProduct: Product) {
    return this.http.post(this.url, newProduct);
  }

  update(editedProduct: Product) {
    return this.http.put(this.url, editedProduct);
  }

  purchase(purchase: Purchase) {
    return this.http.post(`${this.url}/purchase`, purchase);
  }
}
