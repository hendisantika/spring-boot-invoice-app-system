package com.hendisantika.controller;

import com.hendisantika.entity.Client;
import com.hendisantika.service.ClientService;
import com.hendisantika.service.UploadFileService;
import com.hendisantika.util.PageRender;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
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
                messageSource.getMessage("text.client.lists.title", null, locale) + ": " + client.getFirstName());

        return "view";
    }

    /* ----- List Clients ----- */
    @GetMapping(value = {"/list", "/"})
    public String list(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
                       Authentication authentication, HttpServletRequest request, Locale locale) {

        // 2 Ways of seeing Roles
        // 1st Way
        if (authentication != null) {
            logger.info("Hello authenticated user, your username is: " + authentication.getName());
        }

        // 2nd Way(static)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logger.info(
                    "Using static form 'SecurityContextHolder.getContext (). GetAuthentication ();': Authenticated " +
                            "user, username: "
                            + auth.getName());
        }

        // 3 Ways of assigning Roles
        // 1st Way
        if (hasRole("ROLE_ADMIN")) {
            logger.info("Halo " + auth.getName() + " you have access");
        } else {
            logger.info("Halo " + auth.getName() + " You do not have access");
        }

        // 2nd Way
        SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request,
                "ROLE_");

        if (securityContext.isUserInRole("ADMIN")) {
            logger.info(
                    "Way using SecurityContextHolderAwareRequestWrapper: Hello " + auth.getName() + " you have access");
        } else {
            logger.info("Way using SecurityContextHolderAwareRequestWrapper: Hello " + auth.getName()
                    + " You do not have access");
        }

        // 3rd Way
        if (request.isUserInRole("ROLE_ADMIN")) {
            logger.info("Form using HttpServletRequest: Hello " + auth.getName() + " you have access");
        } else {
            logger.info("Form using HttpServletRequest: Hello " + auth.getName() + " You do not have access");
        }

        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Client> clients = clientService.findAll(pageRequest);
        PageRender<Client> pageRender = new PageRender<>("/list", clients);

        model.addAttribute("title", messageSource.getMessage("text.client.lists.title", null, locale));
        model.addAttribute("clients", clients);
        model.addAttribute("page", pageRender);
        return "list";
    }

    /* ----- Create Client ----- */
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/form")
    public String create(Model model, Locale locale) {
        Client client = new Client();
        model.addAttribute("client", client);
        model.addAttribute("title", messageSource.getMessage("text.client.form.title.create", null, locale));
        return "form";
    }

    /* ----- Edit Client ----- */
    // PreAuthorize <=> @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/form/{id}")
    public String update(@PathVariable(value = "id") Long id, RedirectAttributes flash, Model model, Locale locale) {

        Client client = null;

        // If Customer exist, find
        if (id > 0) {
            client = clientService.findOne(id);
            // If not exist, error
            if (client == null) {
                flash.addFlashAttribute("error", messageSource.getMessage("text.client.flash.db.error", null, locale));
                return "redirect:/list";
            }
        } else {
            flash.addFlashAttribute("error", messageSource.getMessage("text.client.flash.id.error", null, locale));
            return "redirect:/list";
        }

        model.addAttribute("client", client);
        model.addAttribute("title", messageSource.getMessage("text.client.form.title.edit", null, locale));

        return "form";
    }

    /* ----- Save Client ----- */
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/form")
    public String save(@Valid Client client, BindingResult result, Model model,
                       @RequestParam("file") MultipartFile photo, RedirectAttributes flash, SessionStatus status,
                       Locale locale) {

        if (result.hasErrors()) {
            model.addAttribute("title", messageSource.getMessage("text.client.form.title", null, locale));
            return "form";
        }

        /* ----- Upload Photo ----- */
        if (!photo.isEmpty()) {

            if (client.getId() != null
                    && client.getId() > 0
                    && client.getPhoto() != null
                    && client.getPhoto().length() > 0) {
                uploadFileService.delete(client.getPhoto());
            }

            String uniqueFilename = null;

            try {
                uniqueFilename = uploadFileService.copy(photo);
            } catch (IOException e) {
                e.printStackTrace();
            }

            flash.addFlashAttribute(
                    "info", messageSource.getMessage("text.client.flash.foto.upload.success", null, locale)
                            + "'" + uniqueFilename + "'");
            client.setPhoto(uniqueFilename);

        }

        String flashMsg = (client.getId() != null)
                ? messageSource.getMessage("text.client.flash.edit.success", null, locale)
                : messageSource.getMessage("text.client.flash.create.success", null, locale);

        clientService.save(client);
        status.setComplete();
        flash.addFlashAttribute("success", flashMsg);
        return "redirect:/list";
    }
}
