<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="../styles/main.css"/>
</head>
<body class="login-page">

<form method="POST" class="login-form" name="login" action="../j_spring_security_check">
    <div class="login-form__logo"></div>
    <fieldset class="login-form__fields">
        <label class="login-form__inp-wrap login-form__inp-wrap_login">
            <input type="text" name="j_username" class="login-form__inp" placeholder="ENTER YOUR LOGIN"/>
        </label>
        <label class="login-form__inp-wrap login-form__inp-wrap_pass">
            <input type="password" name="j_password" class="login-form__inp" placeholder="ENTER YOUR PASSWORD"/>
        </label>
        <input type="submit" name="submit" class="login-form__btn-submit"/>
    </fieldset>
    <label class="login-form__remember">
        <input type="checkbox" class="login-form__remember-inp"/>Remember me
    </label>
</form>

<div class="copyright">
    <span class="copyright__year">&copy; 2015</span> FGL Customer Service
</div>

</body>
</html>

