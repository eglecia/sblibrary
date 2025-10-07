package io.github.eglecia.sblibrary.security;

import io.github.eglecia.sblibrary.model.User;
import io.github.eglecia.sblibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userService.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário e/ou senha inválidos!");
        }

        String passwordHash = user.getPassword();

        if (passwordEncoder.matches(password, passwordHash)) {
            return new CustomAuthentication(user);
        }

        throw new UsernameNotFoundException("Usuário e/ou senha inválidos!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
