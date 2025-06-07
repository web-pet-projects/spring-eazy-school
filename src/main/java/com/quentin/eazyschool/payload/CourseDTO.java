package com.quentin.eazyschool.payload;

import jakarta.validation.constraints.Digits;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class CourseDTO {
    private Long id;
    private String title;
    private String code;
    private Integer credit;
    private String description;

    @Digits(integer = 10, fraction = 2)
    private BigDecimal fee;
}
