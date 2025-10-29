package accrox.aros.api.infrastructure.jwt;

import accrox.aros.api.domain.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import javax.crypto.SecretKey;

public class JwtService implements TokenService {

    private String accessTokenSecrect;

    public JwtService(String accessTokenSecrect) {
        this.accessTokenSecrect = accessTokenSecrect;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateAccessToken(String userDocument) {
        SecretKey signKey = Keys.hmacShaKeyFor(
            accessTokenSecrect.getBytes(StandardCharsets.UTF_8)
        );

        JwtBuilder builder = Jwts.builder()
            .claim("userDocument.data", userDocument)
            .subject(userDocument)
            .expiration(
                new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 168)
            )
            .signWith(signKey);
        // .compact()

        builder.claim("userDocument.areas", Collections.EMPTY_SET);

        return builder.compact();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateRefreshToken(String userDocument) {
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
    public String extractUserDocument(String token) {
        return this.extractClaimsAccessToken(token).getSubject();
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
            accessTokenSecrect.getBytes(StandardCharsets.UTF_8)
        );

        return Jwts.parser()
            .verifyWith(signKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
