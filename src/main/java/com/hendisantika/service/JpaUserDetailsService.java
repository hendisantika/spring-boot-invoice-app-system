package com.hendisantika.service;

import com.hendisantika.entity.Role;
import com.hendisantika.entity.User;
import com.hendisantika.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app-system
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 16/09/21
 * Time: 10.11
 */
@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            logger.error("Login error: user does not exist '" + username + "'");
            throw new UsernameNotFoundException("Username " + username + "does not exist in the system");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role : user.getAuthorities()) {
            logger.info("Role: ".concat(role.getAuthority()));
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }

        if (authorities.isEmpty()) {
            logger.error("Login error: user '" + username + "' does not have assigned roles");
            throw new UsernameNotFoundException("Login error: user '" + username + "' does not have assigned roles");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getEnabled(), true, true, true, authorities);
    }
}
