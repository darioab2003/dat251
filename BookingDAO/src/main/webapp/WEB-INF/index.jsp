<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="author" content="Nacho Alcalde">
    <title>DAT251_SignIn</title>
    <link rel="stylesheet" href="css/style1.css">
    <link rel="icon" href="img/icono.svg">
</head>

<body>
    <header>
        <div id="bannerAzul">
            <div id="encuentraEstancia">
                <div id="logo">DAT251</div>
                <a href="RegistroServlet.do"><button class="inicioSesion">Sign up</button></a>
                <div id="icono1" class="iconosInicioSesion"><img src="img/bandera.png" width="30" alt="Icono Bandera"></div>
                <div id="icono2" class="iconosInicioSesion"><img src="img/ayudaWhite.png" width="30" alt="Icono Ayuda"></div>
            </div>
        </div>
    </header>
	
    <div id="login-container">
        <p class="login-message">Log in or Create an account</p>
        
        <form action="LoginServlet.do" method="post">
    		<div class="form-group">
        		<label for="email" class="form-label">E-mail:</label>
        		<input type="text" id="email" name="email" class="form-input" placeholder="Insert your E-mail">
    		</div>
    		<div class="form-group">
        		<label for="password" class="form-label">Password:</label>
        		<input type="password" id="password" name="password" class="form-input" placeholder="Insert your password">
    		</div>
    		
    		 <c:if test="${not empty loginMessage}">
            	<div class="error-message">${loginMessage}</div>
       		 </c:if>
    		
    		<div class="form-group">
        		<button id="submit-button" class="submit-button">Log in</button>
    		</div>
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