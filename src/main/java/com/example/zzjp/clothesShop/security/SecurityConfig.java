package com.example.zzjp.clothesShop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .httpBasic().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/users/").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/users/admin/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/users/", "/api/v1/users/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/categories/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/categories/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/categories/").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/items/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/items/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/items/").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/items/**").permitAll()
                .antMatchers("/api/v1/**").authenticated()
                .anyRequest().permitAll();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.csrf()
                .disable();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
