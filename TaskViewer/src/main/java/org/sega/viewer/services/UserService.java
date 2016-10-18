package org.sega.viewer.services;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sega.viewer.models.User;
import org.sega.viewer.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        initialize();
    }

    public User createUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void initialize() {
		//String city = request.getParameter("city");
        if (userRepository.findByEmail("user") == null) {
        	
            createUser(new User("user", "demo", User.ROLE_OPERATOR));
            createUser(new User("admin", "admin" , User.ROLE_ADMIN));
        }
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	String name = username.split(",")[0];
    	RequestAttributes ra = RequestContextHolder.getRequestAttributes();  
	    HttpServletRequest request = ((ServletRequestAttributes)ra).getRequest(); 
	    request.getSession().setAttribute("city",username.split(",")[1] );
        User user = userRepository.findByEmail(name);
        if (user == null) {
            throw new UsernameNotFoundException("User with name " + name +" is not found.");
        }
        return createSecureUser(user);
    }

    public void signin(User user) {
        SecurityContextHolder.getContext().setAuthentication(authenticate(user));
    }

    private Authentication authenticate(User user) {
        return new UsernamePasswordAuthenticationToken(
                createSecureUser(user),
                null,
                Collections.singleton(createAuthority(user)));
    }

    private org.springframework.security.core.userdetails.User createSecureUser(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(createAuthority(user)));
    }

    private GrantedAuthority createAuthority(User user) {
        return new SimpleGrantedAuthority(user.getRole());
    }
}
