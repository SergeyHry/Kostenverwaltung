package kostenverwaltung_package;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/Zugang_Users")
public class Zugang_Users extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		        response.setContentType("text/html;charset=UTF-8");
		        PrintWriter pw = response.getWriter();
		        pw.println("<html><body>");
		        String email = request.getParameter("email");
		        String passwort = request.getParameter("passwort");	    
		        String encryptedEmail = null;
		    
				try {
					encryptedEmail = Email_code.encrypt(email);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    
				try (Connection conn = Db_conn.getConnection()) {
				    // 1️⃣ SQL vorbereiten
					String sql = "SELECT user_id, Passwort, vorname FROM Users WHERE email = ? ";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1, encryptedEmail);
					ResultSet rs = ps.executeQuery();
					if (rs.next()) {
						 long userId = rs.getLong("user_id");
					    String storedHash = rs.getString("Passwort");
					   

					    if (BCrypt.checkpw(passwort, storedHash)) {
					        //  Passwort korrekt
					    	HttpSession old = request.getSession(false);
			                    if (old != null) old.invalidate();
			                    HttpSession session = request.getSession(true);
			                    session.setAttribute("user_id", userId);
			                    session.setAttribute("vorname", rs.getString("vorname"));
			              
			                    session.setMaxInactiveInterval(30 * 60);
			                    response.sendRedirect(request.getContextPath() +"/User_interface.jsp");
			                    return;
							}
					    if (!BCrypt.checkpw(passwort, storedHash)) {
					    	pw.println("<p> Passwort oder E-mail ist falsch! </p>");
					    }
					    }
					    
					 else {
						 
		                response.sendRedirect("/Users_KostenVerwaltung/Autorisierung.jsp");	// nach der Registrierung wird der User sofort zur Anmeldungsseite übergehen
		                pw.println("</body></html>");}
		        } catch (SQLException e) {
		            e.printStackTrace();
		            pw.println("Fehler bei der DB-Verbindung");
		        }
	}

}
