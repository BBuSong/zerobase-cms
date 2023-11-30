package com.zerobase.cms.persist;

import com.zerobase.cms.model.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

  Optional<UserEntity> findByUserId(String userId);

  boolean existsByUserId(String userId);

}
