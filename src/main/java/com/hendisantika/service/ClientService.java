package com.hendisantika.service;

import com.hendisantika.entity.Client;
import com.hendisantika.entity.Invoice;
import com.hendisantika.entity.Product;
import com.hendisantika.repository.ClientRepository;
import com.hendisantika.repository.InvoiceRepository;
import com.hendisantika.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    private final ProductRepository productRepository;

    private final InvoiceRepository invoiceRepository;

    /*----- Method List -----*/
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    /*----- Paginator -----*/
    public Page<Client> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    /*----- Method Find By ID -----*/
    public Client findOne(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public Client fetchByIdWithInvoices(Long id) {
        return clientRepository.fetchByIdWithInvoices(id);
    }

    /*----- Method Save -----*/
    @Transactional
    public void save(Client client) {
        clientRepository.save(client);
    }

    /*----- Method Delete -----*/
    @Transactional
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    /*----- Method Find by Name (Product) -----*/
    public List<Product> findByName(String term) {
        return productRepository.findByName(term);
    }

    /*----- Method Save (Invoice) -----*/
    @Transactional
    public void saveInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    /*----- Method Find Product by ID -----*/
    public Product findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    /*----- Method Find by ID (Invoice) -----*/
    public Invoice findInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    /*----- Method Delete (Invoice) -----*/
    @Transactional
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }

    /*----- Method Fetch Invoice with Client with Invoice -----*/
    @Transactional(readOnly = true)
    public Invoice fetchInvoiceByIdWithClientWithInvoiceItemWithProduct(Long id) {
        return invoiceRepository.fetchByIdWithClientWithInvoiceItemWithProduct(id);
    }
}
