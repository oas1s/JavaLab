package servlets;

import models.Product;
import models.User;
import services.AddProductService;
import services.DeleteProductService;
import utills.TokenizeUser;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/newproduct")
public class AddServlet extends HttpServlet {
    AddProductService addProductService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        addProductService = (AddProductService) servletContext.getAttribute("add");
        super.init(config);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = "";
        boolean isAdmin = false;
        if ((req.getParameter("name") != null) & req.getParameter("cost") != null) {
            if (req.getSession().getAttribute("token") != null) {
                String token = (String) req.getSession().getAttribute("token");
                User user = new TokenizeUser().decodeJwt(token);
                username = user.getEmail();
                isAdmin = user.getRole().equals("admin");
                Product product = new Product();
                String name = req.getParameter("name");
                int cost = Integer.parseInt(req.getParameter("cost"));
                product.setName(name);
                product.setCost(cost);
                addProductService.addProduct(product,token);
            }
        }
        resp.sendRedirect("/");
    }
}
