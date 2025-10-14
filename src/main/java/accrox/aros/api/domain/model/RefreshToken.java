package accrox.aros.api.domain.model;

import java.time.LocalDateTime;

public class RefreshToken {
    private Long id;

    private String hash;

    private LocalDateTime revokedAt;

    private String userEmail;

    public RefreshToken() {
    }

    public RefreshToken(Long id, String hash, LocalDateTime revokedAt, String userEmail) {
        this.id = id;
        this.hash = hash;
        this.revokedAt = revokedAt;
        this.userEmail = userEmail;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
