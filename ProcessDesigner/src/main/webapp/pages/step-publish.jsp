<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html lang="zh-CN">
<head>
<%@include file="/partials/common_header.jspf"%>
<link href="<s:url value="css/step-custom-entity.css" />" rel="stylesheet">
<link href="<s:url value="css/icomoon-font.css" />" rel="stylesheet">
<link rel="stylesheet"
	href="<s:url value="js/lib/jstree/themes/default/style.css" />" />
</head>
<body>
	<header>
		<%int step=7; %>
		<%@include file="/partials/navbar.jspf"%>
		<%@include file="/partials/step_detail.jspf"%>
	</header>
	
	<section class="container">
	</section>
</body>
</html>
