package controller;

import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ban")
public class UserBanController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User admin = (User)request.getSession().getAttribute("admin");
        if (admin != null){
        long id = Long.parseLong(request.getParameter("id"));
        int active = Integer.parseInt(request.getParameter("active"));
        boolean setActive = false;
        if (active == 1)
            setActive = true;
        UserService userService = new UserService();
        userService.setIsActive(id,setActive);
        response.sendRedirect(request.getContextPath()+"/admin");
        } else {
            response.sendError(403);
        }

    }
}
