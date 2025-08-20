<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Kostenverwaltung - Login</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background: linear-gradient(135deg, #e6f4ea, #fffde7);
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
    }
    .Container {
        background: #ffffff;
        padding: 30px;
        border-radius: 12px;
        box-shadow: 0 6px 15px rgba(0,0,0,0.15);
        width: 80%;
        text-align: center;
    }
    h1 {
        color: #4caf50;
        margin-bottom: 20px;
    }
    label {
        display: block;
        text-align: left;
        margin: 10px 0 5px;
        font-weight: bold;
        color: #333;
    }
    input[type="email"], 
    input[type="password"] {
        width: 90%;
        padding: 10px;
        margin-bottom: 15px;
        border: 1px solid #ccc;
        border-radius: 8px;
    }
    input[type="submit"] {
        width: 90%;
        padding: 12px;
        background: linear-gradient(145deg, #8bc34a, #cddc39);
        border: none;
        border-radius: 8px;
        font-size: 16px;
        font-weight: bold;
        color: #fff;
        cursor: pointer;
        transition: 0.3s;
    }
    input[type="submit"]:hover {
        background: linear-gradient(145deg, #7cb342, #afb42b);
    }
    a {
        display: block;
        margin-top: 15px;
        color: #4caf50;
        text-decoration: none;
        font-weight: bold;
    }
    a:hover {
        text-decoration: underline;
    }
</style>
</head>
<body>
<form action="/Users_KostenVerwaltung/Zugang_Users" method="post">
<div class="Container">
    <h1>Kostenverwaltung-Login</h1>
    <label for="email">E-Mail: </label>
    <input type="email" name="email" placeholder="E-Mail-Adresse" required>
    <label for="passwort">Passwort:</label>
    <input type="password" name="passwort" placeholder="Passwort" required>
    <input type="submit" value="Login">
    <a href="/Users_KostenVerwaltung/Login.jsp">Nicht registriert?</a>
</div>
</form>
</body>
</html>
