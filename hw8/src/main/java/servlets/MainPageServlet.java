package servlets;

import dto.DtoProduct;
import models.Product;
import models.User;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/")
public class MainPageServlet extends HttpServlet {
    private GetProductsService getProductsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        getProductsService = (GetProductsService) servletContext.getAttribute("getproducts");
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long page = 0L;
        String username = "";
        boolean isAdmin = false;
        if (req.getParameter("page") != null) {
            page = Long.parseLong(req.getParameter("page"));
        }
        if (req.getSession().getAttribute("token") != null) {
            String token = (String) req.getSession().getAttribute("token");
            User user = new TokenizeUser().decodeJwt(token);
            username = user.getEmail();
            isAdmin = user.getRole().equals("admin");
        }
        List<Product> list = getProductsService.getProducts();
        req.setAttribute("isAdmin", isAdmin);
        req.setAttribute("prevPage", page == 0 ? 0 : page - 1);
        req.setAttribute("username", username);
        req.setAttribute("products", list);
        req.getRequestDispatcher("/index.ftl").forward(req, resp);
    }
}
