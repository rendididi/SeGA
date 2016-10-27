 <%@ page language="java"  import="java.util.*" pageEncoding="UTF-8"%>
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
<%@include file="/partials/i18n.jsp" %>
	<header>
		<%int step=7; %>
		<%@include file="/partials/navbar.jspf"%>
		<%@include file="/partials/step_detail.jspf"%>
	</header>
	
	<section class="container publish">
		<div class="col-md-6 publish1 text-center">
			<img src="images/launch.svg" width=284 heigh=282 ></img>
			<s:form action="step-publish-submit">
			<button class="btn btn-primary btn-lg"><!-- Publish As Process -->发布为一个流程</button>
			</s:form>
		</div>
		<div class="col-md-6 publish1 text-center">
			<img src="images/publish2.svg" width=284 heigh=284 ></img>
			<s:form action="step-publish-template">
			<button class="btn btn-primary btn-lg"><!-- Publish As Template -->发布为一个模板</button>
			</s:form>
		</div>
	</section>
</body>
</html>
