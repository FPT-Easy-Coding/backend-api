package com.quizztoast.backendAPI.service;

import com.quizztoast.backendAPI.model.user.User;
import com.quizztoast.backendAPI.repository.UserRepository;
import com.quizztoast.backendAPI.security.auth_payload.ChangePasswordRequest;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

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

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        User user =  userRepository.findByUserId(id);
        if(user == null){
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        return user;
    }
    public void addNewUser(@RequestBody User user) {
        userRepository.save(user);
    }

    public boolean userExists(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public void deleteUser(int userId) {
        userRepository.deleteById((long) userId);
    }

    public boolean doesUserExist(int userId) {
        // Implement logic to check if the user with the given ID exists
        // For example, you might check if a user with the provided ID is present in your repository
        Optional<User> userOptional = userRepository.findById((long) userId);
        return userOptional.isPresent();
    }

    public boolean doesEmailExist(String email) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        return existingUser.isPresent();
    }

    public boolean doesUsernameExist(String username) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        return existingUser.isPresent();
    }
}
