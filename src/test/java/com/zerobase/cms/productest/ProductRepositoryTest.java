package com.zerobase.cms.productest;

import com.zerobase.cms.model.entity.ProductEntity;
import com.zerobase.cms.repository.ProductRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProductRepositoryTest {

  @Autowired
  ProductRepository productRepository;
  @Test
  @DisplayName("상품 저장 테스트")
  public void createProductTest() {
    ProductEntity product = new ProductEntity();
    product.setProductName("테스트1");
    product.setPrice(1000);
    product.setDeliveryFee(300);
    product.setSize("XL");
    product.setCreatedAt(LocalDateTime.now());
    product.setModifiedAt(LocalDateTime.now());

    ProductEntity savedProduct = productRepository.save(product);
    System.out.println(savedProduct.toString());
  }

}
