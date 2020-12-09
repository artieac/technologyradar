package com.pucksandprogramming.technologyradar.security;

import com.auth0.AuthenticationController;
import com.pucksandprogramming.technologyradar.security.Auth0.Auth0Configuration;
import com.pucksandprogramming.technologyradar.security.jwt.JwtAuthorizationFilter;
import com.pucksandprogramming.technologyradar.security.jwt.JwtCookieManager;
import com.pucksandprogramming.technologyradar.security.jwt.JwtManager;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.io.UnsupportedEncodingException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {
    @Autowired
    Auth0Configuration auth0Configuration;

    @Autowired
    JwtManager jwtManager;

    @Autowired
    RadarUserService radarUserService;

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver
                = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public AuthenticationController authenticationController() throws UnsupportedEncodingException {
        return AuthenticationController.newBuilder(this.auth0Configuration.getDomain(), this.auth0Configuration.getClientId(), this.auth0Configuration.getClientSecret())
                .build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new RadarAccessDeniedHandler();
    }

    @Bean JwtAuthorizationFilter getJwtAuthorizationFilter() throws Exception{
        return new JwtAuthorizationFilter(this.authenticationManager(), this.jwtManager, this.radarUserService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAfter(this.getJwtAuthorizationFilter(), SecurityContextPersistenceFilter.class)
                .authorizeRequests()
                    .antMatchers("/script/**",
                            "/css/**",
                            "/webjars/**",
                            "/Images/**", "/images/**",
                            "/favicon.ico").permitAll()
                    .antMatchers(this.auth0Configuration.getCallbackLocation(),
                            "/login",
                            "/logout",
                            "/accessDenied",
                            "/v2/api-docs",
                            "/swagger-resources",
                            "/swagger-resources/**",
                            "/configuration/ui",
                            "/configuration/security",
                            "/swagger-ui.html",
                            "/webjars/**").permitAll()
                    .antMatchers( HttpMethod.GET, "/", "/public/**", "/api/public/**", "/error", "/error/**").permitAll()
                    .antMatchers("/**").authenticated()
                .and().exceptionHandling().accessDeniedPage("/error/accessdenied")
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .deleteCookies(JwtCookieManager.COOKIE_NAME, "JSESSIONID");
    }
}

