<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="zh-CN">
<head>
	<%@include file="/partials/common_header.jspf" %>
	<link href="<s:url value="css/step3.css" />" rel="stylesheet">

</head>
<body>
  <header>
  	<%int step=3; %>
  	<%@include file="/partials/navbar.jspf" %>
  	<%@include file="/partials/step_detail.jspf" %>
  </header>

	<section class="container toolbox">
		<div class="container form-group">
			<label class="col-xs-2">Database Connection</label>
			<div class="col-xs-5">
				<input type="text" class="form-control" readonly value=""
					id="input-db-conn-string" />
			</div>
			<button class="btn btn-primary col-xs-2" id="btn-db-config">Configure</button>
			<button class="btn btn-primary col-xs-2" id="btn-db-import">Import
				Schema</button>
		</div>

		<div class="container form-group">
			<button class="btn btn-default col-xs-2 ">Toggle Mapping
				Lines</button>
			<button class="btn btn-default col-xs-2 ">
				View Mapping Rules <span class="badge">0</span>
			</button>
			<button class="btn btn-success col-xs-2 ">Submit</button>

		</div>
	</section>
</body>
 </html>