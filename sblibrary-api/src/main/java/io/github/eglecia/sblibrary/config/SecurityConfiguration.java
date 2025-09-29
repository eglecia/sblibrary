package io.github.eglecia.sblibrary.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    public SecurityFilterChain securityFilterChain(HttpSecurity httpsec) throws Exception {
        return httpsec
                .csrf(AbstractHttpConfigurer::disable)
                //.formLogin(Customizer.withDefaults()) //Habilita formLogin padrão
                .formLogin(configurer -> {
                    //Por padrão ele guarda um cookie de sessão por meio hora
                    configurer.loginPage("/login").permitAll();
                })
                .httpBasic(Customizer.withDefaults()) //Habilita autenticação básica entre aplicações
                .authorizeHttpRequests(authorize -> {
                    authorize.anyRequest().authenticated();
                })
                .build();
    }
}
