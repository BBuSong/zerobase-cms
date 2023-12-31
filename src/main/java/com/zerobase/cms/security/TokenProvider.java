package com.zerobase.cms.security;

import com.zerobase.cms.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class TokenProvider {

  @Value("{spring.jwt.secret}")
  private String secretKey;

  private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1hr
  private static final String KEY_ROLES = "roles";

  private final UserService userService;

  // 토큰 생성하는 메소드
  public String generateToken(String userId, List<String> roles) {

    Claims claims = Jwts.claims().setSubject(userId);
    claims.put(KEY_ROLES, roles);

    var now = new Date();
    var exporedDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)                                     // 토큰 생성 시간
        .setExpiration(exporedDate)                           // 토큰 만료 시간
        .signWith(SignatureAlgorithm.HS512, this.secretKey)   // 사용할 암호화 알고리즘, 비밀키
        .compact();

  }

  public Authentication getAuthentication(String jwt) {
    UserDetails userDetails = this.userService.loadUserByUsername(this.getUserId(jwt));
    return new UsernamePasswordAuthenticationToken(userDetails,"", userDetails.getAuthorities());
  }

  public String getUserId(String token) {
    return this.parseClaims(token).getSubject();
  }

  public boolean validateToken(String token) {
    if (!StringUtils.hasText(token)) return false;

    var claims = this.parseClaims(token);
    return !claims.getExpiration().before(new Date());
  }

  // 토큰이 유효한지 확인하는 메소드
  private Claims parseClaims(String token) {
    try {
      return Jwts.parser()
          .setSigningKey(this.secretKey)
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }

  }

}
