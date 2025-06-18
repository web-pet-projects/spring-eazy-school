package com.quentin.eazyschool.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private Long id;

    @Size(min = 2, max = 200)
    @NotEmpty(message = "Primary Address must not be empty")
    private String primary;

    @Size(max = 200)
    private String secondary;

    @Size(max = 50)
    @NotEmpty(message = "City must not be empty")
    private String city;

    @Size(max = 50)
    private String state;

    @NotEmpty
    @Pattern(regexp = "(^$|[0-9]{5,8})", message = "Zip Code must be 5-8 digits")
    private String zipCode;
}
