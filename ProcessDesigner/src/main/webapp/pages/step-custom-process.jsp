
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
	<header>
			<%int step=5; %>
			<%@include file="/partials/navbar.jspf" %>
			<%@include file="/partials/step_detail.jspf" %>
	</header>
	<section class=" relative">
		<%@include file="/pages/step4_partials/step4_attrsForm.jsp" %>

		<div class="toolbox" >
			<div class="startShape" id="startShape" draggable="true"></div>
			<div class="endShape" id="endShape" draggable="true"></div>
			<div class="taskShape" id="taskShape" draggable="true"></div>
			<div class="linkShape" id="linkShape" draggable="true"></div>
			<!-- <img  src="<s:url value="images/task.png" />" draggble/> -->
		</div>
		<div class="paper-container" id = "draw">
	<div class="col-md-2 fr">
		<button class="btn-block btn btn-success " id="btn_viewjson">Submit</button>
	</div>
		</div>
		
	</section>

	<div class="modal fade" id="modal_viewjson">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">Entity JSON</h4>
				</div>
				<div class="modal-body">
					<p></p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
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