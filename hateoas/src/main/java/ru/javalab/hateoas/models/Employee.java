package ru.javalab.hateoas.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String surname;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}
