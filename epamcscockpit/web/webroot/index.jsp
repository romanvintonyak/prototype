<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html ng-app="epamcscockpit">
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="styles/main.css"/>
    
    <script src="js/lib/angular.js"></script> 
    <script src="js/lib/angular-route.js"></script> 
    <script src="js/lib/angular-resource.js"></script> 
    
    <script src="js/filters.js"></script> 
    <script src="js/services.js"></script>  
	<script src="js/controllers.js"></script> 
</head>

<body>
<header class="header"> 
    <div class="container">

        <a href="#/" class="header__logo">
            <img src="img/logo.png" alt=""/>
            <span class="header__logo-descr">customer service</span>
        </a>

        <div class="header__cta">
            <div class="header__cta-inner">
                <button class="header__cta-btn">create</button>
                <div class="header__cta-dropdown">
                    <a href="#/customer_create" class="header__cta-dropdown-link">Customer</a>
                    <a href="#/order_create" class="header__cta-dropdown-link">Order</a>
                    <a href="#/ticket_create" class="header__cta-dropdown-link">Ticket</a>
                </div>
            </div>
        </div>

        <form action="#" class="header-search">
            <fieldset class="header-search__fields">
                <input class="header-search__inp" type="text" placeholder="quick search"/>
                <input class="header-search__submit" type="submit"/>
            </fieldset>
            <div class="header-search__cta-wrap">
                <span class="header-search__cta">
                    <a href="#/advanced_search" class="header-search__cta-link">advanced search</a><a href="#" class="header-search__cta-ext-link" target="_blank"></a>
                </span>
            </div>
        </form>
        <div class="header-notification">
            X
        </div>

        <div class="header-user">
            <div class="header-user__name">
            	<sec:authentication property="principal.displayName"/>
            </div>
            <div class="header-user__role">
            	<!--CS Ticket Pool Group-->
            </div>
            
        </div>

    </div>
</header>
<!-- /header -->

<section class="sub-header">
    
</section>
<!-- /sub-header -->
<main class="main-wrap" ng-view></main>

</body>
</html>