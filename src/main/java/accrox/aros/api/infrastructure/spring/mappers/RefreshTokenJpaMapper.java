package accrox.aros.api.infrastructure.spring.mappers;

import accrox.aros.api.domain.model.RefreshToken;
import accrox.aros.api.infrastructure.spring.jpa.entity.RefreshTokenEntity;

public class RefreshTokenJpaMapper {
    /**
     * map {@link RefreshTokenEntity} to {@link RefreshToken}
     */
    public static RefreshToken toDomain(
            RefreshTokenEntity source,
            String userEmail) {
        RefreshToken target = new RefreshToken();

        target.setId(source.getId());
        target.setHash(source.getHash());
        target.setRevokedAt(source.getRevokedAt());
        target.setUserEmail(userEmail);

        return target;
    }

    /**
     * map {@link RefreshToken} to {@link RefreshTokenEntity}
     */
    public static RefreshTokenEntity toEntity(
            RefreshToken source,
            String userEmail) {
        RefreshTokenEntity target = new RefreshTokenEntity();

        target.setId(source.getId());
        target.setHash(source.getHash());
        target.setRevokedAt(source.getRevokedAt());
        target.setUserEmail(userEmail);

        return target;
    }
}
