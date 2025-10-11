package accrox.aros.api.infrastructure.spring.adapters;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import accrox.aros.api.domain.model.RefreshToken;
import accrox.aros.api.domain.repository.RefreshTokenRepository;
import accrox.aros.api.infrastructure.spring.jpa.repository.RefreshTokenRepositoryJpa;

@Repository
public class RefreshTokenJpaAdapter implements RefreshTokenRepository {

    @Autowired
    private RefreshTokenRepositoryJpa refreshTokenRepositoryJpa;

    @Override
    public Optional<RefreshToken> findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Optional<RefreshToken> findByHash(String hash) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByHash'");
    }

    @Override
    public RefreshToken create(RefreshToken token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public void revokeToken(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'revokeToken'");
    }

}
