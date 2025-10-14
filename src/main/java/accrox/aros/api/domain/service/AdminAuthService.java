package accrox.aros.api.domain.service;

import accrox.aros.api.domain.model.User;

public interface AdminAuthService {

    boolean isAdminCredentials(String email);

    User getUser();
}