package org.vdragun.backend.security.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Map;

// TODO: use different key for each user
@Service
public class JjwtTokenService implements JwtTokenService {

    private final String signingKey;

    public JjwtTokenService(@Value("${jwt.signing.key}") String signingKey) {
        this.signingKey = signingKey;
    }

    @Override
    public String buildJwtToken(Map<String, Object> claims) {
        SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(key)
                .compact();
    }

    @Override
    public Map<String, Object> parseJwtToken(String jwt) {
        SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }
}
