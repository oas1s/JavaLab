package services;

import com.fasterxml.jackson.core.type.TypeReference;
import context.Component;
import dto.DtoPagination;
import dto.DtoProduct;
import dto.DtoProductList;
import models.Pagination;
import models.Product;
import repositories.ProductRepositoryImpl;
import utills.JsonCreator;

import java.util.ArrayList;
import java.util.List;

public class GetProductsService implements Component {
    public GetProductsService() {
    }

    @Override
    public String getName() {
        return "GetProductsService";
    }
    public List<Product> getProducts(){
        ProductRepositoryImpl productRepository = new ProductRepositoryImpl();
        List<Product> list = productRepository.find();
        return list;
    }
}
