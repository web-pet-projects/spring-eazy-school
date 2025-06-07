package com.quentin.eazyschool.service;

import com.quentin.eazyschool.model.Course;
import com.quentin.eazyschool.model.Enrollment;
import com.quentin.eazyschool.model.Person;
import com.quentin.eazyschool.payload.CourseDTO;
import com.quentin.eazyschool.payload.EnrollmentDTO;
import com.quentin.eazyschool.repository.CourseRepository;
import com.quentin.eazyschool.repository.EnrollmentRepository;
import com.quentin.eazyschool.repository.PersonRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CourseService {
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final PersonRepository personRepository;
    private final EnrollmentRepository enrollmentRepository;

    public List<CourseDTO> fetchAll() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDTO> courseDTOs = courses.stream().map(c -> modelMapper.map(c, CourseDTO.class)).toList();
        return courseDTOs;
    }

    public List<EnrollmentDTO> fetchAllByStudentId(Long studentId) {
        Person student = personRepository.findById(studentId).orElseThrow(
                () -> new RuntimeException("Student not found")
        );
        List<Enrollment> enrollments = student.getCourses();
        List<EnrollmentDTO> enrollmentDTOs = enrollments.stream().map(e -> modelMapper.map(e, EnrollmentDTO.class)).toList();
        return enrollmentDTOs;
    }

    public void createCourse(@Valid CourseDTO course) {
        Course newCourse = modelMapper.map(course, Course.class);
        courseRepository.save(newCourse);
    }

    public List<EnrollmentDTO> fetchAllByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new RuntimeException("Course not found")
        );
        List<Enrollment> enrollments = course.getStudents();
        List<EnrollmentDTO> enrollmentDTOs = enrollments.stream().map(e -> modelMapper.map(e, EnrollmentDTO.class)).toList();
        return enrollmentDTOs;
    }

    public void addStudentToCourse(Long courseId, @Email String email) {
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new RuntimeException("Course not found")
        );
        Person student = personRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Student not found")
        );
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus(Enrollment.Status.ACTIVE);
        enrollmentRepository.save(enrollment);
    }

    public CourseDTO fetchCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new RuntimeException("Course not found")
        );
        return modelMapper.map(course, CourseDTO.class);
    }

    public void deleteStudentFromCourse(Long courseId, Long studentId) {
//        Course course = courseRepository.findById(courseId).orElseThrow(
//                () -> new RuntimeException("Course not found")
//        );
//        Person student = personRepository.findById(studentId).orElseThrow(
//                () -> new RuntimeException("Student not found")
//        );
        Enrollment enrollment = enrollmentRepository.findByCourseIdAndStudentId(courseId, studentId).orElseThrow(
                () -> new RuntimeException("Enrollment not found")
        );
        enrollmentRepository.delete(enrollment);
    }
}
