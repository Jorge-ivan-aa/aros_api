package accrox.aros.api.domain.model;

import accrox.aros.api.domain.service.AdminAuthService;
import accrox.aros.api.domain.service.PasswordService;
import java.util.Collection;

public class User {

    private Long id;

    private String document;

    private String name;

    private String email;

    private String password;

    private String address;

    private String phone;

    private Collection<Area> areas;

    private Collection<RefreshToken> tokens;

    public User() {}

    public User(
        Long id,
        String document,
        String name,
        String email,
        String password,
        String address,
        String phone,
        Collection<Area> areas,
        Collection<RefreshToken> tokens
    ) {
        this.id = id;
        this.document = document;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.areas = areas;
        this.tokens = tokens;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Collection<Area> getAreas() {
        return areas;
    }

    public void setAreas(Collection<Area> areas) {
        this.areas = areas;
    }

    public Collection<RefreshToken> getTokens() {
        return tokens;
    }

    public void setTokens(Collection<RefreshToken> tokens) {
        this.tokens = tokens;
    }

    public boolean passwordMatches(String rawPassword, PasswordService hasher) {
        return hasher.matches(rawPassword, this.password);
    }

    /**
     * Check if this user has admin privileges
     * This method delegates to AdminAuthService for admin detection
     */
    public boolean isAdmin(AdminAuthService adminAuthService) {
        return adminAuthService.isAdminCredentials(this.email);
    }
}
