package com.aryan.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class responsible for generating JSON Web Tokens (JWT).
 *
 * Creates signed JWTs containing authenticated user information
 * such as email, roles, and user identifier.
 */
@Component
public class JwtProvider {

    /**
     * Secret key used to sign JWTs.
     */
    private final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    /**
     * Generates a signed JWT for an authenticated user.
     *
     * The generated token contains:
     * - User email
     * - User roles
     * - User identifier
     * - Issue timestamp
     * - Expiration timestamp
     *
     * @param auth authenticated user details
     * @param userId unique user identifier
     * @return generated JWT
     */
    public String generateToken(Authentication auth, Long userId){
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);
        String jwt = Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .claim("email",auth.getName())
                .claim("authorities", roles)
                .claim("userId", userId)
                .signWith(key)
                .compact();

        return jwt;
    }

    /**
     * Converts granted authorities into a comma-separated string.
     *
     * @param authorities authenticated user's granted authorities
     * @return comma-separated list of authority names
     */
    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();

        for (GrantedAuthority authority : authorities){
            auths.add(authority.getAuthority());
        }

        return String.join(",",auths);
    }
}
