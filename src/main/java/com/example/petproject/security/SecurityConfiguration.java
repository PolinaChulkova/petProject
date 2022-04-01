package com.example.petproject.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {
//
//    private UserDetailsService userDetailsService;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService()).passwordEncoder(encoder());
//    }
//
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable().authorizeRequests().anyRequest().permitAll();
//        http.csrf().disable().authorizeRequests()
//                .antMatchers("/user").not().fullyAuthenticated()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().loginPage("/login").defaultSuccessUrl("/news/all")
//                .and()
//                .logout().permitAll();
    }
//
//    @Bean
//    public BCryptPasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
}
