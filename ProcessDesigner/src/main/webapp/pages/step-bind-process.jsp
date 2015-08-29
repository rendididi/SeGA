<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="zh-CN">
<head>
	<%@include file="/partials/common_header.jspf" %>
	<link href="<s:url value="js/lib/joint/joint.all.css" />" rel="stylesheet">
	<link href="<s:url value="css/theme.css" />" rel="stylesheet">
	<link href="<s:url value="css/step-bind-process.css" />" rel="stylesheet">
	<link href="<s:url value="css/icomoon-font.css" />" rel="stylesheet">


</head>
<body>
  <header>
  	<%int step=6; %>
  	<%@include file="/partials/navbar.jspf" %>
  	<%@include file="/partials/step_detail.jspf" %>
  </header>
  <div class="col-md-2 fr">
	  <button class="btn-block btn btn-success " id="btn_viewjson">Submit</button>
  </div>
  	<section  class="paper-container" id = "draw">

	</section>


  <%@include file="/pages/step-bind-process_partials/task-config.jsp" %>
  	<script type="text/javascript">
	  	var process_json = <s:property value="processJSON" escape="false"/>;
	</script>
  	<script type="text/javascript" src="<s:url value="js/lib/joint/joint.nojquery.js" />"></script>
	<script type="text/javascript" src="<s:url value="js/lib/joint/joint.sega_extension.js" />"></script>
  <script type="text/javascript" src="<s:url value="js/lib/Pablo/pablo.js" />"></script>
	<script type="text/javascript" src="<s:url value="js/step-bind-process.js" />"></script>
 </body>
 </html>