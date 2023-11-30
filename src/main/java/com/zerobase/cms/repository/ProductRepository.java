package com.zerobase.cms.repository;

import com.zerobase.cms.model.entity.ProductEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

  List<ProductEntity> findByName(String productName);
}
