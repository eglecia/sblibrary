package io.github.eglecia.sblibrary.security;

import io.github.eglecia.sblibrary.model.User;
import io.github.eglecia.sblibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {
    private final UserService userService;

    public User getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth instanceof CustomAuthentication customAuth) {
            return customAuth.getUser();
        }

        return null;
    }
}
