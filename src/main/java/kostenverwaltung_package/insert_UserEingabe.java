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

/**
 * Servlet implementation class insert_Users
 */
@WebServlet("/insert_UserEingabe")
public class insert_UserEingabe extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Long your_id = (Long) session.getAttribute("user_id");
		String  getDelete = request.getParameter("eingabe_id");
		if (getDelete != null){
			
		try (Connection conn = Db_conn.getConnection()){
			 String deleteSql = "DELETE FROM Eingaben WHERE eingabe_id = ?";
	            try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
	                stmt.setInt(1, Integer.parseInt(getDelete));
	                stmt.executeUpdate();
	             
	    	            response.sendRedirect("/Users_KostenVerwaltung/User_interface.jsp"); }
	        }
		catch (SQLException e) {
	            e.printStackTrace();}}
	        
	    else {
	    	float getEingabenKosten =  Float.parseFloat(request.getParameter("kosten"));
			String getEinngabenArt = request.getParameter("einkomen");
			String getEingabenMonat = request.getParameter("radio");
			String getEingabenDetails = request.getParameter("details");
			
			  
	    	try (Connection conn = Db_conn.getConnection()){
		String eingabeSql = "INSERT INTO Eingaben (kosten,art, monat, user_id, details)VALUES (?,?,?,?,?)";
			PreparedStatement psE = conn.prepareStatement(eingabeSql);
			psE.setFloat(1, getEingabenKosten);
			psE.setString(2, getEinngabenArt);
			psE.setString(3,getEingabenMonat);
			psE.setLong(4, your_id);
			psE.setString(5, getEingabenDetails);
			if (your_id == null) {
			    response.sendRedirect("/Users_KostenVerwaltung/Autorisierung.jsp");
			    return;
			}
			
	        int rowsE = psE.executeUpdate();
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
}
}
