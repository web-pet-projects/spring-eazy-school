package com.quentin.eazyschool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Holiday {
    private String day;
    private String reason;
    private Type type;

    public enum Type {
        FESTIVAL, FEDERAL
    }
}
