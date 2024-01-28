package com.quizztoast.backendAPI.model.entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.quizztoast.backendAPI.model.entity.user.Permission.*;

@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE
            )
    )
    ;


    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.addAll(
                getPermissions()
                        .stream()
                        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                        .collect(Collectors.toList())
        );

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }

}
