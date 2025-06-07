package com.quentin.eazyschool.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Data
@EqualsAndHashCode(callSuper = true)
public class Course extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(name = "course_title")
    private String title;

    private String description;
    private String code;
    private Integer credit;

    @Column(precision = 12, scale = 2)
    private BigDecimal fee;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> students = new ArrayList<>();
}
