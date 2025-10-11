package accrox.aros.api.infrastructure.jwt;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

import javax.crypto.SecretKey;

import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.service.TokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.core.util.Json;

public class JwtService implements TokenService {
    private String accessTokenSecrect;

    public JwtService(
            String accessTokenSecrect) {
        // this.refreshTokenSecrect = refreshTokenSecrect;
        this.accessTokenSecrect = accessTokenSecrect;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateAccessToken(User user) {
        SecretKey signKey = Keys.hmacShaKeyFor(
                accessTokenSecrect.getBytes(StandardCharsets.UTF_8));

        JwtBuilder builder = Jwts
                .builder()
                .claim("user.data", user)
                .subject(user.getEmail())
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 15))
                .signWith(signKey)
        // .compact()
        ;

        builder.claim("user.areas", Collections.EMPTY_SET);

        return builder.compact();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateRefreshToken(User user) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateAccessToken(String token) {
        Claims claims;

        try {
            claims = this.extractClaimsAccessToken(token);
        } catch (JwtException e) {
            return false;
        }

        if (!claims.getExpiration().after(new Date())) {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String extractUsername(String token) {
        return this.extractClaimsAccessToken(token).getSubject();
    }

    @Override
    public User extractUserInfo(String token) {
        Claims claims = this.extractClaimsAccessToken(token);
        return Json.mapper().convertValue(claims.get("user.data"), User.class);
    }

    /**
     * extract the claims from access token
     * 
     * @param token access token
     *
     * @return token's claims
     * 
     * @throws JwtException the token can't be parsed
     */
    private Claims extractClaimsAccessToken(String token) {
        SecretKey signKey = Keys.hmacShaKeyFor(
                accessTokenSecrect.getBytes(StandardCharsets.UTF_8));

        return Jwts
                .parser()
                .verifyWith(signKey)
                .build()
                .parseSignedClaims(token)
                // .parseSignedClaims(token)
                // .parse(token)
                .getPayload();
    }

}
