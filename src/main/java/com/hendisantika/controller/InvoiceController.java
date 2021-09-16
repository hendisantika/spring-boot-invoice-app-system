package com.hendisantika.controller;

import com.hendisantika.entity.Client;
import com.hendisantika.entity.Invoice;
import com.hendisantika.entity.ItemInvoice;
import com.hendisantika.entity.Product;
import com.hendisantika.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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

    /* ----- Save Invoice ----- */
    @PostMapping("/form")
    public String save(@Valid Invoice invoice, BindingResult result, Model model,
                       @RequestParam(name = "item_id[]", required = false) Long[] itemId,
                       @RequestParam(name = "amount[]", required = false) Integer[] amount, RedirectAttributes flash,
                       SessionStatus status, Locale locale) {

        if (result.hasErrors()) {
            model.addAttribute("title", messageSource.getMessage("text.invoice.form.title", null, locale));
            return "invoice/form";
        }

        if (itemId == null || itemId.length == 0) {
            model.addAttribute("title", messageSource.getMessage("text.invoice.form.title", null, locale));
            model.addAttribute("error", messageSource.getMessage("text.invoice.flash.lines.error", null, locale));
            return "invoice/form";
        }

        for (int i = 0; i < itemId.length; i++) {
            Product product = clientService.findProductById(itemId[i]);

            ItemInvoice line = new ItemInvoice();
            line.setAmount(amount[i]);
            line.setProduct(product);
            invoice.addItemInvoice(line);

            log.info("ID: " + itemId[i].toString() + ", amount: " + amount[i].toString());
        }

        clientService.saveInvoice(invoice);
        status.setComplete();

        flash.addFlashAttribute("success", messageSource.getMessage("text.invoice.flash.create.success", null, locale));

        return "redirect:/view/" + invoice.getClient().getId();
    }

    /* ----- Delete Invoice ----- */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash, Locale locale) {
        Invoice invoice = clientService.findInvoiceById(id);

        if (invoice != null) {
            clientService.deleteInvoice(id);
            flash.addAttribute("success", messageSource.getMessage("text.invoice.flash.delete.success", null, locale));
            return "redirect:/view/" + invoice.getClient().getId();
        }

        flash.addFlashAttribute("error", messageSource.getMessage("text.invoice.flash.db.error", null, locale));
        return "redirect:/list/";
    }
}
