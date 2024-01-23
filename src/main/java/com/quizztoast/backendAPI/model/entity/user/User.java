package com.quizztoast.backendAPI.model.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.quizztoast.backendAPI.model.entity.token.Token;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {
// Spring boot -> ORM -> JPA Api (Query: JPQL) -> Hibernate (HQL) -> SQL -> RDBMS
    @Id
    @GeneratedValue
    @Column (name = "user_id")
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    @Email(message = "Invalid email address")
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "is_banned")
    private boolean isBanned;

    @Column(name = "google_id")
    private String googleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "is_premium")
    private boolean isPremium;

    @Column(name = "mfa_enabled")
    private boolean mfaEnabled;

    @Column(name = "secret")
    private String secret;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
