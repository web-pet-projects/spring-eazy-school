package com.quentin.eazyschool.payload;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AppClassDTO {
    private Long id;
    private String name;
    private List<PersonDTO> students = new ArrayList<>();
}
