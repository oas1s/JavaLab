package servlets;

import dto.DtoUser;
import models.User;
import services.LoginService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/logIn")
public class LogInServlet extends HttpServlet {
    private LoginService logInService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        logInService = (LoginService) servletContext.getAttribute("login");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = new User();
        user.setEmail(username);
        user.setPassword(password);
        String token = logInService.login(user);
        req.getSession().setAttribute("token",token);
        resp.sendRedirect("/");
    }
}
