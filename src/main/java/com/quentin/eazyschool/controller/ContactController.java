package com.quentin.eazyschool.controller;

import com.quentin.eazyschool.payload.ContactDTO;
import com.quentin.eazyschool.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

//    @PostMapping("/saveMsg")
//    public ModelAndView handleSaveMsg(@RequestParam String name,
//                                      @RequestParam String mobileNum,
//                                      @RequestParam String email,
//                                      @RequestParam String subject,
//                                      @RequestParam String message) {
//        logger.info("Name: {}, mobileNum: {}, email: {}, subject: {}", name, mobileNum, email, subject);
//        logger.info("Message: {}", message);
//        return new ModelAndView("redirect:/contact");
//    }

    @PostMapping("/saveMsg")
    public String handleSaveMsg(@Valid @ModelAttribute("contact") ContactDTO contactDTO, Errors errors) {
        if (errors.hasErrors()) {
            log.error("Contact form has error: {}", errors.toString());
            log.error("Errors encountered while saving contact {}", errors.getAllErrors());
            return "contact";
        }
        log.info(contactDTO.toString());
        contactService.saveMessage(contactDTO);
        return "redirect:/contact";
    }

    @GetMapping("/displayMessages")
    public String displayMessages( Model model ) {
        List<ContactDTO> messages = contactService.findAllWithStatusOpen();
        model.addAttribute("contactMsgs", messages);
        return "messages";
    }

    @GetMapping("/closeMsg")
    public String handleCloseMsg(@RequestParam("id") Integer contactId, Model model, Authentication auth) {
        contactService.closeMessage(contactId, auth.getName());
        return "redirect:/contact/displayMessages";
    }
}
