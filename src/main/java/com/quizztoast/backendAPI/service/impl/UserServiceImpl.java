package com.quizztoast.backendAPI.service.impl;

import com.quizztoast.backendAPI.model.entity.user.Provider;
import com.quizztoast.backendAPI.model.entity.user.User;

import com.quizztoast.backendAPI.repository.UserRepository;
import com.quizztoast.backendAPI.security.auth.auth_payload.ChangePasswordRequest;
import com.quizztoast.backendAPI.security.auth.auth_payload.RegisterRequest;
import com.quizztoast.backendAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

import java.security.Principal;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository ) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
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

    @Override
    public User createUser(RegisterRequest user) {
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setUserName(user.getUserName());
        newUser.setTelephone(user.getTelephone());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        newUser.setMfaEnabled(user.isMfaEnabled());
        newUser.setProvider(Provider.LOCAL);
        newUser.setCreatedAt(new Date());
        return userRepository.save(newUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        User user =  userRepository.findByUserId(id);
        if(user == null){
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User addNewUser(User user) {
        User newUser = new User();
        newUser.setUserName(user.getUsername());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        newUser.setCreatedAt(new Date());
        newUser.setProvider(Provider.LOCAL);
        newUser.setTelephone(user.getTelephone());
        newUser.setPremium(user.isPremium());
        newUser.setBanned(user.isBanned());
        return userRepository.save(newUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById( userId);
    }

    @Override
    public boolean doesUserExist(Long userId) {
        Optional<User> userOptional = userRepository.findById( userId);
        return userOptional.isPresent();
    }

    @Override
    public boolean doesEmailExist(String email) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        return existingUser.isPresent();
    }

    @Override
    public boolean doesUsernameExist(String username) {
        Optional<User> existingUser = userRepository.findByUserName(username);
        return existingUser.isPresent();
    }



    @Override
    public boolean userExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }


    @Override
    public void updateAuthenticationType(String username, String oauth2ClientName) {
        Provider authType = Provider.valueOf(oauth2ClientName.toUpperCase());
        userRepository.updateAuthenticationType(username, authType);
    }

    @Override
    public User updateUser(Long userId, User user) {
        User existingUser = userRepository.findByUserId(userId);
        if (existingUser == null) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setTelephone(user.getTelephone());
        existingUser.setRole(user.getRole());
        existingUser.setBanned(user.isBanned());
        existingUser.setPremium(user.isPremium());
        return userRepository.save(existingUser);
    }
}
