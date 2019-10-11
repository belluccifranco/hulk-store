package com.todo1.hulkstore.service.impl;

import com.todo1.hulkstore.exceptions.BusinessServiceException;
import com.todo1.hulkstore.model.Product;
import com.todo1.hulkstore.model.dto.ProductDTO;
import com.todo1.hulkstore.model.dto.PurchaseProductDTO;
import com.todo1.hulkstore.repository.ProductRepository;
import com.todo1.hulkstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Validated
public class ProductServiceImpl implements ProductService {

  private ProductRepository productRepository;

  @Autowired
  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Product saveNew(@Valid ProductDTO productDTO) {
    Product product =
        new Product(
            0L,
            productDTO.getDescription(),
            productDTO.getAmount(),
            productDTO.getPrice(),
            LocalDateTime.now());
    return this.productRepository.save(product);
  }

  @Override
  public synchronized void purchase(@Valid PurchaseProductDTO purchaseProductDTO) {
    Optional<Product> product = this.productRepository.findById(purchaseProductDTO.getProductId());
    if (product.isPresent()) {
      if (purchaseProductDTO.getAmount().compareTo(product.get().getAmount()) <= 0) {
        product.get().setAmount(product.get().getAmount().subtract(purchaseProductDTO.getAmount()));
        this.productRepository.save(product.get());
      } else {
        throw new BusinessServiceException(
            "Sorry, the amount of product required is not available :(");
      }
    } else {
      throw new BusinessServiceException("Sorry, the product required is no longer available :(");
    }
  }

  @Override
  public void deleteOne(Long productId) {
    this.productRepository.deleteById(productId);
  }

  @Override
  public synchronized Product updateOne(@Valid ProductDTO productDTO) {
    Optional<Product> product =
        this.productRepository.findById(productDTO.getProductId());
    if (product.isPresent()) {
      product.get().setDescription(productDTO.getDescription());
      product.get().setAmount(productDTO.getAmount());
      product.get().setPrice(productDTO.getPrice());
      return this.productRepository.save(product.get());
    } else {
      throw new BusinessServiceException(
          "Product #" + productDTO.getProductId() + " not found :(");
    }
  }

  @Override
  public List<Product> getAll() {
    return this.productRepository.findAll();
  }

  @Override
  public Product getProductById(Long productId) {
    Optional<Product> product = this.productRepository.findById(productId);
    return product.orElse(null);
  }
}
