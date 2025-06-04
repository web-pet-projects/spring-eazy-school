package com.quentin.eazyschool.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity {
    @Id
    @Column(name = "role_id")
    private Integer id;

    @Column(name = "role_name")
    @Size(max = 50)
    @NotEmpty
    private String name;

}
