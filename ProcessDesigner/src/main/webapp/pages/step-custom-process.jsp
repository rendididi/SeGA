 <%@ page language="java"  import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<%@include file="/partials/common_header.jspf" %>
	<title>Insert title here</title>
	<link href="<s:url value="js/lib/joint/joint.all.css" />" rel="stylesheet">
	<link href="<s:url value="css/theme.css" />" rel="stylesheet">
	<link href="<s:url value="css/step-custom-process.css" />" rel="stylesheet">
  	<link href="<s:url value="css/icomoon-font.css" />" rel="stylesheet">
	
</head>
<body>
<%@include file="/partials/i18n.jsp" %>
	<header>
		<%int step=5; %>
		<%@include file="/partials/navbar.jspf" %>
		<%@include file="/partials/step_detail.jspf" %>
		<div class="container step-title2">
			<h1>
			<%=step_names[step-1] %>
			</h1>
			<div class="toolbox" >
				<span><!-- Create -->新建</span>
				<div class="toolShape startShape" id="startShape" draggable="true"></div>
				<div class="toolShape endShape" id="endShape" draggable="true"></div>
				<div class="toolShape taskShape" id="taskShape" draggable="true"></div>
				<div class="toolShape serviceShape" id="serviceShape" draggable="true"></div>
				<div class="toolShape linkShape" id="linkShape" draggable="true"></div>
				<!-- <img  src="<s:url value="images/task.png" />" draggble/> -->
			</div>
		</div>
	</header>


	<section class="relative">
		
		<div class="paper-container" id = "draw">

				<%@include file="/pages/step-custom-process-partials/step-custom-process-attrsForm.jsp" %>
		</div>
	</section>

	<div class="modal fade" id="modal_viewjson">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">流程 JSON</h4>
				</div>
				<div class="modal-body json-modal-body">
					<pre></pre>
				</div>
				<div class="modal-footer">
					<s:form action="step-custom-process-submit">
						<s:hidden name="process.processJSON" id="processJSON"></s:hidden>
						<s:hidden name="svg" id="input-svg"></s:hidden>
						<button type="button" class="btn btn-default" data-dismiss="modal"><!-- Close -->关闭</button>
						<button type="submit" class="btn btn-success">确定</button>
					</s:form>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->

	<script type="text/javascript">
	var process_json = <s:property value="process.processJSON" escape="false"/>;
	</script>
	<script type="text/javascript" src="<s:url value="js/lib/joint/joint.nojquery.js" />"></script>
	<script type="text/javascript" src="<s:url value="js/lib/joint/joint.sega_extension.js" />"></script>
	<script type="text/javascript" src="<s:url value="js/step-custom-process.js" />"></script>
</body>
</html>