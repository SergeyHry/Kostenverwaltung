package kostenverwaltung_package;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User_DatenZeigen {
	
	// EINGABEN
    public static List<String[]> getEingabenForUser(long userId, String art) throws SQLException {
        List<String[]> eingaben = new ArrayList<>();
        Connection conn = Db_conn.getConnection();
        
        String sql = "SELECT kosten, art, monat, datum, eingabe_id, details FROM Eingaben WHERE user_id = ?";
        PreparedStatement ps;

        if (art != null && !"alles".equals(art)) {
            sql += " AND art = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, userId);
            ps.setString(2, art);
        } else {
            ps = conn.prepareStatement(sql);
            ps.setLong(1, userId);
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            eingaben.add(new String[]{
                rs.getString("kosten"),
                rs.getString("art"),
                rs.getString("monat"),
                rs.getString("datum"),
                rs.getString("eingabe_id"),
                rs.getString("details")
            });
        }
        rs.close();
        ps.close();
        return eingaben;
    }

    
    //AUSGABEN

    public static List<String[]> getAusgabenForUser(long userId, String art, String monat) throws SQLException {
        List<String[]> ausgaben = new ArrayList<>();
        Connection conn = Db_conn.getConnection();

        StringBuilder sql = new StringBuilder(
            "SELECT kosten, art, monat, datum, ausgabe_id, details FROM Ausgaben WHERE user_id = ?"
        );

        List<Object> params = new ArrayList<>();
        params.add(userId);

        if (art != null && !"alles".equalsIgnoreCase(art)) {
            sql.append(" AND art = ?");
            params.add(art);
        }

        if (monat != null && !"alle".equalsIgnoreCase(monat)) {
            sql.append(" AND monat = ?");
            params.add(monat);
        }

        PreparedStatement ps = conn.prepareStatement(sql.toString());

        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String[] row = new String[6];
            row[0] = rs.getString("kosten");
            row[1] = rs.getString("art");
            row[2] = rs.getString("monat");
            row[3] = rs.getString("datum");
            row[4] = rs.getString("ausgabe_id");
            row[5] = rs.getString("details");
            ausgaben.add(row);
        }

        rs.close();
        ps.close();
        conn.close();

        return ausgaben;
    }
  
}
