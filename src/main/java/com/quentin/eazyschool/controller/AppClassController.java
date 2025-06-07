package com.quentin.eazyschool.controller;

import com.quentin.eazyschool.payload.AppClassDTO;
import com.quentin.eazyschool.payload.PersonDTO;
import com.quentin.eazyschool.repository.AppClassRepository;
import com.quentin.eazyschool.service.AppClassService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/classes")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AppClassController {
    private final AppClassService appClassService;

    @GetMapping
    public String displayClasses(Model model) {
        List<AppClassDTO> classes = appClassService.fetchAll();
        model.addAttribute("classes", classes);
        model.addAttribute("appClass", new AppClassDTO());
        return "classes";
    }

    @PostMapping("/create")
    public String addClass(@ModelAttribute AppClassDTO appClass, Errors errors) {
        if (errors.hasErrors()) {
            return "classes";
        }
        appClassService.createClass(appClass);
        return "redirect:/classes";
    }

    @GetMapping("/{classId}/remove")
    public String removeClass(@PathVariable Long classId) {
        appClassService.removeClass(classId);
        return "redirect:/classes";
    }

    @GetMapping("/{classId}/students/display")
    public String viewStudents(@RequestParam(value = "error", required = false) String error,
                               @PathVariable Long classId,
                               Model model) {
        String errorMessage = null;
        if (error != null) {
            errorMessage = error.isEmpty() ? "Something went wrong" : error;
        }
        AppClassDTO classEntity = appClassService.fetchById(classId);
        model.addAttribute("appClass", classEntity);
        model.addAttribute("student", new PersonDTO());
        model.addAttribute("errorMessage", errorMessage);
        return "students";
    }

    @PostMapping("/{classId}/students/add")
    public String addStudent(@PathVariable Long classId, @ModelAttribute PersonDTO student, Errors errors) {
        if (errors.hasErrors()) {
            return "students";
        }
        String email = student.getEmail();
        try {
            appClassService.addStudentByEmail(classId, email);
        } catch (RuntimeException e) {
            return "redirect:/classes/" + classId + "/students/display?error=" + e.getMessage();
        }
        return "redirect:/classes/" + classId + "/students/display";
    }

    @GetMapping("/{classId}/students/{studentId}/remove")
    public String deleteStudent(@PathVariable Long classId, @PathVariable Long studentId) {
        try {
            appClassService.removeStudentById(classId, studentId);
        } catch (RuntimeException e) {
            return "redirect:/classes/" + classId + "/students/display?error=" + e.getMessage();
        }
        return "redirect:/classes/" + classId + "/students/display";
    }

}
