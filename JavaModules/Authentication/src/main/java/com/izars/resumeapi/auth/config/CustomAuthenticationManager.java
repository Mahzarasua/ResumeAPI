package com.izars.resumeapi.auth.config;

import com.izars.resumeapi.auth.exception.CustomBadRequestException;
import com.izars.resumeapi.auth.exception.ExceptionBody;
import com.izars.resumeapi.auth.service.MyUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    private final MyUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder encoder;

    public CustomAuthenticationManager(MyUserDetailsService userDetailsService, BCryptPasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    private static void credentialsException(String errorMessage) {
        throw new CustomBadRequestException(
                new ExceptionBody.ErrorDetails("credentials", errorMessage)
                , "Bad credentials");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = String.valueOf(authentication.getPrincipal());
        String password = String.valueOf(authentication.getCredentials());

        commonValidations(username, password);
        return new UsernamePasswordAuthenticationToken(username, password);
    }

    public UserDetails authentication(String username, String password) throws AuthenticationException {
        return commonValidations(username, password);
    }

    private UserDetails commonValidations(String username, String password) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!encoder.matches(password, userDetails.getPassword())) {
            credentialsException("You have entered an invalid username or password");
        }
        if (!userDetails.isEnabled()) {
            credentialsException("The user provided is disabled");
        }
        return userDetails;
    }
}
