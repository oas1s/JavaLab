package servers;

import context.ApplicationContextReflectionBased;
import context.Component;
import dispatcher.RequestsDispatcher;
import protocol.RequestsHandler;
import services.*;

import java.util.ArrayList;
import java.util.List;

public class ServerMain {
    public static void main(String[] args) {
        String port = null;
        String properties = null;
        for (int i = 0; i <args.length; i++) {
            if (args[i].startsWith("--port")) {
                port = args[i].split("=")[1];
            }
            if (args[i].startsWith("--db-properties-path")) {
                properties = args[i].split("=")[1];
            }
        }
        System.out.println(port + " " +  properties);
        ApplicationContextReflectionBased context = new ApplicationContextReflectionBased();
        AddProductService addProductService = (AddProductService) context.getComponent(Component.class,"AddProductService");
        DeleteProductService deleteProductService = (DeleteProductService) context.getComponent(Component.class,"DeleteProductService");
        GetProductsService getProductsService = (GetProductsService) context.getComponent(Component.class,"GetProductsService");
        LoginService loginService = (LoginService) context.getComponent(Component.class,"LoginService");
        List<ClientHandler> list = new ArrayList<>();
        MessageService messageService = (MessageService) context.getComponent(Component.class,"MessageService");
        messageService.setClients(list);
        PaginationService paginationService = (PaginationService) context.getComponent(Component.class,"PaginationService");
        RequestsDispatcher dispatcher = new RequestsDispatcher(loginService,deleteProductService,getProductsService, messageService,paginationService,addProductService);
        RequestsHandler requestsHandler = new RequestsHandler(dispatcher);
        MyServer server = new MyServer(requestsHandler,list);
        server.start(7000);
    }
}
