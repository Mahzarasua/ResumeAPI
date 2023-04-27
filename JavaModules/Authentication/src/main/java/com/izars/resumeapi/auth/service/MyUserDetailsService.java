package com.izars.resumeapi.auth.service;

import com.izars.resumeapi.auth.config.MyUserDetails;
import com.izars.resumeapi.auth.exception.CustomNotFoundException;
import com.izars.resumeapi.auth.exception.ExceptionBody;
import com.izars.resumeapi.auth.mapper.CustomMapper;
import com.izars.resumeapi.auth.model.User;
import com.izars.resumeapi.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    private final CustomMapper mapper;

    public MyUserDetailsService(UserRepository userRepository, CustomMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            log.warn("User {} not found", username);
            throw new CustomNotFoundException(
                    new ExceptionBody.ErrorDetails("credentials", "You have entered an invalid username or password")
                    , "Bad credentials");
        }
        return mapper.map(user.get(), MyUserDetails.class);
    }
}
