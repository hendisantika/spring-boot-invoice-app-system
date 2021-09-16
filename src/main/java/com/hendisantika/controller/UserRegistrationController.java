package com.hendisantika.controller;

import com.hendisantika.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app-system
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 16/09/21
 * Time: 11.25
 */
@Controller
@SessionAttributes("user")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;
}
