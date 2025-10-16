package accrox.aros.api.infrastructure.spring.mappers;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

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
        Collection<AreaEntity> areas,
        Collection<RefreshToken> tokens
    ) {
        if (source == null) {
            return null;
        }

        User target = new User();

        target.setId(source.getId());
        target.setDocument(source.getDocument());
        target.setName(source.getName());
        target.setEmail(source.getEmail());
        target.setPassword(source.getPassword());
        target.setPhone(source.getPhone());
        target.setAddress(source.getAddress());
        //target.setAreas(areas);
        Collection<Area> domainAreas = (areas == null || areas.isEmpty())
                ? Collections.emptyList()
                : areas.stream()
                .map(ae -> {
                    Area a = new Area();
                    a.setId(ae.getId());
                    a.setName(ae.getName());
                    return a;
                })
                .collect(Collectors.toList());

        target.setAreas(domainAreas);



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
        target.setDocument(source.getDocument());
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
