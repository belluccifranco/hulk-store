package com.todo1.hulkstore.service;

import com.todo1.hulkstore.model.Product;
import com.todo1.hulkstore.model.dto.ProductDTO;
import com.todo1.hulkstore.model.dto.PurchaseProductDTO;

import javax.validation.Valid;
import java.util.List;

public interface ProductService {

  Product saveNew(@Valid ProductDTO productDTO);

  void purchase(@Valid PurchaseProductDTO purchaseProductDTO);

  void deleteOne(Long productId);

  Product updateOne(@Valid ProductDTO productDTO);

  List<Product> getAll();

  Product getProductById(Long productId);
}
