package com.cairoshop.configs.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
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
        String token = request.getHeader(Constants.AUTHORIZATION_HEADER_KEY);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.AUTHORIZATION_HEADER_VALUE_PREFIX)) {
            Jws<Claims> parsedToken = Jwts.parserBuilder()
                                            .setSigningKey(jwtSecret.getBytes())
                                            .build()
                                            .parseClaimsJws(token.replace(Constants.AUTHORIZATION_HEADER_VALUE_PREFIX, ""));
            String username = parsedToken.getBody().getSubject();
            List<SimpleGrantedAuthority> authorities = ((List<String>) parsedToken.getBody().get("rol"))
                                                            .stream()
                                                            .map(authority -> new SimpleGrantedAuthority(authority))
                                                            .collect(Collectors.toList());
            if (StringUtils.isEmpty(username) && authorities.isEmpty()) return;
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, authorities));
        }
        filterChain.doFilter(request, response);
    }

}
