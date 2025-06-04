package com.quentin.eazyschool.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "addresses")
@Data
public class Address extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @Size(min = 2, max = 200)
    private String address1;
    @Size(min = 2, max = 200)
    private String address2;
    @Size(min = 2, max = 50)
    private String city;
    @Size(min = 2, max = 50)
    private String state;

    @NotBlank
    @Pattern(regexp = "(^$|[0-9]{5})", message = "Zip Code must contain 5 digits")
    private Integer zipCode;
    private String country;

    @OneToOne(mappedBy = "address")
    private Person person;
}
