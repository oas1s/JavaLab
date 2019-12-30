package listners;


import services.AddProductService;
import services.DeleteProductService;
import services.GetProductsService;
import services.LoginService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class PrimitiveContextServletListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("IN LISTENER");
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("add", new AddProductService());
        servletContext.setAttribute("delete", new DeleteProductService());
        servletContext.setAttribute("getproducts", new GetProductsService());
        servletContext.setAttribute("login", new LoginService());
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
