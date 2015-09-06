package org.sega.viewer.services;

import java.util.Collections;

import javax.annotation.PostConstruct;

import org.sega.viewer.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;

public class UserService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private IModel model;

    @PostConstruct
    protected void initialize() {
//        Account user = accountRepository.findByEmail("user");

//        if (model.findAll(Account.class).isEmpty()) {
//            accountRepository.save(new Account("user", "demo", "ROLE_OPERATOR"));
//            accountRepository.save(new Account("admin", "admin", "ROLE_ADMIN"));
//        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username);
        if (account == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return createUser(account);
    }

    public void signin(Account account) {
        System.out.println(account.getEmail());
        System.out.println(account.getPassword());
        System.out.println(account.getRole());
        SecurityContextHolder.getContext().setAuthentication(authenticate(account));
    }

    private Authentication authenticate(Account account) {
        System.out.println(account.getEmail());
        System.out.println(account.getPassword());
        System.out.println(account.getRole());
        return new UsernamePasswordAuthenticationToken(createUser(account), null, Collections.singleton(createAuthority(account)));
    }

    private User createUser(Account account) {
        return new User(account.getEmail(), account.getPassword(), Collections.singleton(createAuthority(account)));
    }

    private GrantedAuthority createAuthority(Account account) {
        return new SimpleGrantedAuthority(account.getRole());
    }

}
