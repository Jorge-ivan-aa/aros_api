package accrox.aros.api.infrastructure.spring.mappers;

import accrox.aros.api.domain.model.RefreshToken;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.infrastructure.spring.jpa.entity.RefreshTokenEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.UserEntity;

public class RefreshTokenJpaMapper {
    /**
     * map {@link RefreshTokenEntity} to {@link RefreshToken}
     */
    public static RefreshToken toDomain(
        RefreshTokenEntity source,
        User user
    ) {
        RefreshToken target = new RefreshToken();

        target.setId(source.getId());
        target.setHash(source.getHash());
        target.setRevokedAt(source.getRevokedAt());
        target.setUser(user);

        return target;
    }

    /**
     * map {@link RefreshToken} to {@link RefreshTokenEntity}
     */
    public static RefreshTokenEntity toEntity(
        RefreshToken source,
        UserEntity user
    ) {
        RefreshTokenEntity target = new RefreshTokenEntity();

        target.setId(source.getId());
        target.setHash(source.getHash());
        target.setRevokedAt(source.getRevokedAt());
        target.setUser(user);

        return target;
    }
}
