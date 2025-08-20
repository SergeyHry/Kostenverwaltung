<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Willkomen,  <%= session.getAttribute("vorname")%> </title>
 <link rel="stylesheet" href="Style_for_App.css">
</head>

<body>

<div class = "ober">
<h1> Wilkommen,   <%= session.getAttribute("vorname") %> </h1>
</div>
<form action="<%= request.getContextPath() %>/logout" method="get" style="text-align:right;">
<button type="submit" id = "logout">Logout</button>
    
</form>
<%@ page import="java.util.*, kostenverwaltung_package.User_DatenZeigen" %>
<%
if (session.getAttribute("user_id") == null) {
    response.sendRedirect("/Autorisierung.jsp");
    return;
}

long userId = (Long) session.getAttribute("user_id");

// Filterwert aus Request holen
String filter = request.getParameter("ausgabenArt");
String monat = request.getParameter("monat");
if (monat == null) monat = "alle";
String filter2 = request.getParameter("eingabenArt");

// Daten holen
List<String[]> eingaben = null;
List<String[]> ausgaben = null;
try {

       
   		
        ausgaben = User_DatenZeigen.getAusgabenForUser(userId, filter, monat);
        eingaben = User_DatenZeigen.getEingabenForUser(userId, filter2);
    
} catch (Exception e) {
    out.println("<p style='color:red;'>Fehler beim Laden der Daten: " + e.getMessage() + "</p>");
}

%>

<div class= "parent">
<div class = "Container" id = "left">
<form Action = "/Users_KostenVerwaltung/insert_UserEingabe" method = "post">

	<h2>Einkommen: </h2>
  <label for="einkommen">Einkommen (Art):</label>
   <select name = "einkomen"  >
 <option value ="none"> </option>
<option value = "gehalt"> Gehalt</option>
<option value = premie> Premie</option>
<option value = "sozialleistung"> Sozialleistung</option>
<option value = "sonstige">Sonstige</option>
  </select>
   <label for = "details"> Details: </label> <br>
  <input type ="text" name = "details" id = "details"><br>
   <label for="kosten">Kosten (€):</label><br>
  <input type="number" step="0.01" id="kosten" name="kosten" ><br>
    <label for="monatEingabe">Monatlich:</label><br>
  <input type="radio" id="radio" name="radio" value = "ja" >Ja <input type = "radio" id = "radio" name = "radio" value= "nein" >nein<br>
  <input type ="submit" id = "button2" value = "Eintragen" >
  <label for = "eingabenArt"></label><br>
    <table border="1">
        <tr><th>Kosten (€)</th><th>Art</th><th>Monatlich</th><th>Datum</th><th>Details</th></tr>
         
        
        <% float sumE = 0;
        if(ausgaben!=null){
            for (String[] rowE : eingaben) {
            	float a = Float.parseFloat(rowE[0]);
            	sumE +=a; %>
      
            <tr>
                <td><%= rowE[0] %></td>
                <td><%= rowE[1] %></td>
                <td><%= rowE[2] %></td>
                <td><%= rowE[3] %></td>
                 <td ><%= rowE[5] %></td>   
           <td>
			<button type="submit" name="eingabe_id" value="<%= rowE[4] %>">x</button>
			</td>
       				
            </tr>
        <%
            }} %><h3><%out.println("Summe (gsmt): " + sumE +" €"); %></h3>
        
    </table>  
    </form>
</div>



<div class = "Container" id = "right">

<form Action = "/Users_KostenVerwaltung/insert_UserAusgabe" method = "post">
<h2> Ausgaben: </h2>

  <label for="ausgaben">Ausgaben (Art):</label> 
   <select name = "ausgaben"  >
 <option value ="none"> </option>
