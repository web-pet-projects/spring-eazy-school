package com.quentin.eazyschool.payload;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EnrollmentDTO {
    private Long id;

    @Valid
    private PersonDTO student;

    @Valid
    private CourseDTO course;
    private String status;
    private Double grade;

    @PastOrPresent
    private LocalDateTime enrolledAt;
}
