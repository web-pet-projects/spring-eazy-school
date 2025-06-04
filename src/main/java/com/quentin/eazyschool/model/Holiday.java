package com.quentin.eazyschool.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "holidays")
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Holiday extends BaseEntity {
    @Id
    @Column(name = "holiday_id")
    private Integer id;

    @Column(name = "holiday_date")
    private String day;
    private String reason;

    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        FESTIVAL, FEDERAL
    }
}
