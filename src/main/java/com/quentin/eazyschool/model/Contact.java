package com.quentin.eazyschool.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Contact extends BaseEntity {
    private Integer contactId;
    private String name;
    private String mobileNum;
    private String email;
    private String message;
    private String subject;
    private String status;

    public enum Status {
        OPEN, CLOSE
    }
}
