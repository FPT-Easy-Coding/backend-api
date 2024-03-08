package com.quizztoast.backendAPI.model.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.quizztoast.backendAPI.model.entity.token.PasswordResetToken;
import com.quizztoast.backendAPI.model.entity.token.Token;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.jpa.repository.Modifying;
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
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    @NaturalId(mutable = true)
    @Email(message = "Invalid email address")
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "is_banned")
    private boolean isBanned;

    @Column(name = "is_verified")
    private boolean isVerified;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "is_premium")
    private boolean isPremium;

    @Column(name = "mfa_enabled")
    private boolean mfaEnabled;

    @Column(name = "secret")
    private String secret;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "avatar")
    private String avatar;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    public User(String userName, String password, String email, Role role) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
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

    public String getUserName() {
        return userName;
    }

    @Setter
    @Getter
    @OneToOne(mappedBy = "user")
    private PasswordResetToken passwordResetToken;

}
