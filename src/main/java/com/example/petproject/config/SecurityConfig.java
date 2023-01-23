package com.example.petproject.config;

import com.example.petproject.config.jwt.AuthEntryPointJwt;
import com.example.petproject.config.jwt.AuthTokenFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final AuthEntryPointJwt authEntryPointJwt;
    private final AuthTokenFilter authTokenFilter;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

   @Override
   public void configure(WebSecurity web) throws Exception {
       web.ignoring().mvcMatchers(HttpMethod.OPTIONS, "/**")
               .mvcMatchers("/swagger-ui.html/**", "/configuration/**", "/swagger-resources/**", "/v2/api-docs");
   }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authEntryPointJwt)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/auth/**", "/swagger-ui.html/**", "/configuration/**", "/swagger-resources/**", "/v2/api-docs")
                .permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
