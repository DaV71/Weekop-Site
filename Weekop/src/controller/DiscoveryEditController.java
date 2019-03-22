package controller;

import model.Discovery;
import model.User;
import service.DiscoveryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/edit")
public class DiscoveryEditController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        long discoveryToEditId = Long.valueOf(request.getParameter("discovery_to_edit_id"));
        String name = request.getParameter("inputName");
        String desc = request.getParameter("inputDescription");
        String url = request.getParameter("inputUrl");
        DiscoveryService discoveryService = new DiscoveryService();
        Discovery discoveryToEdit = discoveryService.getDiscoveryById(discoveryToEditId);
        User authenticatedUser = (User) request.getSession().getAttribute("user");
        if (authenticatedUser.getUsername().equals(discoveryToEdit.getUser().getUsername())) {
            discoveryToEdit.setName(name);
            discoveryToEdit.setDescription(desc);
            discoveryToEdit.setUrl(url);
            discoveryService.updateDiscovery(discoveryToEdit);
            response.sendRedirect(request.getContextPath()+"/account");
        } else {
            response.sendError(403);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long discoveryId = Long.parseLong(request.getParameter("discovery_id"));
        DiscoveryService discoveryService = new DiscoveryService();
        Discovery discoveryToEdit = discoveryService.getDiscoveryById(discoveryId);
        request.setAttribute("discoveryToEdit",discoveryToEdit);
        request.getRequestDispatcher("/WEB-INF/new.jsp").forward(request,response);
    }
}
