package servlets;

import models.Product;
import models.User;
import org.springframework.security.core.parameters.P;
import services.DeleteProductService;
import services.GetProductsService;
import utills.TokenizeUser;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@WebServlet("/delProduct")
public class DeleteServlet extends HttpServlet {
    DeleteProductService delProductService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        delProductService = (DeleteProductService) servletContext.getAttribute("delete");
        super.init(config);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = 0;
        String username = "";
        boolean isAdmin = false;
        if (req.getParameter("id") != null) {
             id = Integer.parseInt(req.getParameter("id"));
            if (req.getSession().getAttribute("token") != null) {
                String token = (String) req.getSession().getAttribute("token");
                User user = new TokenizeUser().decodeJwt(token);
                username = user.getEmail();
                isAdmin = user.getRole().equals("admin");
                Product product = new Product();
                product.setId(id);
                delProductService.deleteProduct(product,token);
            }
        }
        resp.sendRedirect("/");
    }
}
