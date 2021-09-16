package com.hendisantika.service;

import com.hendisantika.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

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

}
