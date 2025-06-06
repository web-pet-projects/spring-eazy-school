package com.quentin.eazyschool.payload;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    private Long id;

    @Size(min = 2, max = 50)
    private String username;

    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    private String gender;

    @Email
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number must be 10 digits")
    private String mobileNum;

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private Integer age;

    @Valid
    private AddressDTO address;


}
