package kostenverwaltung_package;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class logout
 */
@WebServlet("/logout")
public class logout extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false); // existierende Session holen
        if (session != null) {
            session.invalidate(); // Session beenden
        }
        
        response.sendRedirect(request.getContextPath() + "/Users_KostenVerwaltung/Autorisierung.jsp"); // zurück zur Loginseite
    }
}
