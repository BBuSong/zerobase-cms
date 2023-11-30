package com.zerobase.cms.web;

import com.zerobase.cms.model.Auth;
import com.zerobase.cms.security.TokenProvider;
import com.zerobase.cms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final UserService userService;

  private final TokenProvider tokenProvider;

  // 회원가입용 API
  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody Auth.SignUp request) {

    var result = this.userService.register(request);

    return null;
  }

  // 로그인용 API
  @PostMapping("/signin")
  public ResponseEntity<?> signin(@RequestBody Auth.SignIn request) {

    var member = this.userService.authenticate(request);
    var token = this.tokenProvider.generateToken(member.getUserId(), member.getRoles());

    return ResponseEntity.ok(token);
  }

}
