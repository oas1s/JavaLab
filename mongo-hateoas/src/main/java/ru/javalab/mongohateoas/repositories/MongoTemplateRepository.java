package ru.javalab.mongohateoas.repositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.javalab.mongohateoas.model.Employee;

public class MongoTemplateRepository {
    private MongoTemplate mongoTemplate;

    public MongoTemplateRepository() {
         MongoClient mongoClient = MongoClients.create();
         mongoTemplate = new MongoTemplate(mongoClient,"javalab");
    }

    private void save(Employee employee){
        mongoTemplate.insert(employee, "employee");
    }

    private void update(Employee employee){
        employee = mongoTemplate.findOne(
                Query.query(Criteria.where("_id").is(employee.get_id())), Employee.class);
        mongoTemplate.save(employee, "employee");
    }

    private Employee find(Employee employee){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(employee.get_id()));
        return mongoTemplate.findOne(query, Employee.class, "employee");
    }

    private void delete(Employee employee){
        mongoTemplate.remove(employee, "employee");
    }
}
