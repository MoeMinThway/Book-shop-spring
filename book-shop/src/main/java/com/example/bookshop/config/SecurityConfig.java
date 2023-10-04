package com.example.bookshop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private  final UserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity htpp) throws Exception {

        htpp.formLogin(c -> c.loginPage("/login")
                        .failureUrl("/login-error")
                        .permitAll())

                .logout(c -> c.logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll());
        htpp.authorizeHttpRequests(c -> c.requestMatchers("/account/**")
                .hasRole("USER")
                .anyRequest().permitAll()
        );
        return htpp.build();
    }
/*    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

    *//*    http.formLogin(
                        c->c.loginPage("/login")
                                .failureUrl("/login-error")
                                .permitAll())
                .logout(
                        c->c.logoutUrl("/logout")
                                .logoutSuccessUrl("/")
                                .permitAll());

        http.authorizeHttpRequests(c->
                c.requestMatchers("/account/**")
                .hasRole("USER")
                        .anyRequest().permitAll());*//*
        http.formLogin(Customizer.withDefaults());
        http.authorizeHttpRequests(c->c.anyRequest().permitAll());
        return http.build();

    }*/
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
}
