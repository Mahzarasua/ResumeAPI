package com.izars.resumeapi.auth.config;

import com.izars.resumeapi.auth.exception.CustomAuthException;
import com.izars.resumeapi.auth.exception.ExceptionBody;
import com.izars.resumeapi.auth.service.MyUserDetailsService;
import com.izars.resumeapi.auth.utils.SpringUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final MyUserDetailsService jwtUserDetailsService;


    private final JwtTokenUtil jwtTokenUtil;

    public JwtRequestFilter(MyUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    private static void filterException(String requestUri, HttpServletResponse response, CustomAuthException e) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ExceptionBody body = new ExceptionBody();
        body.setTimestamp(LocalDateTime.now());
        body.setStatusCode(response.getStatus());
        body.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        body.setMessage("Authentication failed, please review");
        body.setPath(requestUri.replace("uri=", ""));
        body.setDetails(e.getErrorDetails());
        response.getWriter().write(SpringUtils.OBJECT_MAPPER.writeValueAsString(body));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        try {
            validateRequestAndToken(request, requestTokenHeader);
            chain.doFilter(request, response);
        } catch (CustomAuthException e) {
            filterException(request.getRequestURI(), response, e);
        }
    }

    private void validateRequestAndToken(HttpServletRequest request, String requestTokenHeader) {
        String username = null;
        String jwtToken = null;

        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
                throw new CustomAuthException("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
                throw new CustomAuthException("JWT Token has expired");
            } catch (Exception e) {
                System.out.println("Generic exception");
                throw new CustomAuthException(e.getMessage());
            }
        } else {
            logger.warn("JWT Token does not include Bearer word");
            //throw new CustomAuthException("JWT Token does not include Bearer word");
        }

        // Once we get the token validated it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            // if token is valid configure Spring Security to manually set authentication
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null
                                , userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify that the current user is authenticated.
                // So it passes Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
    }
}
