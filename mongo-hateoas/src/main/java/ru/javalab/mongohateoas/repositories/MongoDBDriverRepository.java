package ru.javalab.mongohateoas.repositories;


import com.mongodb.client.*;
import org.bson.Document;
import ru.javalab.mongohateoas.model.Employee;

public class MongoDBDriverRepository {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoDBDriverRepository() {
        mongoClient = MongoClients.create();
        database = mongoClient.getDatabase("javalab");
        collection = database.getCollection("employee");
    }

    public void create(Employee employee){
        Document doc = new Document("name", employee.getName())
                .append("surname", employee.getSurname())
                .append("education", employee.getEducation())
                .append("job", new Document("name", employee.getJob().getName()).append("slogan", employee.getJob().getSlogan()));

        collection.insertOne(doc);
    }

    public void delete(Employee employee){
        Document doc = new Document("name", employee.getName())
                .append("surname", employee.getSurname())
                .append("education", employee.getEducation())
                .append("job", new Document("name", employee.getJob().getName()).append("slogan", employee.getJob().getSlogan()));

        collection.deleteOne(doc);
    }

    public FindIterable<Document> find(Employee employee){
        Document doc = new Document("name", employee.getName())
                .append("surname", employee.getSurname())
                .append("education", employee.getEducation())
                .append("job", new Document("name", employee.getJob().getName()).append("slogan", employee.getJob().getSlogan()));

       return collection.find(doc);
    }

}
