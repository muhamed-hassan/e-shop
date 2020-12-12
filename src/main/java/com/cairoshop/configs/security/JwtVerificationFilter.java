package com.cairoshop.configs.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class JwtVerificationFilter
            extends OncePerRequestFilter {

    private final String jwtSecret;

    public JwtVerificationFilter(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        var token = request.getHeader(Constants.AUTHORIZATION_HEADER_KEY);
        if (token != null
                && !token.isBlank()
                && token.startsWith(Constants.AUTHORIZATION_HEADER_VALUE_PREFIX)) {
            var parsedToken = Jwts.parserBuilder()
                                                .setSigningKey(jwtSecret.getBytes())
                                                .build()
                                                .parseClaimsJws(token.replace(Constants.AUTHORIZATION_HEADER_VALUE_PREFIX, ""));
            var username = parsedToken.getBody().getSubject();
            var authorities = ((List<String>) parsedToken.getBody().get("rol"))
                                                            .stream()
                                                            .map(SimpleGrantedAuthority::new)
                                                            .collect(Collectors.toList());
            if (username.isBlank() && authorities.isEmpty()) return;
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, authorities));
        }
        filterChain.doFilter(request, response);
    }

}
