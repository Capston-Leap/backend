package com.dash.leap.global.auth.jwt.service;

import com.dash.leap.global.auth.jwt.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
@EnableConfigurationProperties({TokenProperties.class})
public abstract class AbstractTokenProvider {

    protected TokenProperties tokenProperties;
    protected final Key secretKey;

    public AbstractTokenProvider(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
        this.secretKey = new SecretKeySpec(
                tokenProperties.secretKey().getBytes(StandardCharsets.UTF_8),
                SignatureAlgorithm.HS256.getJcaName()
        );
    }

    public abstract String createToken(Object payload);

    public Claims getPayload(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey.getEncoded())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new UnauthorizedException();
        }
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(tokenProperties.secretKey().getBytes())
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new UnauthorizedException();
        }
    }
}
