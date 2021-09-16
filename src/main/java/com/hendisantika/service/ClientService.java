package com.hendisantika.service;

import com.hendisantika.repository.ClientRepository;
import com.hendisantika.repository.InvoiceRepository;
import com.hendisantika.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app-system
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 16/09/21
 * Time: 09.55
 */
@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;


}
