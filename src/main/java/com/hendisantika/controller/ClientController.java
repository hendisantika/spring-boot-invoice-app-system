package com.hendisantika.controller;

import com.hendisantika.entity.Client;
import com.hendisantika.service.ClientService;
import com.hendisantika.service.UploadFileService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.MalformedURLException;
import java.util.Locale;

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

    /* ----- View Photo ----- */
    // '.+' = returns the file name without format
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/uploads/{filename:.+}")
    public ResponseEntity<Resource> viewPhoto(@PathVariable String filename) {

        Resource resource = null;

        try {
            resource = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    /* ----- View Clients Details ----- */
    @Secured("ROLE_USER")
    @GetMapping(value = "/view/{id}")
    public String view(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash, Locale locale) {

        Client client = clientService.fetchByIdWithInvoices(id);
        if (client == null) {
            flash.addFlashAttribute("error", messageSource.getMessage("text.client.flash.db.error", null, locale));
            return "redirect:/list";
        }

        model.addAttribute("client", client);
        model.addAttribute("title",
                messageSource.getMessage("text.client.list.title", null, locale) + ": " + client.getFirstName());

        return "view";
    }

}
