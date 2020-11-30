package org.vdragun.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.vdragun.backend.security.filter.InitialAuthenticationFilter;
import org.vdragun.backend.security.filter.JwtAuthenticationFilter;
import org.vdragun.backend.security.provider.OtpAuthenticationProvider;
import org.vdragun.backend.security.provider.UsernamePasswordAuthenticationProvider;
import org.vdragun.backend.security.token.JwtTokenService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private OtpAuthenticationProvider otpAuthenticationProvider;

    @Autowired
    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(otpAuthenticationProvider)
                .authenticationProvider(usernamePasswordAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterAt(new InitialAuthenticationFilter(authenticationManager(), jwtTokenService), BasicAuthenticationFilter.class)
                .addFilterAfter(new JwtAuthenticationFilter(jwtTokenService), BasicAuthenticationFilter.class)
                .authorizeRequests().anyRequest().authenticated();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
