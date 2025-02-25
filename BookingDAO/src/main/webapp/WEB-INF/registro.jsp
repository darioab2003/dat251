<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="author" content="Nacho Alcalde">
    <title>DAT251_SignUp</title>
    <link rel="stylesheet" href="css/registro.css">
    <link rel="icon" href="img/icono.svg">
</head>

<body>
    <header>
        <div id="bannerAzul">
            <div id="encuentraEstancia">
                <div id="logo">DAT251</div>
                <div id="icono1" class="iconosInicioSesion"><img src="img/bandera.png" width="30" alt="Icono Bandera"></div>
                <div id="icono2" class="iconosInicioSesion"><img src="img/ayudaWhite.png" width="30" alt="Icono Ayuda"></div>
            </div>
        </div>
    </header>

    <div id="login-container">
        <p class="login-message">Create an account</p>
        
        <form action="RegistroServlet.do" method="post">
            <div class="form-group">
                <label for="name" class="form-label">Name:</label>
                <input type="text" id="name" name="name" class="form-input" placeholder="Name">
            </div>
            <div class="form-group">
                <label for="surname" class="form-label">Last name:</label>
                <input type="text" id="surname" name="surname" class="form-input" placeholder="Last name">
            </div>
            <div class="form-group">
            	<label for="email" class="form-label">E-mail:</label>
            	<input type="text" id="email" name="email" class="form-input" placeholder="email">
            	<c:if test="${not empty messages.email}">
                	<div class="error-message">Wrong E-mail: That e-mail is already registered</div>
            	</c:if>
        	</div>

            <div class="form-group">
                <label for="password" class="form-label">Password:</label>
                <input type="password" id="password" name="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\W).{8,}" title="Contraseña incorrecta: debe contener al menos 8 caracteres incluyendo un número, una minúscula, una mayúscula y un carácter especial" class="form-input" placeholder="Contraseña">
            </div>
            <button id="submit-button" class="submit-button">Register</button>
        </form>
        
        <div class="separator-line">
            <span class="separator-text">or use one of these options</span>
        </div>
        <div id="registration-options">
            <div class="registration-option"><img src="img/icono_facebook.png" width="30" alt="Icono Facebook"></div>
            <div class="registration-option"><img src="img/icono_google.png" width="30" alt="Icono Google"></div>
            <div class="registration-option"><img src="img/icono_apple.png" width="30" alt="Icono Apple"></div>
        </div>
        <div class="separator"></div>
        <p class="disclaimer-text">When logging in or creating an account, you are accepting our <span class="link-text">Terms and conditions</span> and <span class="link-text">Privacy Policy</span>.</p>
        <div class="separator"></div>
        <p class="copyright-text">All rights reserved.</p>
        <p class="copyright-text">Copyright (2006-2024) - Booking.com</p>
    </div>
</body>

</html>