package io.github.eglecia.sblibrary.security;

import io.github.eglecia.sblibrary.model.User;
import io.github.eglecia.sblibrary.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final String DEFAULT_PASSWORD = "123";

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oauth2Token.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        User user = userService.findByEmail(email);

        //Faz o cadastro de usuário não cadastrado
        if (user == null) {
            user = registerUser(email);
        }

        authentication = new CustomAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private User registerUser(String email) {
        User user;
        user = new User();
        user.setLogin(getLoginFromEmail(email));
        user.setEmail(email);
        user.setPassword(DEFAULT_PASSWORD);
        user.setRoles(List.of("USER"));
        userService.save(user);
        return user;
    }

    private String getLoginFromEmail(String email) {
        if(email != null && email.contains("@")) {
            return email.split("@")[0];
        }
        return email;
    }
}
