package controller;

import model.*;
import service.CommentService;
import service.DiscoveryService;
import service.VoteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/deleteComment")
public class CommentDeleteController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long commentToDeleteId = Long.valueOf(request.getParameter("comment_to_delete_id"));
        CommentService commentService = new CommentService();
        Comment commentToDelete = commentService.getComment(commentToDeleteId);
        long discoveryId = commentToDelete.getDiscovery().getId();
        User loggedUser = (User)request.getSession().getAttribute("user");
        User admin = (User)request.getSession().getAttribute("admin");
        if (loggedUser.getUsername().equals(commentToDelete.getUser().getUsername()) || admin != null) {
                commentService.deleteComment(commentToDeleteId);
                DiscoveryService discoveryService = new DiscoveryService();
            List<Discovery> discoveries = discoveryService.getAllDiscoveries(null);
            if (discoveries != null)
                discoveries.forEach(discovery -> {
                    int numOfComments = 0;
                    if (commentService.getAllDiscoveryComments(discovery.getId()) != null)
                        numOfComments = (commentService.getAllDiscoveryComments(discovery.getId())).size();
                    discovery.setNumberOfComments(numOfComments);
                    discoveryService.updateDiscovery(discovery);
                });
            response.sendRedirect(request.getContextPath()+"/comment?discovery_id="+discoveryId);
        } else {
            response.sendError(403);
        }
    }


}
