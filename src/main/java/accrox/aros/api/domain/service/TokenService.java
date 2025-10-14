package accrox.aros.api.domain.service;

public interface TokenService {
    /**
     * generate a new (opaque) refresh token
     *
     * @param userEmail owner of the token
     *
     * @return generated token
     */
    public String generateRefreshToken(String userEmail);

    /**
     * generate a new accesss token
     *
     * @param userEmail owner of the token
     *
     * @return generated token
     */
    public String generateAccessToken(String userEmail);

    /**
     * check for a valid access token
     * 
     * @param token token to check
     *
     * @return token is valid
     */
    public boolean validateAccessToken(String token);


    /**
     * extract the userEmail from a access token
     * 
     * @param token token with the info
     *
     * @return extracted userEmailname
     */
    public String extractUserEmail(String token);

}