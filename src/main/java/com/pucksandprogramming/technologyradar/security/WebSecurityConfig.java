package com.pucksandprogramming.technologyradar.security;

import com.auth0.AuthenticationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.io.UnsupportedEncodingException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    Environment env;

    @Value("${com.auth0.domain}")
    private String domain;

    @Value("${com.auth0.clientId}")
    private String clientId;

    @Value("${com.auth0.clientSecret}")
    private String clientSecret;

    @Value("${com.auth0.callbackUrl}")
    private String callbackLocation;

    @Value("${com.pucksandprogramming.securityEnabled}")
    private boolean securityEnabled;

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
        return AuthenticationController.newBuilder(domain, clientId, clientSecret)
                .build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new RadarAccessDeniedHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        if(this.securityEnabled==true)
        {
            http
                    .authorizeRequests()
                    .antMatchers("/script/**",
                            "/css/**",
                            "/webjars/**",
                            "/Images/**", "/images/**",
                            "/favicon.ico").permitAll()
                    .antMatchers(callbackLocation,
                            "/login",
                            "/accessDenied").permitAll()
                    .antMatchers( HttpMethod.GET, "/", "/public/**", "/api/public/**").permitAll()
                    .antMatchers("/**").authenticated()
                    .and().exceptionHandling().accessDeniedPage("/accessDenied")
                    .and().logout().permitAll();
        }
        else
        {
            http
                    .authorizeRequests()
                    .antMatchers("/script/**",
                            "/css/**",
                            "/webjars/**",
                            "/Images/**", "/images/**",
                            "/favicon.ico").permitAll()
                    .antMatchers(callbackLocation,
                            "/login",
                            "/accessDenied").permitAll()
                    .antMatchers(HttpMethod.GET, "/", "/public/**", "/api/public/**").permitAll()
                    .antMatchers("/**").permitAll()
                    .and().logout().permitAll();
        }

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
    }

    private boolean shouldUseEnvironmentForConfiguration()
    {
        boolean retVal = false;

        String propertyValue = env.getProperty("com.pucksandprogramming.useEnvironmentForConfiguration");

        retVal = Boolean.parseBoolean(propertyValue);

        return retVal;
    }

    public String getDomain()
    {
        String retVal = domain;

        if(this.shouldUseEnvironmentForConfiguration())
        {
            retVal = env.getProperty("AUTH0_DOMAIN");
        }

        return retVal;
    }

    public String getClientId()
    {
        String retVal = clientId;

        if(this.shouldUseEnvironmentForConfiguration())
        {
            retVal = env.getProperty("AUTH0_CLIENT_ID");
        }

        return retVal;
    }

    public String getClientSecret()
    {
        String retVal = clientSecret;

        if(this.shouldUseEnvironmentForConfiguration())
        {
            retVal = env.getProperty("AUTH0_CLIENT_SECRET");
        }

        return retVal;
    }
}

