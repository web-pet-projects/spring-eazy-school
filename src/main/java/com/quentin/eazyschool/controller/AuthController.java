package com.quentin.eazyschool.controller;

import com.quentin.eazyschool.model.Person;
import com.quentin.eazyschool.payload.RegisterRequest;
import com.quentin.eazyschool.repository.PersonRepository;
import com.quentin.eazyschool.service.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthController {
    private final PersonService personService;

    @RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        @RequestParam(value = "register", required = false) String register,
                        Model model) {
        String errorMessage = null;
        if (error != null) {
            errorMessage = "Username or password is incorrect !!";
        }
        if (logout != null) {
            errorMessage = "You have been logged out successfully !!";
        }
        if (register != null) {
            errorMessage = "Registered successfully !! Please enter registered credentials";
        }
        model.addAttribute("errorMessage", errorMessage);
        log.info("Successfully logged in");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }

    @GetMapping("/public/register")
    public String register(Model model) {
        RegisterRequest person = new RegisterRequest();
        model.addAttribute("person", person);
        return "register";
    }

    @PostMapping("/public/register")
    public String register(@Valid @ModelAttribute(name = "person") RegisterRequest registerRequest,
                           Errors errors) {
        if (errors.hasErrors()) {
            log.error("Error registering user: {}", errors.getAllErrors().toString());
            return "register";
        }
        personService.createUser(registerRequest);
        return "redirect:/login?register=true";
    }
}
