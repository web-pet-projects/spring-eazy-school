package com.quentin.eazyschool.payload;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ContactDTO {

    private Long contactId;

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide valid email")
    private String email;

    @NotBlank(message = "Phone number must not be blank")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number must be 10 digits")
    private String mobileNum;

    @NotBlank(message = "Subject must not be blank")
    @Size(min = 5, message = "Subject must be at least 5 characters long")
    private String subject;

    @NotBlank(message = "Message must not be blank")
    @Size(min = 10, message = "Message must be at least 10 characters long")
    private String message;
}
