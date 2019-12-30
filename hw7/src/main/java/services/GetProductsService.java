package services;

import com.fasterxml.jackson.core.type.TypeReference;
import context.Component;
import dto.DtoPagination;
import dto.DtoProduct;
import dto.DtoProductList;
import models.Pagination;
import models.Product;
import protocol.Request;
import protocol.Response;
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
    public Response getProducts(Request request){
        ProductRepositoryImpl productRepository = new ProductRepositoryImpl();
        Request<DtoPagination> paginationRequest = request;
        DtoPagination dtoPagination = paginationRequest.getPayload();
        Pagination pagination = new Pagination();
        pagination.setStart(dtoPagination.getStart());
        pagination.setEnd(dtoPagination.getEnd());
        List<Product> list = productRepository.findByStartAndEnd(pagination.getStart(),pagination.getEnd());
        List<DtoProduct> dtoProducts = new ArrayList<>();
        for (int i = 0; i <list.size(); i++) {
            DtoProduct dtoProduct = new DtoProduct();
            Product product = new Product();
            product = list.get(i);
            dtoProduct.setId(product.getId());
            dtoProduct.setName(product.getName());
            dtoProduct.setCoast(product.getCoast());
            dtoProducts.add(dtoProduct);
        }
        Response response = new Response();
        response.setResponse(new DtoProductList(dtoProducts));
        return response;
    }
}
