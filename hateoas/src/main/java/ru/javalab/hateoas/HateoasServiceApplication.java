package ru.javalab.hateoas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.javalab.hateoas.models.*;
import ru.javalab.hateoas.repositories.*;

import static java.util.Arrays.asList;

@SpringBootApplication
public class HateoasServiceApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(HateoasServiceApplication.class, args);

        CityRepository cityRepository = context.getBean(CityRepository.class);
        CommentRepository commentRepository = context.getBean(CommentRepository.class);
        EmployeeRepository employeeRepository = context.getBean(EmployeeRepository.class);
        WarehouseRepository warehouseRepository = context.getBean(WarehouseRepository.class);
        ShoperRepository shoperRepository = context.getBean(ShoperRepository.class);
        PickUpStoreRepository pickUpStoreRepository = context.getBean(PickUpStoreRepository.class);

        Shoper shoper1 = Shoper.builder().name("SomeName").surname("Surnamee").status("Active").build();
        Shoper shoper2 = Shoper.builder().name("SomeName2").surname("Surnamee2").status("Deleted").build();
        shoperRepository.saveAll(asList(shoper1,shoper2));

        Employee employee1 = Employee.builder().name("EmpName1").surname("EmpSurname1").build();
        Employee employee2 = Employee.builder().name("EmpName2").surname("EmpSurname2").build();
        employeeRepository.saveAll(asList(employee1,employee2));

        Warehouse warehouse1 = Warehouse.builder().bulk(300L).street("SomeStreet1").house("1").build();
        Warehouse warehouse2 = Warehouse.builder().bulk(600L).street("SomeStreet2").house("2").build();
        warehouseRepository.saveAll(asList(warehouse1,warehouse2));

        PickUpStore pickUpStore1 = PickUpStore.builder().street("SomeStreet3").house("3").build();
        PickUpStore pickUpStore2 = PickUpStore.builder().street("SomeStreet4").house("4").build();
        pickUpStoreRepository.saveAll(asList(pickUpStore1,pickUpStore2));

        City city1 = City.builder().employees(asList(employee1)).name("Kazan").warehouses(asList(warehouse1)).pickUpStores(asList(pickUpStore1)).build();
        City city2 = City.builder().employees(asList(employee2)).name("Moscow").warehouses(asList(warehouse2)).pickUpStores(asList(pickUpStore2)).build();
    }

}
