package com.quentin.eazyschool.payload;

import com.quentin.eazyschool.validator.FieldMatch;
import com.quentin.eazyschool.validator.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@FieldMatch.List(
        value = {
                @FieldMatch(
                        first = "password",
                        second = "confirmPassword",
                        message = "Password does not match !!"
                ),
                @FieldMatch(
                        first = "email",
                        second = "confirmEmail",
                        message = "Email does not match !!"
                )
        }
)
public class RegisterRequest {
    @Size(min = 2, max = 50)
    private String username;

//    @Size(min = 2, max = 100)
//    private String firstName;
//
//    @Size(min = 2, max = 100)
//    private String lastName;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number must be 10 digits")
    private String mobileNum;

    @Password
    @NotEmpty(message = "Password cannot be empty !!")
    private String password;
    private String confirmPassword;

    @Email
    @NotEmpty(message = "Email cannot be empty !!")
    private String email;
    private String confirmEmail;
}
