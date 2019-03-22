package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import model.User;
import service.UserService;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    /**
     * Default constructor.
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        if (httpReq.getUserPrincipal()!=null&&httpReq.getSession().getAttribute("user")==null) {
            saveUserInSession(httpReq);
            saveAdminInSession(httpReq);
        }
        chain.doFilter(request, response);
    }


    private void saveUserInSession(HttpServletRequest request) {
        UserService userService = new UserService();
        String username = request.getUserPrincipal().getName();
        User userByUsername = userService.getUserByUsername(username);
        request.getSession(true).setAttribute("user", userByUsername);
    }

    private void saveAdminInSession(HttpServletRequest request) {
        UserService userService = new UserService();
        String username = request.getUserPrincipal().getName();
        User userByUsername = userService.getUserByUsername(username);
        if (userByUsername.getRole().equals("admin"))
            request.getSession(true).setAttribute("admin", userByUsername);
    }
    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

}
