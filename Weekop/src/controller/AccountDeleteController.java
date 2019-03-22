package controller;

import model.Discovery;
import model.User;
import model.Vote;
import model.VoteType;
import service.CommentService;
import service.DiscoveryService;
import service.UserService;
import service.VoteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/deleteAccount")
public class AccountDeleteController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User userToDelete = (User) request.getSession().getAttribute("user");
        if (userToDelete != null) {
            VoteService voteService = new VoteService();
            DiscoveryService discoveryService = new DiscoveryService();
            CommentService commentService = new CommentService();
            boolean commentResult = commentService.deleteCommentsByUserId(userToDelete.getId());


            List<Discovery> discoveries = discoveryService.getAllDiscoveries(null);
            if (discoveries != null)
                discoveries.forEach(discovery -> {
                    int numOfComments = 0;
                    if (commentService.getAllDiscoveryComments(discovery.getId()) != null)
                        numOfComments = (commentService.getAllDiscoveryComments(discovery.getId())).size();
                    discovery.setNumberOfComments(numOfComments);
                    Vote vote = voteService.getVoteByDiscoveryUserId(discovery.getId(), userToDelete.getId());
                    if (vote != null) {
                        VoteType voteType = vote.getVoteType();
                        if (voteType == VoteType.VOTE_UP) {
                            discovery.setUpVote(discovery.getUpVote() - 1);
                        } else if (voteType == VoteType.VOTE_DOWN) {
                            discovery.setDownVote(discovery.getDownVote() - 1);
                        }
                    }
                    discoveryService.updateDiscovery(discovery);
                });
            boolean voteResult = voteService.deleteVotesByUserId(userToDelete.getId());
            boolean discoveryResult = discoveryService.deleteDiscoveryByUserId(userToDelete.getId());
            UserService userService = new UserService();
            boolean userRoleResult = userService.deleteUserFromUserRole(userToDelete.getUsername());
            if (voteResult && commentResult && discoveryResult && userRoleResult) {

                userService.deleteUser(userToDelete.getId());
                request.getSession().invalidate();
                response.sendRedirect(request.getContextPath() + "/");
            }
        } else {
            response.sendError(404);
        }
    }

    private void updateDiscoveryAfterDeleteUser(Discovery discovery) {

    }
}
