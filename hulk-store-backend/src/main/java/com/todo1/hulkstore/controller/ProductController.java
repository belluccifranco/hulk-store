package com.todo1.hulkstore.controller;

import com.todo1.hulkstore.model.Product;
import com.todo1.hulkstore.model.dto.ProductDTO;
import com.todo1.hulkstore.model.dto.PurchaseProductDTO;
import com.todo1.hulkstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
@RequestMapping("/api/v1")
public class ProductController {

  private ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/products/{productId}")
  public Product getProductById(@PathVariable Long productId) {
    return this.productService.getProductById(productId);
  }

  @GetMapping("/products")
  public List<Product> getAll() {
    return this.productService.getAll();
  }

  @PostMapping("/products")
  public Product addNew(@RequestBody ProductDTO productDTO) {
    return productService.saveNew(productDTO);
  }

  @PostMapping("/products/purchase")
  public void purchase(@RequestBody PurchaseProductDTO purchaseProductDTO) {
    this.productService.purchase(purchaseProductDTO);
  }

  @DeleteMapping("/products/{productId}")
  public void deleteOne(@PathVariable Long productId) {
    this.productService.deleteOne(productId);
  }

  @PutMapping("/products")
  public Product updateProduct(@RequestBody ProductDTO productDTO) {
    return this.productService.updateOne(productDTO);
  }
}
