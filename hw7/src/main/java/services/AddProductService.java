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

public class AddProductService implements Component {
    public AddProductService() {
    }

    @Override
    public String getName() {
        return "AddProductService";
    }
    public Response addProduct(Request request){
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        ProductRepositoryImpl productRepository = new ProductRepositoryImpl();
        Request<DtoProduct> productRequest = request;
        Product product = new Product();
        DtoProduct dtoProduct = productRequest.getPayload();
        product.setCoast(dtoProduct.getCoast());
        product.setName(dtoProduct.getName());
        product.setId(dtoProduct.getId());
        String token = productRequest.getToken();
        TokenizeUser tokenizeUser = new TokenizeUser();
        User user = tokenizeUser.decodeJwt(token);
        User user2 = userRepository.findByLogin(user.getEmail()).get();
        if (user2.getRole().equals("admin")) {
            productRepository.save(product);
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
