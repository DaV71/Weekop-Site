package controller;

import model.Discovery;
import model.User;
import service.CommentService;
import service.DiscoveryService;
import service.VoteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete")
public class DiscoveryDeleteController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long discoveryToDeleteId = Long.valueOf(request.getParameter("discovery_to_delete_id"));
        DiscoveryService discoveryService = new DiscoveryService();
        Discovery discoveryToDelete = discoveryService.getDiscoveryById(discoveryToDeleteId);
        User loggedUser = (User)request.getSession().getAttribute("user");
        User admin = (User)request.getSession().getAttribute("admin");
        if (loggedUser.getUsername().equals(discoveryToDelete.getUser().getUsername()) || admin != null) {
            VoteService voteService = new VoteService();
            boolean voteResult = voteService.deleteVotesByDiscoveryId(discoveryToDeleteId);
            CommentService commentService = new CommentService();
            boolean commentsResult = commentService.deleteCommentsByDiscoveryId(discoveryToDeleteId);
            if (commentsResult && voteResult) {
                discoveryService.deleteDiscovery(discoveryToDeleteId);
            }
            if (admin == null) {
                response.sendRedirect(request.getContextPath() + "/account");
            } else {
                response.sendRedirect(request.getContextPath()+"/");
            }
        } else {
            response.sendError(403);
        }
    }
}
