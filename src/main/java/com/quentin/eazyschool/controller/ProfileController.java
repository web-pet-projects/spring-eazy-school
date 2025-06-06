package com.quentin.eazyschool.controller;

import com.quentin.eazyschool.payload.AddressDTO;
import com.quentin.eazyschool.payload.PersonDTO;
import com.quentin.eazyschool.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProfileController {

    private final PersonService personService;


    @GetMapping("/profile")
    public String profile(@RequestParam(name = "update", required = false) String update,
                          Model model,
                          Authentication auth) {
        String successMessage = null;
        if (update != null) {
            successMessage = "Updated successfully";
        }
        String username = auth.getName();
        PersonDTO profile = personService.fetchUserByUsername(username);
        if (profile.getAddress() == null) {
            profile.setAddress(new AddressDTO());
        }
        model.addAttribute("profile", profile);
        model.addAttribute("successMessage", successMessage);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid @ModelAttribute("profile") PersonDTO profile, Errors errors) {
        if (errors.hasErrors()) {
            log.error("Errors occurred: {}", errors.getAllErrors().toString());
            return "profile";
        }
        PersonDTO updatedProfile = personService.updateDetails(profile);
        if (updatedProfile == null) {
            return "profile";
        }
        return "redirect:/profile?update=true";
    }
}
