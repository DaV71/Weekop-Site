package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import org.springframework.dao.DuplicateKeyException;
import service.DiscoveryService;

/**
 * Servlet implementation class DiscoveryAddController
 */
@WebServlet("/add")
public class DiscoveryAddController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User loggedUser = (User) request.getSession().getAttribute("user");
        if (loggedUser!=null && loggedUser.isActive()) {
            request.getRequestDispatcher("/WEB-INF/new.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("inputName");
        String desc = request.getParameter("inputDescription");
        String url = request.getParameter("inputUrl");
        User authenticatedUser = (User) request.getSession().getAttribute("user");
        if (request.getUserPrincipal()!=null) {
            DiscoveryService discoveryService = new DiscoveryService();
            try {
                discoveryService.addDiscovery(name, desc, url, authenticatedUser);
                response.sendRedirect(request.getContextPath() + "/");
            } catch (DuplicateKeyException e){
                e.printStackTrace();
                request.getSession().setAttribute("addDiscoveryFailed",e);
                response.sendRedirect("add");
            }
        } else {
            response.sendError(403);
        }
    }

}
