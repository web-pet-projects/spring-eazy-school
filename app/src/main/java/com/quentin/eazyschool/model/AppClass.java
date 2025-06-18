package com.quentin.eazyschool.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "classes")
@Data
public class AppClass extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long id;

    @Column(name = "class_name")
    private String name;

    @OneToMany(mappedBy = "appClass", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private List<Person> students = new ArrayList<>();
}
