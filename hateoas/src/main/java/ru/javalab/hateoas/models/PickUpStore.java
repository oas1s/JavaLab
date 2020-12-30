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
public class PickUpStore {
    @Id
    @GeneratedValue
    private Long id;

    private String street;
    private String house;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}
