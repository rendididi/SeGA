<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="zh-CN" ng-app="processConfig">
<head>
	<%@include file="/partials/common_header.jspf" %>
	<link href="<s:url value="js/lib/joint/joint.all.css" />" rel="stylesheet">
	<link href="<s:url value="css/theme.css" />" rel="stylesheet">
	<link href="<s:url value="css/step-bind-process.css" />" rel="stylesheet">
	<link href="<s:url value="css/icomoon-font.css" />" rel="stylesheet">
	<link href="<s:url value="css/icomoon-font.css" />" rel="stylesheet">
	<link rel="stylesheet"
		  href="<s:url value="js/lib/jstree/themes/default/style.css" />" />

</head>
<body>
	<header>
		<%int step=6; %>
		<%@include file="/partials/navbar.jspf" %>
		<%@include file="/partials/step_detail.jspf" %>
	</header>

	<section  class="paper-container" id = "draw"></section>


  	<%@include file="/pages/step-bind-process_partials/task-config.jsp" %>

	<div class="modal fade" id="modal_viewjson">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">Process JSON</h4>
				</div>
				<div class="modal-body json-modal-body">
					<p></p>
				</div>
				<div class="modal-footer">
					<s:hidden name="process.processJSON" id="processJSON"></s:hidden>
					<s:hidden name="svg" id="svg"></s:hidden>
					<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->

  	<script type="text/javascript">
		var process_json = (<s:property value="process.processJSON" escape="false"/>);
		var entity_json = (<s:property value="process.entityJSON" escape="false"/>);
		var binding_json = [<s:property value="process.bindingJSON" escape="false"/>];
		if(binding_json.length>0)
			binding_json = binding_json[0];
		else
			binding_json = {
				read:[],
				write:[],
				service:[]
			};
	</script>
  	<script type="text/javascript" src="<s:url value="js/lib/joint/joint.nojquery.js" />"></script>
	<script type="text/javascript" src="<s:url value="js/lib/joint/joint.sega_extension.js" />"></script>
  	<script type="text/javascript" src = "<s:url value="js/lib/angular/angular.js" />"></script>
  	<script type="text/javascript" src = "<s:url value="js/lib/jstree/jstree.js" />"></script>
  	<script type="text/javascript" src = "<s:url value="js/lib/jstree/jstree.sega.task_data.js" />"></script>
	<script type="text/javascript" src="<s:url value="js/step-bind-process.js" />"></script>
 </body>
 </html>