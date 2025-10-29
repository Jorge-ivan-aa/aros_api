package accrox.aros.api.domain.service;

public interface TokenService {
    /**
     * generate a new (opaque) refresh token
     *
     * @param userDocument owner of the token
     *
     * @return generated token
     */
    public String generateRefreshToken(String userDocument);

    /**
     * generate a new accesss token
     *
     * @param userDocument owner of the token
     *
     * @return generated token
     */
    public String generateAccessToken(String userDocument);

    /**
     * check for a valid access token
     *
     * @param token token to check
     *
     * @return token is valid
     */
    public boolean validateAccessToken(String token);

    /**
     * extract the userDocument from a access token
     *
     * @param token token with the info
     *
     * @return extracted userDocument
     */
    public String extractUserDocument(String token);
}
