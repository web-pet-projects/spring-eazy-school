package com.quentin.eazyschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        String errorMessage = null;
        if (error != null) {
            errorMessage = "Username or password is incorrect !!";
        }
        if (logout != null) {
            errorMessage = "You have been logged out successfully !!";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }
}
