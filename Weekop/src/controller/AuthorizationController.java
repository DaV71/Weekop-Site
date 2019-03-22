package controller;

import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth")
public class AuthorizationController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        UserService userService = new UserService();
        userService.setIsActive(id,true);
        User user = (User)request.getSession().getAttribute("user");
        if (user != null){
            User authUser = userService.getUserById(id);
            request.getSession().setAttribute("user",authUser);
        }
        response.sendRedirect(request.getContextPath()+"/");
    }
}
