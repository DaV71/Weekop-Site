package controller;

import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User admin = (User)request.getSession().getAttribute("admin");
        if (admin != null) {
            saveUsersInRequest(request);
            request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
        } else {
            response.sendError(403);
        }
    }

    private void saveUsersInRequest(HttpServletRequest request){
        UserService userService = new UserService();
        List <User> users = userService.getUsers();
        request.setAttribute("users",users);
    }
}
