package com.aryan.service;

import com.aryan.model.User;
import com.aryan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Custom implementation of {@link UserDetailsService}.
 *
 * Responsible for loading user credentials and authorities
 * from the database during the authentication process.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Loads a user by their email address.
     *
     * Spring Security invokes this method during authentication
     * to retrieve the user's credentials and granted authorities.
     *
     * @param email user's registered email address
     * @return authenticated user details
     * @throws UsernameNotFoundException if no user exists with the provided email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with email " + email
                        ));

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().toString());
        Collection<GrantedAuthority> grantedAuthorities  = Collections.singletonList(authority);
        return new org.springframework.security.core.userdetails.User(
             user.getEmail(), user.getPassword(), grantedAuthorities
        );
    }
}
