package com.zerobase.cms.service;

import com.zerobase.cms.model.Auth;
import com.zerobase.cms.model.entity.UserEntity;
import com.zerobase.cms.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    return this.userRepository.findByUserId(username)
        .orElseThrow(() -> new UsernameNotFoundException("couldn't find user -> " + username));

  }

  // 회원가입을 위한 메소드
  public UserEntity register(Auth.SignUp user) {
    boolean exists = this.userRepository.existsByUserId(user.getUserId());
    if (exists) {
      throw new RuntimeException("이미 사용 중인 아이디 입니다.");
    }

    user.setPassword(this.passwordEncoder.encode(user.getPassword()));
    var result = this.userRepository.save(user.toEntity());

    return result;
  }

  // 로그인 시 검증을 위한 메소드
  public UserEntity authenticate(Auth.SignIn user) {

    var member = this.userRepository.findByUserId(user.getUserId())
        .orElseThrow(() -> new RuntimeException("존재하지 않는 ID입니다."));

    if (this.passwordEncoder.matches(user.getPassword(), member.getPassword())) {
      throw new RuntimeException("비밀번호가 일치하지 않습니다.");
    }

    return member;
  }

}
