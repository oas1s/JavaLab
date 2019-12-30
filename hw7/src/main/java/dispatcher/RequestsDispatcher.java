package dispatcher;

import protocol.Request;
import protocol.Response;
import services.*;

public class RequestsDispatcher {

    private LoginService loginService;
    private DeleteProductService deleteProductService;
    private GetProductsService getProductsService;
    private MessageService messageService;
    private PaginationService paginationService;
    private AddProductService addProductService;

    public RequestsDispatcher(LoginService loginService, DeleteProductService deleteProductService, GetProductsService getProductsService, MessageService messageService, PaginationService paginationService, AddProductService addProductService) {
        this.loginService = loginService;
        this.deleteProductService = deleteProductService;
        this.getProductsService = getProductsService;
        this.messageService = messageService;
        this.paginationService = paginationService;
        this.addProductService = addProductService;
    }

    public Response doDispatch(Request request) {
        if (request.getHeader().equals("Login")) {
           return loginService.login(request);
        }
        if (request.getHeader().equals("Message")) {
            return messageService.message(request);
        }
        if (request.getHeader().equals("Pagination")) {
            return paginationService.paginate(request);
        }
        if (request.getHeader().equals("GetProducts")) {
            return getProductsService.getProducts(request);
        }
        if (request.getHeader().equals("DeleteProduct")){
            return deleteProductService.deleteProduct(request);
        }
        if (request.getHeader().equals("AddProduct")) {
            return addProductService.addProduct(request);
        }
        return null;
    }
}
