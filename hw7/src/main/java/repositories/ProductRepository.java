package repositories;

import models.Product;

import java.util.List;

public interface ProductRepository  extends CrudRepository<Product,Integer> {
    void save(Product product);
    Product findById(int id);
    List<Product> findByStartAndEnd(int start,int end);
    void delete(int id);
}
