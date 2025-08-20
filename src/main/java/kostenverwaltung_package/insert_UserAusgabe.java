package kostenverwaltung_package;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/insert_UserAusgabe")
public class insert_UserAusgabe extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Long your_id = (Long) session.getAttribute("user_id");
		String  getDelete = request.getParameter("ausgabe_id");
		if (getDelete != null) {
			
		try (Connection conn = Db_conn.getConnection()){
			 String deleteSql = "DELETE FROM Ausgaben WHERE ausgabe_id = ?";
	            try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
	                stmt.setInt(1, Integer.parseInt(getDelete));
	                stmt.executeUpdate();
	             
	    	            response.sendRedirect("/Users_KostenVerwaltung/User_interface.jsp"); }
	        }
		catch (SQLException e) {
	            e.printStackTrace();}}
	        
	    else {
	float getAusgabenKosten =   Float.parseFloat(request.getParameter("kostenA"));
	String getAusgabenArt = request.getParameter("ausgaben");
	String getAusgabenMonat = request.getParameter("radio2");
	String getAusgabenDetails = request.getParameter("details");
	if (getAusgabenArt!= null) {
	try (Connection conn = Db_conn.getConnection()) {
		String ausgabeSql = "INSERT INTO Ausgaben (kosten,art, monat, user_id, details)VALUES (?,?,?,?,?)";
		PreparedStatement psA = conn.prepareStatement(ausgabeSql);
		psA.setFloat(1,getAusgabenKosten );
		psA.setString(2, getAusgabenArt);
		psA.setString(3, getAusgabenMonat);
		psA.setLong(4, your_id);
		psA.setString(5,getAusgabenDetails);
		if (your_id == null) {
		    response.sendRedirect("/Users_KostenVerwaltung/Autorisierung.jsp");
		    return;
		}
		
        int rowsE = psA.executeUpdate();
        if (rowsE > 0) {
            response.sendRedirect("/Users_KostenVerwaltung/User_interface.jsp");
        } else {
            response.getWriter().println("Fehler beim Speichern.");
        }
       
    } catch (SQLException e) {
        e.printStackTrace();
        response.getWriter().println("SQL-Fehler: " + e.getMessage());
    }
}		
	}}}
