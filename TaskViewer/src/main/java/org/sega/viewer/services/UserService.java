package org.sega.viewer.services;

import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.sega.viewer.models.User;
import org.sega.viewer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @PostConstruct
    protected void initialize() {
        if (userRepository.findByEmail("user") == null) {
            userRepository.save(new User("user", "demo", User.ROLE_OPERATOR));
            userRepository.save(new User("admin", "admin", User.ROLE_ADMIN));
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with name " + username +" is not found.");
        }
        return createUser(user);
    }

    public void signin(User user) {
        SecurityContextHolder.getContext().setAuthentication(authenticate(user));
    }

    private Authentication authenticate(User user) {
        return new UsernamePasswordAuthenticationToken(
                createUser(user),
                null,
                Collections.singleton(createAuthority(user)));
    }

    private org.springframework.security.core.userdetails.User createUser(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(createAuthority(user)));
    }

    private GrantedAuthority createAuthority(User user) {
        return new SimpleGrantedAuthority(user.getRole());
    }

}
