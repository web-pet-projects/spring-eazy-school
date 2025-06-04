package com.quentin.eazyschool.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "contact_msg")
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Integer id;
    private String name;
    private String mobileNum;
    private String email;
    private String message;
    private String subject;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        OPEN, CLOSE
    }
}
