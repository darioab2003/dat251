<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="author" content="Nacho Alcalde">
    <title>Project Dat251</title>
    <link rel="stylesheet" href="css/registro.css">
    <link rel="icon" href="img/icono.svg">
</head>

<body>
    <header>
        <div id="bannerAzul">
            <div id="encuentraEstancia">
                <div id="logo">Project Dat251</div>
                <div id="icono1" class="iconosInicioSesion"><img src="img/bandera.png" width="30" alt="Flag Icon"></div>
                <div id="icono2" class="iconosInicioSesion"><img src="img/ayudaWhite.png" width="30" alt="Help Icon"></div>
            </div>
        </div>
    </header>

    <div id="login-container">
        <p class="login-message">Sign up and create a new account</p>
        
        <form action="RegistroServlet.do" method="post">
            <div class="form-group">
                <label for="name" class="form-label">First Name:</label>
                <input type="text" id="name" name="name" class="form-input" placeholder="First Name">
            </div>
            <div class="form-group">
                <label for="surname" class="form-label">Last Name:</label>
                <input type="text" id="surname" name="surname" class="form-input" placeholder="Last Name">
            </div>
            <div class="form-group">
            	<label for="email" class="form-label">E-mail:</label>
            	<input type="text" id="email" name="email" class="form-input" placeholder="Enter your email address">
            	<c:if test="${not empty messages.email}">
                	<div class="error-message">Invalid email: This email is already registered</div>
            	</c:if>
        	</div>

            <div class="form-group">
                <label for="password" class="form-label">Password:</label>
                <input type="password" id="password" name="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\W).{8,}" title="Invalid password: must contain at least 8 characters, including a number, a lowercase letter, an uppercase letter, and a special character" class="form-input" placeholder="Password">
            </div>
            <button id="submit-button" class="submit-button">Register</button>
        </form>
        
        <div class="separator-line">
            <span class="separator-text">or use one of these options</span>
        </div>
        <div id="registration-options">
            <div class="registration-option"><img src="img/icono_facebook.png" width="30" alt="Facebook Icon"></div>
            <div class="registration-option"><img src="img/icono_google.png" width="30" alt="Google Icon"></div>
            <div class="registration-option"><img src="img/icono_apple.png" width="30" alt="Apple Icon"></div>
        </div>
        <div class="separator"></div>
        <p class="disclaimer-text">By signing in or creating an account, you agree to our <span class="link-text">Terms and Conditions</span> and <span class="link-text">Privacy Policy</span>.</p>
        <div class="separator"></div>
        <p class="copyright-text">All rights reserved.</p>
        <p class="copyright-text">Copyright (2006-2024) - Booking.com</p>
    </div>
</body>

</html>
