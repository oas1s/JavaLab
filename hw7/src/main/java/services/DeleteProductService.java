package services;

import com.fasterxml.jackson.core.type.TypeReference;
import context.Component;
import dto.Dto;
import dto.DtoProduct;
import dto.DtoString;
import models.Product;
import models.User;
import protocol.Request;
import protocol.Response;
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
    public Response deleteProduct(Request request) {
        ProductRepositoryImpl productRepository = new ProductRepositoryImpl();
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        Request<DtoProduct> productRequest = request;
        DtoProduct dtoProduct = productRequest.getPayload();
        Product product = new Product();
        product.setId(dtoProduct.getId());
        product.setName(dtoProduct.getName());
        product.setCoast(dtoProduct.getCoast());
        String token = productRequest.getToken();
        TokenizeUser tokenizeUser = new TokenizeUser();
        User user = tokenizeUser.decodeJwt(token);
        User user2 = userRepository.findByLogin(user.getEmail()).get();
        if (user2.getRole().equals("admin")) {
            productRepository.delete(product.getId());
            Response response = new Response();
            response.setResponse(new DtoString("Succesfull"));
            return response;
        } else {
            Response response = new Response();
            response.setResponse(new DtoString("Fail"));
            return response;
        }

        }
    }
