package org.example.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {

    @Id
    private Integer id;

    @NotBlank
    @Size(min = 1, max = 50)
    @Column(name = "name", length = 50)
    private String name;

    @NotBlank
    @Size(min = 1, max = 50)
    @Column(name = "pass", length = 50)
    private String password;

}
