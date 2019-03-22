package controller;

import model.Comment;
import model.Discovery;
import model.User;
import service.CommentService;
import service.DiscoveryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/comment")
public class CommentController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String content = request.getParameter("content");
        long id = Long.parseLong(request.getParameter("discovery_id"));
        User user = (User)request.getSession().getAttribute("user");
        DiscoveryService discoveryService = new DiscoveryService();
        Discovery discovery = discoveryService.getDiscoveryById(id);
        if (request.getUserPrincipal()!=null) {
            CommentService commentService = new CommentService();
            commentService.addComment(content,user,discovery);
            response.sendRedirect(request.getContextPath()+"/comment?discovery_id="+id);
        } else {
            response.sendError(403);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("discovery_id"));
        saveDiscoveryInRequest(request,id);
        saveCommentsInRequest(request,id);
        request.getRequestDispatcher("/WEB-INF/discovery.jsp").forward(request,response);
    }

    private void saveDiscoveryInRequest (HttpServletRequest request, long id){
        DiscoveryService discoveryService = new DiscoveryService();
        Discovery discoveryById = discoveryService.getDiscoveryById(id);
        request.setAttribute("discoveryById",discoveryById);
    }

    private void saveCommentsInRequest (HttpServletRequest request, long id){
        CommentService commentService = new CommentService();
        List<Comment> comments = commentService.getAllDiscoveryComments(id);
        request.setAttribute("comments",comments);
    }
}
