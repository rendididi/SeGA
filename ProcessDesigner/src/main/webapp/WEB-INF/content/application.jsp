<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="en" ng-app="app">
<head>
    <meta charset="utf-8">
    <title>My AngularJS Struts2 App</title>

    <base href="<s:url forceAddSchemeHostAndPort="true" includeContext="true" value="/" namespace="/" />">
    
    <!-- 新 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="<s:url value="js/lib/bootstrap-3.3.5-dist/css/bootstrap.min.css" />">
	<!-- 可选的Bootstrap主题文件（一般不用引入） -->
	<link rel="stylesheet" href="<s:url value="js/lib/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css" />">
	<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
	<script src="<s:url value="js/lib/jquery/jquery-2.1.4.min.js" />"></script>
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="<s:url value="js/lib/bootstrap-3.3.5-dist/js/bootstrap.min.js" />"></script>
</head>
<body>

<h2><s:property value="message"/></h2>

<div>
    <a href="/home">Home</a> - <a href="/projects">Projects</a>
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
