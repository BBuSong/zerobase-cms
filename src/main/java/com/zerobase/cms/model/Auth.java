package com.zerobase.cms.model;

import com.zerobase.cms.model.entity.UserEntity;
import java.util.List;
import lombok.Data;

public class Auth {

  //로그인 사용하는 클래스
  @Data
  public static class SignIn {
    private String userId;
    private String password;
  }

  //회원가입 시 사용하는 클래스
  @Data
  public static class SignUp {
    private String username;
    private String userId;
    private String password;
    private List<String> roles;

    public UserEntity toEntity() {
      return UserEntity.builder()
          .userId(this.userId)
          .username(this.username)
          .password(this.password)
          .build();
    }
  }
}
