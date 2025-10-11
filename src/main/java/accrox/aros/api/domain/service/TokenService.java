package accrox.aros.api.domain.service;

import accrox.aros.api.domain.model.User;

public interface TokenService {
    /**
     * generate a new (opaque) refresh token
     *
     * @param user owner of the token
     *
     * @return generated token
     */
    public String generateRefreshToken(User user);

    /**
     * generate a new accesss token
     *
     * @param user owner of the token
     *
     * @return generated token
     */
    public String generateAccessToken(User user);

    /**
     * check for a valid access token
     * 
     * @param token token to check
     *
     * @return token is valid
     */
    public boolean validateAccessToken(String token);

    /**
     * check for a valid refresh token
     * 
     * @param token token to check
     *
     * @return token is valid
     */
    // public boolean validateRefreshToken(String token);

    /**
     * extract the username from a access token
     * 
     * @param token token with the info
     *
     * @return extracted username
     */
    public String extractUsername(String token);

    /**
     * extact the user info from access token
     * 
     * @param token token with the info
     * 
     * @return user's info
     */
    public User extractUserInfo(String token);
}

