<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html lang="zh-CN">
<head>
<%@include file="/partials/common_header.jspf"%>
<link href="<s:url value="css/step-publish.css" />" rel="stylesheet">
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
	
	<section class="container publish">
		<div class="col-md-offset-3 col-md-6 publish1 text-center">
			<s:set value="publishType" var="type"/>
			<s:if test="%{#type=='process'}">
			<img src="images/launch.svg" width=500 heigh=500 ></img>
			</s:if>
			<s:elseif test="%{#type=='template'}">
			<img src="images/publish2.svg" width=500 heigh=500 ></img>
			</s:elseif>
			<s:form action="task-list">
			<button class="btn btn-primary btn-lg">Success! Go Back</button>
			</s:form>
		</div>
	</section>
</body>
</html>
