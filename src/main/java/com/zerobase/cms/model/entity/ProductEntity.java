package com.zerobase.cms.model.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name="product")
@Data
public class ProductEntity {
  @Id
  @Column(name = "product_no")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long productNo;           // 상품 번호

  @Column(nullable = false, length = 50)
  private String productName;       // 상품명

  @Column(name = "price", nullable = false)
  private int price;               // 상품 가격

  @Column(name = "deliveryFee", nullable = false)
  private int deliveryFee;         // 배송비

  @Column(name = "size", nullable = false)
  private String size;              // 상품 사이즈

  private LocalDateTime createdAt;  // 상품 등록 일자

  private LocalDateTime modifiedAt; // 상품 수정 일자

}
