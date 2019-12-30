package services;

import com.fasterxml.jackson.core.type.TypeReference;
import context.Component;
import dto.Dto;
import dto.DtoProduct;
import dto.DtoString;
import models.Product;
import models.User;
import repositories.ProductRepositoryImpl;
import repositories.UserRepositoryImpl;
import utills.JsonCreator;
import utills.TokenizeUser;

public class AddProductService implements Component {
    public AddProductService() {
    }

    @Override
    public String getName() {
        return "AddProductService";
    }
    public boolean addProduct(Product product, String token){
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        ProductRepositoryImpl productRepository = new ProductRepositoryImpl();
        TokenizeUser tokenizeUser = new TokenizeUser();
        User user = tokenizeUser.decodeJwt(token);
        User user2 = userRepository.findByLogin(user.getEmail()).get();
        if (user2.getRole().equals("admin")) {
            productRepository.save(product);
            return true;
        } else {
            return false;
        }
    }
}
