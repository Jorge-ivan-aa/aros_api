package accrox.aros.api.infrastructure.spring.mappers;

import java.util.Collection;

import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.model.RefreshToken;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.infrastructure.spring.jpa.entity.AreaEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.RefreshTokenEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.UserEntity;

public class UserJpaMapper {
    /**
     * map {@link UserEntity} to {@link User}
     */
    public static User toDomain(
        UserEntity source,
        Collection<Area> areas,
        Collection<RefreshToken> tokens
    ) {
        if (source == null) {
            return null;
        }

        User target = new User();

        target.setId(source.getId());
        target.setName(source.getName());
        target.setEmail(source.getEmail());
        target.setPassword(source.getPassword());
        target.setPhone(source.getPhone());
        target.setAddress(source.getAddress());
        target.setAreas(areas);
        target.setTokens(tokens);

        return target;
    }

    /**
     * map {@link User} to {@link UserEntity}
     */
    public static UserEntity toEntity(
        User source,
        Collection<AreaEntity> areas,
        Collection<RefreshTokenEntity> tokens
    ) {
        if (source == null) {
            return null;
        }

        UserEntity target = new UserEntity();

        target.setId(source.getId());
        target.setName(source.getName());
        target.setEmail(source.getEmail());
        target.setPassword(source.getPassword());
        target.setPhone(source.getPhone());
        target.setAddress(source.getAddress());
        target.setAreas(areas);
        target.setTokens(tokens);

        return target;
    }
}
