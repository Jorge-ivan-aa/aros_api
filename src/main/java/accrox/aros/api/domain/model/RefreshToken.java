package accrox.aros.api.domain.model;

import java.time.LocalDateTime;

public class RefreshToken {

    private Long id;

    private String hash;

    private LocalDateTime createdAt;

    private LocalDateTime revokedAt;

    private String userDocument;

    public RefreshToken() {}

    public RefreshToken(
        Long id,
        String hash,
        LocalDateTime createdAt,
        LocalDateTime revokedAt,
        String userDocument
    ) {
        this.id = id;
        this.hash = hash;
        this.createdAt = createdAt;
        this.revokedAt = revokedAt;
        this.userDocument = userDocument;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getRevokedAt() {
        return revokedAt;
    }

    public void setRevokedAt(LocalDateTime revokedAt) {
        this.revokedAt = revokedAt;
    }

    public String getUserDocument() {
        return userDocument;
    }

    public void setUserDocument(String userDocument) {
        this.userDocument = userDocument;
    }
}
