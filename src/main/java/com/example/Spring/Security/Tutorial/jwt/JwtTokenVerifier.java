package com.example.Spring.Security.Tutorial.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtTokenVerifier extends OncePerRequestFilter {
    public JwtTokenVerifier(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    private final JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (!Strings.isNullOrEmpty(authorizationHeader) || authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
            try {
                JWTVerifier verifier = JWT.require(HMAC512(jwtConfig.getSecretKey().getBytes()))
                        .build();
                DecodedJWT verify = verifier.verify(token);
                final String sub = verify.getSubject();
                Claim authorityClaim = verify.getClaim("authorities");
                String[] authorities = authorityClaim.asArray(String.class);
                Set<SimpleGrantedAuthority> simpleGrantedAuthorities = Lists
                        .newArrayList(authorities)
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        sub,
                        null,
                        simpleGrantedAuthorities
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JWTVerificationException e) {
                throw new IllegalArgumentException(String.format("Token %s cannot be trusted", token));
            }
        }
        filterChain.doFilter(request, response);
    }
}
