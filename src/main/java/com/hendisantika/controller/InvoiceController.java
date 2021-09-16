package com.hendisantika.controller;

import com.hendisantika.entity.Client;
import com.hendisantika.entity.Invoice;
import com.hendisantika.entity.Product;
import com.hendisantika.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app-system
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 16/09/21
 * Time: 11.18
 */
@Log4j2
@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/invoice")
@SessionAttributes("invoice")
public class InvoiceController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private MessageSource messageSource;

    /* ----- View Invoice[id] ----- */
    @GetMapping("/view/{id}")
    public String view(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash, Locale locale) {

        Invoice invoice = clientService.fetchInvoiceByIdWithClientWithInvoiceItemWithProduct(id);

        if (invoice == null) {
            flash.addAttribute("error", messageSource.getMessage("text.invoice.flash.db.error", null, locale));
            return "redirect:/list";
        }

        model.addAttribute("invoice", invoice);
        model.addAttribute("title", String.format(messageSource.getMessage("text.invoice.view.title", null, locale),
                invoice.getDescription()));

        return "invoice/view";
    }

    /* ----- Create Invoice for Client[id] ----- */
    @GetMapping("/form/{clientId}")
    public String create(@PathVariable(value = "clientId") Long clientId, Map<String, Object> model,
                         RedirectAttributes flash, Locale locale) {

        Client client = clientService.findOne(clientId);

        if (client == null) {
            flash.addAttribute("error", messageSource.getMessage("text.client.flash.db.error", null, locale));
            return "redirect:/list";
        }

        Invoice invoice = new Invoice();
        invoice.setClient(client);

        model.put("invoice", invoice);
        model.put("title", messageSource.getMessage("text.invoice.form.title", null, locale));

        return "invoice/form";
    }

    /* ----- Autocomplete for Finding Products (autocomplete-products.js)----- */
    @GetMapping(value = "/load-products/{term}", produces = {"application/json"})
    public @ResponseBody
    List<Product> loadProducts(@PathVariable String term) {
        return clientService.findByName(term);
    }

}
