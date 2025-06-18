package com.quentin.eazyschool.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "enrollments")
@Data
@EqualsAndHashCode(callSuper = true)
public class Enrollment extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long id;

    @ManyToOne(cascade = { CascadeType.PERSIST})
    @JoinColumn(name = "student_id")
    private Person student;

    @ManyToOne(cascade = { CascadeType.PERSIST})
    @JoinColumn(name = "course_id")
    private Course course;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Double grade;

    public enum Status { ACTIVE, COMPLETED, DROPPED }

}
