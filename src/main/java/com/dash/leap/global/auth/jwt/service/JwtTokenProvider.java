package com.dash.leap.global.auth.jwt.service;

import com.dash.leap.domain.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider extends AbstractTokenProvider {

    public JwtTokenProvider(TokenProperties tokenProperties) {
        super(tokenProperties);
    }

    public String createToken(Object payload) {
        User user = (User) payload;
        return Jwts.builder()
                .claim("id", user.getId())
                .claim("loginId", user.getLoginId())
                .claim("nickname", user.getNickname())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public long extractUserId(String token) {
        Claims claims = getPayload(token);
        return claims.get("id", Long.class);
    }
}
