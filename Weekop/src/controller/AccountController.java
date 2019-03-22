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
import java.util.Comparator;
import java.util.List;

@WebServlet("/account")
public class AccountController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        if (user!=null && user.isActive()) {
            long userId = user.getId();
            saveUserDiscoveriesInRequest(request,userId);
            request.getRequestDispatcher("/WEB-INF/account.jsp").forward(request,response);
        } else {
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }

    }

    private void saveUserDiscoveriesInRequest(HttpServletRequest request, long userId) {
        DiscoveryService discoveryService = new DiscoveryService();
        List<Discovery> allUserDiscoveries = discoveryService.getAllUserDiscoveries(userId,new Comparator<Discovery>() {

            @Override
            public int compare(Discovery arg0, Discovery arg1) {
                long d1Id = arg0.getId();
                long d2Id = arg1.getId();
                if (d1Id<d2Id)
                    return 1;
                else if (d1Id>d2Id)
                    return -1;
                else
                    return 0;
            }
            //smaler id = higher
        });
        request.setAttribute("discoveriesByUserId", allUserDiscoveries);
    }
}
