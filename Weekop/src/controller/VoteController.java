package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Discovery;
import model.User;
import model.Vote;
import model.VoteType;
import service.DiscoveryService;
import service.VoteService;

/**
 * Servlet implementation class VoteController
 */
@WebServlet("/vote")
public class VoteController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User loggedUser = (User) request.getSession().getAttribute("user");
        if (loggedUser != null&&loggedUser.isActive()) {
            VoteType voteType = VoteType.valueOf(request.getParameter("vote"));
            long userId = loggedUser.getId();
            long discoveryId = Long.parseLong(request.getParameter("discovery_id"));
            updateVote(userId,discoveryId,voteType);
            response.sendRedirect(request.getContextPath()+"/");
        } else{
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }

    }

    private void updateVote(long userId, long discoveryId, VoteType voteType) {
        VoteService voteService = new VoteService();
        Vote existingVote = voteService.getVoteByDiscoveryUserId(discoveryId, userId);
        Vote updatedVote = voteService.addOrUpdateVote(discoveryId, userId, voteType);
        if (existingVote != updatedVote || !updatedVote.equals(existingVote)) {
            updateDiscovery(discoveryId,existingVote,updatedVote);
        }

    }

    private void updateDiscovery(long discoveryId, Vote oldVote, Vote newVote) {
        DiscoveryService discoveryService = new DiscoveryService();
        Discovery discoveryById = discoveryService.getDiscoveryById(discoveryId);
        Discovery updatedDiscovery =null;
        if (oldVote == null && newVote != null) {
            updatedDiscovery = addDiscoveryVote(discoveryById,newVote.getVoteType());
        } else if (oldVote != null && newVote != null && oldVote.getVoteType() != newVote.getVoteType()) {
            updatedDiscovery = removeDiscoveryVote(discoveryById,oldVote.getVoteType());
            if (newVote.getVoteType() == VoteType.VOTE_DOWN)
                updatedDiscovery.setDownVote((addDiscoveryVote(discoveryById, newVote.getVoteType())).getDownVote());
            else if (newVote.getVoteType() == VoteType.VOTE_UP)
                updatedDiscovery.setUpVote((addDiscoveryVote(discoveryById, newVote.getVoteType())).getDownVote());
        }
        if (updatedDiscovery != null)
            discoveryService.updateDiscovery(updatedDiscovery);

    }

    private Discovery removeDiscoveryVote(Discovery discoveryById, VoteType voteType) {
        Discovery discoveryCopy = new Discovery (discoveryById);
        if (voteType == VoteType.VOTE_UP) {
            discoveryCopy.setUpVote(discoveryCopy.getUpVote()-1);
        } else if (voteType == VoteType.VOTE_DOWN) {
            discoveryCopy.setDownVote(discoveryCopy.getDownVote()-1);
        }
        return discoveryCopy;
    }

    private Discovery addDiscoveryVote(Discovery discoveryById, VoteType voteType) {
        Discovery discoveryCopy = new Discovery(discoveryById);
        if (voteType == VoteType.VOTE_UP) {
            discoveryCopy.setUpVote(discoveryCopy.getUpVote()+1);
        } else if (voteType == VoteType.VOTE_DOWN) {
            discoveryCopy.setDownVote(discoveryCopy.getDownVote()+1);
        }
        return discoveryCopy;
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
