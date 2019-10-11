package com.todo1.hulkstore;

import com.todo1.hulkstore.model.Product;
import com.todo1.hulkstore.model.dto.ProductDTO;
import com.todo1.hulkstore.model.dto.PurchaseProductDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HulkStoreApplicationTests {

  @Autowired TestRestTemplate testRestTemplate;

  private String url = "/api/v1/products";

  @Test
  public void shouldGetAllProduct() {
    ProductDTO productDTO1 =
        new ProductDTO(0L, "Incredible Hulk", new BigDecimal("1"), new BigDecimal("150.50"), null);
    ProductDTO productDTO2 =
        new ProductDTO(0L, "Superman", new BigDecimal("2"), new BigDecimal("300.50"), null);
    this.testRestTemplate.postForObject(url, productDTO1, Product.class);
    this.testRestTemplate.postForObject(url, productDTO2, Product.class);
    Product[] products = this.testRestTemplate.getForObject("/api/v1/products", Product[].class);
    Assert.assertEquals(2, products.length);
  }

  @Test
  public void shouldUpdateProduct() {
    ProductDTO productDTO1 =
        new ProductDTO(
            0L, "The amazing Spiderman", new BigDecimal("1"), new BigDecimal("150.50"), null);
    Product product = this.testRestTemplate.postForObject(url, productDTO1, Product.class);
    Assert.assertNotNull(product);
    product.setDescription("The amazing Spiderman and Venom");
    product.setAmount(new BigDecimal("2.00"));
    product.setPrice(new BigDecimal("300.00"));
    this.testRestTemplate.put(url, product);
    product = this.testRestTemplate.getForObject(url + "/1", Product.class);
    Assert.assertNotNull(product);
    Assert.assertEquals("The amazing Spiderman and Venom", product.getDescription());
    Assert.assertEquals(new BigDecimal("2.00"), product.getAmount());
    Assert.assertEquals(new BigDecimal("300.00"), product.getPrice());
  }

  @Test
  public void shouldDeleteProduct() {
    ProductDTO productDTO1 =
        new ProductDTO(
            0L, "The amazing Spiderman", new BigDecimal("1"), new BigDecimal("150.50"), null);
    Product product = this.testRestTemplate.postForObject(url, productDTO1, Product.class);
    Assert.assertNotNull(product);
    this.testRestTemplate.delete(url + "/1");
    product = this.testRestTemplate.getForObject(url + "/1", Product.class);
    Assert.assertNull(product);
  }

  @Test
  public void shouldGetProductById() {
    ProductDTO productDTO1 =
        new ProductDTO(
            0L, "The amazing Spiderman", new BigDecimal("1"), new BigDecimal("150.50"), null);
    Product product = this.testRestTemplate.postForObject(url, productDTO1, Product.class);
    Assert.assertEquals(productDTO1.getDescription(), product.getDescription());
  }

  @Test
  public void shouldReduceAmountAfterPurchase() {
    ProductDTO productDTO1 =
        new ProductDTO(0L, "Joker", new BigDecimal("10"), new BigDecimal("850.50"), null);
    Product product = this.testRestTemplate.postForObject(url, productDTO1, Product.class);
    Assert.assertNotNull(product);
    PurchaseProductDTO purchaseProductDTO = new PurchaseProductDTO(1L, new BigDecimal("10"));
    this.testRestTemplate.postForObject(url + "/purchase", purchaseProductDTO, Void.class);
    product = this.testRestTemplate.getForObject(url + "/1", Product.class);
    Assert.assertEquals(new BigDecimal("0.00"), product.getAmount());
  }
}
