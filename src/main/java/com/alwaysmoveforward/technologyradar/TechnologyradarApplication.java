package com.alwaysmoveforward.technologyradar;

import com.alwaysmoveforward.technologyradar.security.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import(WebSecurityConfig.class)
@SpringBootApplication
public class TechnologyradarApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechnologyradarApplication.class, args);
	}
}
