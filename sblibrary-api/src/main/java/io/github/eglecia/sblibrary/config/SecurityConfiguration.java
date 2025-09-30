package io.github.eglecia.sblibrary.config;

import io.github.eglecia.sblibrary.security.UserSecurity;
import io.github.eglecia.sblibrary.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpsec) throws Exception {
        return httpsec
                .csrf(AbstractHttpConfigurer::disable)
                //.formLogin(Customizer.withDefaults()) //Habilita formLogin padrão
                .formLogin(configurer -> {
                    //Por padrão ele guarda um cookie de sessão por meio hora
                    configurer.loginPage("/login");
                })
                .httpBasic(Customizer.withDefaults()) //Habilita autenticação básica entre aplicações
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/api/v1/users/**").permitAll(); //Para Teste: Permite criar usuários sem autenticação
                    authorize.requestMatchers("/api/v1/authors/**").hasRole("ADMIN");
                    authorize.requestMatchers("/api/v1/books/**").hasAnyRole("USER", "ADMIN");
                    authorize.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    //Teste em memória
    //@Bean
    public UserDetailsService userDetailsServiceMem(PasswordEncoder passwordEncoder) {
        UserDetails user1 = User.builder()
                .username("user")
                .password(passwordEncoder.encode("123"))
                .roles("USER")
                .build();

        UserDetails user2 = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("321"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new UserSecurity(userService);
    }
}
