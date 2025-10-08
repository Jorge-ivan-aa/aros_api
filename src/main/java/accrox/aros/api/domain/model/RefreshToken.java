package accrox.aros.api.domain.model;

import java.time.LocalDateTime;

public class RefreshToken {
    private Long id;

    private String hash;

    private LocalDateTime revokedAt;

    private User user;

    public RefreshToken() {
    }

    public RefreshToken(Long id, String hash, LocalDateTime revokedAt, User user) {
        this.id = id;
        this.hash = hash;
        this.revokedAt = revokedAt;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public LocalDateTime getRevokedAt() {
        return revokedAt;
    }

    public void setRevokedAt(LocalDateTime revokedAt) {
        this.revokedAt = revokedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
