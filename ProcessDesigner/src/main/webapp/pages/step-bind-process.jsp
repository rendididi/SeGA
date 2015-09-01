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
	  	var process_json = <s:property value="process.processJSON" escape="false"/>;
	</script>
  	<script type="text/javascript" src="<s:url value="js/lib/joint/joint.nojquery.js" />"></script>
	<script type="text/javascript" src="<s:url value="js/lib/joint/joint.sega_extension.js" />"></script>
  	<script type="text/javascript" src = "<s:url value="js/lib/angular/angular.js" />"></script>
	<script type="text/javascript" src="<s:url value="js/step-bind-process.js" />"></script>
 </body>
 </html>