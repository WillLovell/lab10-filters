
package filters;

import dataaccess.UserDB;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;

public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
    {
        
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        HttpSession session = httpRequest.getSession();
        //get session
        String adminEmail = (String)session.getAttribute("email");
        //search db and match role
        UserDB udb = new UserDB();
        if(adminEmail != null){
        User u1 = udb.get(adminEmail);
        
        AccountService as = new AccountService();

        User user = as.login(u1.getEmail(), u1.getPassword());

        
         if (user.getRole().getRoleId() == 1) {
            chain.doFilter(request, response);
            httpResponse.sendRedirect("admin");
            return;
            
        }
         httpResponse.sendRedirect("notes");
         return;
         //chain.doFilter(request, response);
        }
        httpResponse.sendRedirect("login");
         return;
    }

    @Override
    public void destroy() {}

    
}
