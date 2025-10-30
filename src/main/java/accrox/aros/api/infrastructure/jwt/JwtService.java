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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwtService implements TokenService {

    private static final Logger logger = LoggerFactory.getLogger(
        JwtService.class
    );

    private String accessTokenSecrect;

    public JwtService(String accessTokenSecrect) {
        this.accessTokenSecrect = accessTokenSecrect;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateAccessToken(String userDocument) {
        logger.debug("Generating access token for user: {}", userDocument);
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

        String token = builder.compact();
        logger.debug(
            "Access token generated successfully, length: {}",
            token.length()
        );
        return token;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateRefreshToken(String userDocument) {
        logger.debug("Generating refresh token for user: {}", userDocument);
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);

        String token = Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(bytes);
        logger.debug(
            "Refresh token generated successfully, length: {}",
            token.length()
        );
        return token;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateAccessToken(String token) {
        logger.debug("Validating access token, length: {}", token.length());
        Claims claims;
        logger.debug("Attempting to parse access token");

        try {
            claims = this.extractClaimsAccessToken(token);
        } catch (JwtException e) {
            logger.warn("Failed to parse access token: {}", e.getMessage());
            return false;
        }

        if (!claims.getExpiration().after(new Date())) {
            logger.warn("Access token has expired");
            return false;
        }

        logger.debug("Access token validation successful");
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String extractUserDocument(String token) {
        logger.debug("Extracting user document from access token");
        String userDocument = this.extractClaimsAccessToken(token).getSubject();
        logger.debug("Extracted user document: {}", userDocument);
        return userDocument;
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
        logger.debug("Extracting claims from access token");
        SecretKey signKey = Keys.hmacShaKeyFor(
            accessTokenSecrect.getBytes(StandardCharsets.UTF_8)
        );

        Claims claims = Jwts.parser()
            .verifyWith(signKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();

        logger.debug(
            "Claims extracted successfully, subject: {}",
            claims.getSubject()
        );
        return claims;
    }
}
