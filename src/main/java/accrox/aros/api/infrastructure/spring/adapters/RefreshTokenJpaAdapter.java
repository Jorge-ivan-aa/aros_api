package accrox.aros.api.infrastructure.spring.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import accrox.aros.api.domain.repository.RefreshTokenRepository;
import accrox.aros.api.infrastructure.spring.jpa.repository.RefreshTokenRepositoryJpa;

@Repository
public class RefreshTokenJpaAdapter implements RefreshTokenRepository {

    @Autowired
    private RefreshTokenRepositoryJpa refreshTokenRepositoryJpa;

}
