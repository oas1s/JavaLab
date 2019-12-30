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

public class DeleteProductService implements Component {
    public DeleteProductService() {
    }

    @Override
    public String getName() {
        return "DeleteProductService";
    }
    public boolean deleteProduct(Product product, String token) {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        ProductRepositoryImpl productRepository = new ProductRepositoryImpl();
        TokenizeUser tokenizeUser = new TokenizeUser();
        User user = tokenizeUser.decodeJwt(token);
        User user2 = userRepository.findByLogin(user.getEmail()).get();
        if (user2.getRole().equals("admin")) {
            productRepository.delete(product.getId());
            return true;
        } else {
            return false;
        }
        }
    }
