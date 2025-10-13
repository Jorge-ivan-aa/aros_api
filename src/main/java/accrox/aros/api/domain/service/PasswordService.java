package accrox.aros.api.domain.service;

public interface PasswordService {
    /**
     * encode the given password
     *
     * @param password password to encode
     *
     * @return the encoded password
     */
    public String encode(String password);

    /**
     * check a password match the encoded
     *
     * @param password raw password
     * @param encoded encoded password
     *
     * @return the password match the encoded
     */
    public boolean matches(String password, String encoded);
}