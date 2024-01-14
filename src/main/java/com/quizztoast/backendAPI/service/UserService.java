package com.quizztoast.backendAPI.service;

import com.quizztoast.backendAPI.model.user.User;
import com.quizztoast.backendAPI.repository.UserRepository;
import com.quizztoast.backendAPI.security.auth_payload.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public void changePassword(ChangePasswordRequest request, Principal connectedUser){

        var user = ((User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal());
        //Check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(),user.getPassword())){
            throw new IllegalStateException("Wrong password");
        }
        //Check if 2 password are matched
        if(!request.getNewPassword().equals(request.getConfirmPassword())){
            throw new IllegalStateException("Password are not the same");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

}
