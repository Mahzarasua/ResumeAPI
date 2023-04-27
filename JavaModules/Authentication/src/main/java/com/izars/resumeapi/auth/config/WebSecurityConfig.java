package com.izars.resumeapi.auth.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
@OpenAPIDefinition(
        info = @Info(title = "${info.app.name}"
                , version = "${info.app.version}"
                , description = "${info.app.description}"
                , license = @License(name = "${info.app.license.name}", url = "${info.app.license.url}")
                , contact = @Contact(name = "${info.app.contact.name}", url = "${info.app.contact.url}", email = "${info.app.contact.email}"))
)
public class WebSecurityConfig {
    public static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration**",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui**",
            "/openapi**",
            "/openapi/**",
            "/admin**",
            "/admin/**"
            // other public endpoints of your API may be appended to this array
    };

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtRequestFilter jwtRequestFilter;

    private final CustomAuthenticationManager customAuthenticationManager;

    public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtRequestFilter jwtRequestFilter, CustomAuthenticationManager customAuthenticationManager) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtRequestFilter = jwtRequestFilter;
        this.customAuthenticationManager = customAuthenticationManager;
    }

    @Bean
    @Primary
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authz) ->
                        {
                            try {
                                authz.requestMatchers("/authenticate").permitAll()
                                        .requestMatchers(AUTH_WHITELIST).permitAll()
                                        .anyRequest().authenticated().and()
                                        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .httpBasic(withDefaults())
                .authenticationManager(customAuthenticationManager);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
