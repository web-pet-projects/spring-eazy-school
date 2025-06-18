package com.quentin.eazyschool.controller;

import com.quentin.eazyschool.model.Person;
import com.quentin.eazyschool.payload.CourseDTO;
import com.quentin.eazyschool.payload.EnrollmentDTO;
import com.quentin.eazyschool.payload.PersonDTO;
import com.quentin.eazyschool.security.CustomUserDetails;
import com.quentin.eazyschool.service.CourseService;
import com.quentin.eazyschool.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CourseController {

    private final CourseService courseService;
    private final PersonService personService;

    @GetMapping
    public String getCourses(Model model, Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return "courses/guest-view";
        }
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            List<CourseDTO> courses = courseService.fetchAll();
            model.addAttribute("course", new CourseDTO());
            model.addAttribute("courses", courses);
            return "courses/admin-view";
        }

        if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"))) {
            PersonDTO student = personService.fetchUserByUsername(userDetails.getUsername());
            model.addAttribute("person", student);
            List<EnrollmentDTO> enrollments = courseService.fetchAllByStudentId(student.getId());
            model.addAttribute("enrollments", enrollments);
            return "courses/student-view";
        }

        return "redirect:/login";
    }

    @PostMapping("add")
    public String addCourse(@Valid @ModelAttribute CourseDTO course,
                            Errors errors) {
        if (errors.hasErrors()) {
            return "courses/admin-view";
        }
        courseService.createCourse(course);
        return "redirect:/courses";
    }

    @GetMapping("/{courseId}/students")
    public String displayStudents(@RequestParam(value = "error", required = false) String error,
                                  @PathVariable Long courseId,
                                  Model model) {
        String errorMessage = null;
        if (error != null) {
            errorMessage = error.isEmpty() ? "Something went wrong" : error;
        }
        List<EnrollmentDTO> enrollments = courseService.fetchAllByCourseId(courseId);
        CourseDTO course = courseService.fetchCourseById(courseId);
        model.addAttribute("course", course);
        model.addAttribute("person", new PersonDTO());
        model.addAttribute("enrollments", enrollments);
        model.addAttribute("errorMessage", errorMessage);
        return "courses/students-list";
    }

    @PostMapping("{courseId}/students/add")
    public String addStudentToCourse(@PathVariable Long courseId,
                                     @Valid @ModelAttribute PersonDTO student,
                                     Errors errors) {
        if (errors.hasErrors()) {
            return "courses/students-list";
        }
        try {
            courseService.addStudentToCourse(courseId, student.getEmail());
        } catch (RuntimeException e) {
            return "redirect:/courses/" + courseId + "/students?error=" + e.getMessage();
        }
        return "redirect:/courses/" + courseId + "/students";
    }

    @GetMapping("/{courseId}/students/{studentId}/delete")
    public String deleteStudentFromCourse(@PathVariable Long courseId,
                                          @PathVariable Long studentId) {
        try {
            courseService.deleteStudentFromCourse(courseId, studentId);
        } catch (RuntimeException e) {
            return "redirect:/courses/" + courseId + "/students?error=" + e.getMessage();
        }
        return "redirect:/courses/" + courseId + "/students";
    }
}
