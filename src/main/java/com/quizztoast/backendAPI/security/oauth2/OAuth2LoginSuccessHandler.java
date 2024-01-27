package com.quizztoast.backendAPI.security.oauth2;

import com.quizztoast.backendAPI.model.entity.user.Role;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.repository.UserRepository;
import com.quizztoast.backendAPI.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserServiceImpl userServiceImpl;
    @Autowired
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws ServletException, IOException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String oauth2ClientName = token.getAuthorizedClientRegistrationId();
        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oauth2User.getAttributes();
        String email = oauth2User.getEmail();
        String givenName = attributes.getOrDefault("given_name", "").toString();
        String familyName = attributes.getOrDefault("family_name", "").toString();
        System.out.println(attributes);
        User user = userServiceImpl.getUserByEmail(email);
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setLastName(familyName);
            user.setFirstName(givenName);
            user.setRole(Role.USER);
            userRepository.save(user);
        }

        userServiceImpl.updateAuthenticationType(email, oauth2ClientName);

        setAlwaysUseDefaultTargetUrl(true);
        String frontendUrl = "http://localhost:5173";
        setDefaultTargetUrl(frontendUrl);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
