package com.hendisantika.controller;

import com.hendisantika.service.ClientService;
import com.hendisantika.service.UploadFileService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
 * Time: 11.06
 */
@Controller
@SessionAttributes("client")
public class ClientController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private ClientService clientService;

    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private MessageSource messageSource;
}
