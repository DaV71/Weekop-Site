package controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Discovery;
import service.DiscoveryService;

/**
 * Servlet implementation class HomeController
 */
@WebServlet("")
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;



    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        saveDiscoveriesInRequest(request);
        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }

    private void saveDiscoveriesInRequest(HttpServletRequest request) {
        DiscoveryService discoveryService = new DiscoveryService();
        List<Discovery> allDiscoveries = discoveryService.getAllDiscoveries(new Comparator<Discovery>() {

            @Override
            public int compare(Discovery arg0, Discovery arg1) {
                int d1Vote = arg0.getUpVote()-arg0.getDownVote();
                int d2Vote = arg1.getUpVote()-arg1.getDownVote();
                if (d1Vote<d2Vote)
                    return 1;
                else if (d1Vote>d2Vote)
                    return -1;
                else
                    return 0;
            }
            //more votes = higher
        });
        request.setAttribute("discoveries", allDiscoveries);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
