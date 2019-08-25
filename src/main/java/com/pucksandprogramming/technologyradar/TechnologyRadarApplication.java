package com.pucksandprogramming.technologyradar;

import com.pucksandprogramming.technologyradar.security.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import(WebSecurityConfig.class)
@SpringBootApplication
public class TechnologyRadarApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechnologyRadarApplication.class, args);
    }
}