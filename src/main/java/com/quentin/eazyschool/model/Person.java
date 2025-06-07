package com.quentin.eazyschool.model;

import com.quentin.eazyschool.validator.Password;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;

    @Size(min = 2, max = 50)
    @Column(unique = true)
    private String username;

    @Size(min = 2, max = 100)
    private String firstName;

    @Size(min = 2, max = 100)
    private String lastName;

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number must be 10 digits")
    private String mobileNum;

    @Email
    @Column(unique = true)
    private String email;

    @Password
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private AppClass appClass;

    @OneToMany(mappedBy = "student")
    private List<Enrollment> courses = new ArrayList<>();

    public enum Gender { MALE, FEMALE, UNKNOWN }

    @Transient
    public Integer getAge() {
        return (dateOfBirth != null) ? LocalDate.now().getYear() - dateOfBirth.getYear() : null;
    }


}
