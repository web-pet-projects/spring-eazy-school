package com.quentin.eazyschool.controller;

import com.quentin.eazyschool.constants.EazySchoolConstants;
import com.quentin.eazyschool.model.Contact;
import com.quentin.eazyschool.payload.ContactDTO;
import com.quentin.eazyschool.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("contact")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public String displayContactPage(Model model) {
        model.addAttribute("contact", new ContactDTO());
        return "contact";
    }

    @PostMapping
    public String handleSaveMsg(@Valid @ModelAttribute("contact") ContactDTO contactDTO, Errors errors) {
        if (errors.hasErrors()) {
            log.error("Contact form has error: {}", errors.toString());
            log.error("Errors encountered while saving contact {}", errors.getAllErrors());
            return "contact";
        }
        boolean isSaved = contactService.saveMessage(contactDTO);
        if (!isSaved) {
            return "contact";
        }
        return "redirect:/contact";
    }

    @GetMapping("/messages")
    public String displayMessages( Model model,
                                   @RequestParam(value = "page", defaultValue = EazySchoolConstants.DEFAULT_PAGE_NUMBER) int page,
                                   @RequestParam(value = "limit", defaultValue = EazySchoolConstants.DEFAULT_PAGE_SIZE) int limit,
                                   @RequestParam(value = "sort", defaultValue = EazySchoolConstants.DEFAULT_SORT) String[] sort) {
//        List<ContactDTO> messages = contactService.fetchAllByStatus(Contact.Status.OPEN );
        Page<ContactDTO> contactDTOPage = contactService.fetchPageByStatus(Contact.Status.OPEN, page - 1, limit, sort);
        model.addAttribute("currentPage", contactDTOPage.getNumber() + 1);
        model.addAttribute("reverseSortDir", sort[1].equals("asc") ? "desc" : "asc");
        model.addAttribute("messages", contactDTOPage.getContent());
        model.addAttribute("totalPages", contactDTOPage.getTotalPages());
        model.addAttribute("sort", sort);
        return "messages";
    }

    @GetMapping("/messages/close")
    public String handleCloseMsg(@RequestParam("id") Long contactId) {
        boolean isSaved = contactService.closeMessage(contactId);
        if (!isSaved) {
            return "messages";
        }
        return "redirect:/contact/messages";
    }
}
