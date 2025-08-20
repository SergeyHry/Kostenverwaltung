package kostenverwaltung_package;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Sql_verbindung")
public class Sql_verbindung extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        pw.println("<html><body>");
        String vorname = request.getParameter("vorname");
        String nachname = request.getParameter("nachname");
        String email = request.getParameter("email");
        String adresse = request.getParameter("adresse");
        String passwort = request.getParameter("passwort");
        String hashedPassword = BCrypt.hashpw(passwort, BCrypt.gensalt(12));
        String encryptedEmail = null;
		try {
			encryptedEmail = Email_code.encrypt(email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
		try (Connection conn = Db_conn.getConnection()) {
		    // 1️⃣ SQL vorbereiten
		    String checkSql = "SELECT COUNT(*) FROM Users WHERE email = ?";
		    PreparedStatement checkPs = conn.prepareStatement(checkSql);
		    checkPs.setString(1, encryptedEmail);

		    // 2️⃣ Ausführen
		    ResultSet rs = checkPs.executeQuery();
		    rs.next();
		    int count = rs.getInt(1);

		    if (count > 0) {
		        pw.println("Diese Email ist vergeben");
		    } else {
		        // 3️⃣ Einfügen, wenn nicht vorhanden
		        String sql = "INSERT INTO Users (vorname, nachname, email, adresse, Passwort) VALUES (?, ?, ?, ?, ?)";
		        PreparedStatement ps = conn.prepareStatement(sql);
		        ps.setString(1, vorname);
		        ps.setString(2, nachname);
		        ps.setString(3, encryptedEmail); // hier ggf. vorher verschlüsseln
		        ps.setString(4, adresse);
		        ps.setString(5, hashedPassword); // gehashtes Passwort
		        ps.executeUpdate();
		       
		        
		        
               
                response.sendRedirect("/Users_KostenVerwaltung/Autorisierung.jsp");	// nach der Registrierung wird der User sofort zur Anmeldungsseite übergehen
                pw.println("</body></html>");}
        } catch (SQLException e) {
            e.printStackTrace();
            pw.println("Fehler bei der DB-Verbindung");
        }
    }
}