<option value = "Abo's"> Abos</option>
<option value = Lebensmittel> Lebensmittel</option>
<option value = "Miete"> Miete</option>
<option value = "Sonstige">Sonstige</option>
  </select>
  <label for = "details"> Details: </label> 
  <input type ="text" name = "details" id = "details"><br>
 
  <label for="kostenA">Kosten (€):</label><br>
  <input type="number" step="0.01" id="kostenA" name="kostenA" ><br>
    <label for="monatAusgabe">Monatlich:</label><br>
  <input type="radio" id="radio" name="radio2" value = "ja" >Ja <input type = "radio" id = "radio" name = "radio2" value= "nein" >nein<br>
  <input type ="submit" id = "button2" value = "Eintragen" >

<label for = "ausgabenArt"></label>  

    <table border="1">
        <tr><th>Kosten (€)</th>
										        <th> art</th>

  <th>Monatlich</th><th>Datum</th><th>Details</th></tr>
           <% float sum = 0;
            for (String[] row : ausgaben) {
            	float a = Float.parseFloat(row[0]);
            	sum +=a;
        %>
            <tr>
                <td><%= row[0] %></td>
                <td><%= row[1] %></td>
                <td><%= row[2] %></td>
                <td><%= row[3] %></td>
                 <td><%= row[5] %></td>
           <td><button type="submit" name="ausgabe_id" value="<%= row[4] %>">x</button></td>
       
            </tr>
        <%
            }%> <% 
         
    double prozent = (sum / sumE) * 100;%>   
<h3><% out.println("Summe (gsmt): " + sum + " €"); %></h3>
<h3>Prozent von Einkommen: <%= String.format("%.2f", prozent) %>%</h3>
           
  
      
    </table>
 </form>    
</div>
<div class= "Filter">
 <form method="get" action="User_interface.jsp">
  <!-- Eingaben-Filter -->
  <label for="eingabenArt">Filter Einkommen:</label>
  <select name="eingabenArt" onchange="this.form.submit()">
    <option value="alles" <%= "alles".equals(request.getParameter("eingabenArt")) ? "selected" : "" %>>Alles</option>
    <option value="gehalt" <%= "gehalt".equals(request.getParameter("eingabenArt")) ? "selected" : "" %>>Gehalt</option>
    <option value="premie" <%= "premie".equals(request.getParameter("eingabenArt")) ? "selected" : "" %>>Premien</option>
    <option value="sozialleistung" <%= "sozialleistung".equals(request.getParameter("eingabenArt")) ? "selected" : "" %>>Sozialleistung</option>
    <option value="sonstige" <%= "sonstige".equals(request.getParameter("eingabenArt")) ? "selected" : "" %>>Sonstige</option>
  </select><br>

  <!-- Ausgaben-Filter -->
  <label for="ausgabenArt">Filter Ausgaben:</label>
  <select name="ausgabenArt" onchange="this.form.submit()">
    <option value="alles" <%= "alles".equals(request.getParameter("ausgabenArt")) ? "selected" : "" %>>Alles</option>
    <option value="abo's" <%= "abo's".equals(request.getParameter("ausgabenArt")) ? "selected" : "" %>>Abo's</option>
    <option value="lebensmittel" <%= "lebensmittel".equals(request.getParameter("ausgabenArt")) ? "selected" : "" %>>Lebensmittel</option>
    <option value="miete" <%= "miete".equals(request.getParameter("ausgabenArt")) ? "selected" : "" %>>Miete</option>
    <option value="sonstige" <%= "sonstige".equals(request.getParameter("ausgabenArt")) ? "selected" : "" %>>Sonstige</option>
   
  </select><br>
    <label for="monat">Monatlich:</label><br>
  <select name="monat" onchange="this.form.submit()">
    <option value="alle" <%= "alle".equals(request.getParameter("monat")) ? "selected" : "" %>>Alle</option>
    <option value="ja" <%= "ja".equals(request.getParameter("monat")) ? "selected" : "" %>>Ja</option>
    <option value="nein" <%= "nein".equals(request.getParameter("monat")) ? "selected" : "" %>>Nein</option>
  </select><br>
  <div class="Saldo_Container">
  <div class="Saldo_Circle"> 
    <h1>Saldo:</h1>
    <h2><%= String.format("%.2f", sumE - sum) %> €</h2>  
  </div> 
</div>
</form>
</div>
</div>




</body>
</html>