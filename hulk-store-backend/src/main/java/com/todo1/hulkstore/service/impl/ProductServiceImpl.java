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

  /**
   * Saves an entity Product with data from productDTO
   *
   * @param productDTO Data for the new Product
   * @return The saved Product
   */
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

  /**
   * Subtract the required units of products. Check the availability (amount) of products before
   * performing the operation
   *
   * @param purchaseProductDTO Data for the subtract operation
   */
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

  /**
   * Removes a product by id
   *
   * @param productId The product id to be removed
   */
  @Override
  public void deleteOne(Long productId) {
    this.productRepository.deleteById(productId);
  }

  /**
   * Updates a product with data from productDTO
   *
   * @param productDTO Data for the update operation
   * @return The updated product
   */
  @Override
  public synchronized Product updateOne(@Valid ProductDTO productDTO) {
    Optional<Product> product = this.productRepository.findById(productDTO.getProductId());
    if (product.isPresent()) {
      product.get().setDescription(productDTO.getDescription());
      product.get().setAmount(productDTO.getAmount());
      product.get().setPrice(productDTO.getPrice());
      return this.productRepository.save(product.get());
    } else {
      throw new BusinessServiceException("Product #" + productDTO.getProductId() + " not found :(");
    }
  }

  /**
   * Returns all products
   *
   * @return A list of products
   */
  @Override
  public List<Product> getAll() {
    return this.productRepository.findAll();
  }

  /**
   * Returns a product by id
   *
   * @param productId The product id
   * @return The product found or null if doesn't exist
   */
  @Override
  public Product getProductById(Long productId) {
    Optional<Product> product = this.productRepository.findById(productId);
    return product.orElse(null);
  }
}
