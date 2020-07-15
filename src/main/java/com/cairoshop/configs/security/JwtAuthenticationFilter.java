package com.cairoshop.configs.security;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cairoshop.persistence.entities.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class JwtAuthenticationFilter
            extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final String jwtSecret;

    private final String apiName;

    private final long jwtExpiration;

    private final Utils utils;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, String jwtSecret, String apiName, long jwtExpiration, Utils utils) {
        this.authenticationManager = authenticationManager;
        this.jwtSecret = jwtSecret;
        this.apiName = apiName;
        this.jwtExpiration = jwtExpiration;
        this.utils = utils;
        setFilterProcessesUrl("/authenticate");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY);
        String password = request.getParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY);
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<String> roles = user.getAuthorities()
                                    .stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .collect(Collectors.toList());
        var token = Jwts.builder()
                        .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                        .setHeaderParam("typ", "jwt")
                        .setIssuer(apiName)
                        .setSubject(user.getUsername())
                        .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                        .claim("rol", roles)
                        .compact();
        response.addHeader(Constants.AUTHORIZATION_HEADER_KEY, Constants.AUTHORIZATION_HEADER_VALUE_PREFIX + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
                        throws IOException {
        utils.generateResponseFrom(response, HttpStatus.UNAUTHORIZED.value(), "Invalid credentials");
    }

}
