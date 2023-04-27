package com.izars;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@SecurityScheme(name = "jwtAuth",
        scheme = "bearer",
        bearerFormat = "JWT",
        type = SecuritySchemeType.HTTP)
@EnableJpaRepositories("com.izars.resumeapi.*")
@ComponentScan(basePackages = {"com.izars.resumeapi.*"})
@EntityScan("com.izars.resumeapi.*")
public class ResumeAPI {
    public static void main(String[] args) {
        SpringApplication.run(ResumeAPI.class, args);
    }
}