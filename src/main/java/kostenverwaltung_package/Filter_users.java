package kostenverwaltung_package;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter("/User_interface")
public class Filter_users implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            chain.doFilter(req, res); // пропускаем дальше (JSP или Servlet)
        } else {
            response.sendRedirect(request.getContextPath() + "/Users_KostenVerwaltung/Autorisierung.jsp");
        }
    }
}