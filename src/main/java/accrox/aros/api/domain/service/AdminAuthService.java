package accrox.aros.api.domain.service;

public interface AdminAuthService {
    boolean isAdminCredentials(String email, String password);
}
