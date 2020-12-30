package ru.javalab.hateoas.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class City {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "city")
    private List<Employee> employees;

    @OneToMany(mappedBy = "city")
    private List<PickUpStore> pickUpStores;

    @OneToMany(mappedBy = "city")
    private List<Warehouse> warehouses;
}
