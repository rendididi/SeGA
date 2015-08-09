<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="en" ng-app="app">
<head>
    <meta charset="utf-8">
    <title>My AngularJS Struts2 App</title>

    <base href="<s:url forceAddSchemeHostAndPort="true" includeContext="true" value="/" namespace="/" />">
    
    <!-- bootstrap & jquery -->
	<link rel="stylesheet" href="<s:url value="js/lib/bootstrap-3.3.5-dist/css/bootstrap.min.css" />">
	<link rel="stylesheet" href="<s:url value="js/lib/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css" />">
	<script src="<s:url value="js/lib/jquery/jquery-2.1.4.min.js" />"></script>
	<script src="<s:url value="js/lib/bootstrap-3.3.5-dist/js/bootstrap.min.js" />"></script>
	
	
</head>
<body>

<h2><s:property value="message"/></h2>

<div>
    <a href="/home">Home</a> - <a href="/projects">Projects</a>
    test
</div>

<div ng-controller="AppController">
    <div ng-view></div>
</div>

<script src="<s:url value="js/lib/angular/angular.min.js" />"></script>
<script src="<s:url value="js/lib/angular/angular-route.min.js" />"></script>
<script src="<s:url value="js/app.js" />"></script>
<script src="<s:url value="js/config.js" />"></script>
<script src="<s:url value="js/services/DataService.js" />"></script>
<script src="<s:url value="js/controllers/AppController.js" />"></script>
<script src="<s:url value="js/controllers/HomeController.js" />"></script>
<script src="<s:url value="js/controllers/ApacheProjectsController.js" />"></script>
</body>
</html>
