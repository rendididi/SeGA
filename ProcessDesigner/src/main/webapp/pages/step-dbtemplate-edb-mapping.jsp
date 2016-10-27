 <%@ page language="java"  import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html lang="zh-CN">
<head>
<%@include file="/partials/common_header.jspf"%>
<link href="<s:url value="css/step-dbtemplate-edb-mapping.css" />" rel="stylesheet">

</head>
<body>
<%@include file="/partials/i18n.jsp" %>
	<header>
		<%int step=3; %>
		<%@include file="/partials/navbar.jspf"%>
		<%@include file="/partials/step_detail.jspf"%>
	</header>

	<section class="container toolbox">
		<div class="container form-group">
			<label class="col-md-2"><!-- Database Connection -->数据库连接</label>
			<div class="col-md-4">
				<input type="text" class="form-control" readonly value=""
					id="input-db-conn-string" />
			</div>
			<div class="btn-group" role="group" aria-label="...">
				<button class="btn btn-primary" id="btn-db-config"><span class="glyphicon glyphicon-cog" aria-hidden="true"></span><!-- Configure -->配置</button>
				<button class="btn btn-primary" id="btn-db-import"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span><!-- Import Schema -->导入模型</button>
				<button class="btn btn-default"><!-- Toggle Mapping Lines -->变换映射线</button>
			</div>
		</div>
	</section>

	<section class="container db-titles">
		<div class="db_tables col-md-5"><h2><!-- Database Template -->数据库表模板</h2></div>
		<div class="db_tables col-md-5 col-md-offset-2"><h2><!-- Enterprise Database -->企业数据库</h2></div>
	</section>

	<section class="container" id="mappingbox">
		<div id="db_template" class="db_tables col-md-5"></div>
		<div id="edb" class="db_tables col-md-5 col-md-offset-2"></div>
	</section>

	<section class="" id="mapping_panel">
		<div class="container vertical-center">
		  <div class="col-md-5" id="entity_path">
		    <span></span>
		  </div>
		  <div class="col-md-2 btn-group" id="btn-confirm">
		    <button class="btn btn-success btn-block"><!-- Confirm -->确定</button>
		  </div>
		  <div class="col-md-5" id="db_path">
		    <span></span>
		  </div>
		</div>
	</section>	

	<section>
		<s:form action="step-dbtemplate-edb-mapping-submit" id="form-submit">
			<s:hidden name="process.databaseJSON" id="hidden-databaseJSON"></s:hidden>
			<s:hidden name="process.DDmappingJSON" id="hidden-ruleJSON"></s:hidden>
		</s:form>
	</section>

	<div class="modal fade" id="db-config-modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title"><!-- Confiure Database Connection -->配置数据库链接</h4>
				</div>
				<div class="modal-body">
					<div>
						<div class="form-group col-md-12">
							<label><!-- Database -->数据库</label> <select
								class="selectpicker form-control node-type" id="sel-edb-type"
								data-dropup-auto="false" data-hide-disabled="true">
								<option value="mysql">Mysql</option>
							</select>
						</div>
					</div>
					<div>
						<div class="form-group col-md-8">
							<label>Host</label>
							<div>
								<input type="text" class="form-control" id="input-edb-host"
									value="rm-2zetv12ybvdbxmro4o.mysql.rds.aliyuncs.com" />
							</div>
						</div>
						<div class="form-group col-md-4">
							<label>Port</label>
							<div>
								<input type="text" class="form-control" id="input-edb-port"
									value="3306" />
							</div>
						</div>
						<div class="form-group col-md-6">
							<label>User</label>
							<div>
								<input type="text" class="form-control" id="input-edb-user"
									value="sega_tester" />
							</div>
						</div>
						<div class="form-group col-md-6">
							<label>Password</label>
							<div>
								<input type="password" class="form-control"
									id="input-edb-password" value="sega_tester123" />
							</div>
						</div>
						<div class="form-group col-md-12">
							<label>Database Name</label>
							<div>
								<input type="text" class="form-control" id="input-edb-dbname"
									value="edb" />
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<span class="form-control-static input-xlarge" id="span-db-msg"></span>
					<button type="button" class="btn btn-default"
						id="btn-db-test-connection"><!-- Test Connection -->链接测试</button>
					<button type="button" class="btn btn-primary" id="btn-db-save"><!-- Save -->保存</button>
					<button type="button" class="btn btn-primary" id="btn-db-save-import"><!-- Save&Import -->保存且导入</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	<script type="text/javascript">
	
	var dbtJSON = <s:property value="dbt_json" escape="false"/>;
	var edbJSON = [<s:property value="process.databaseJSON" escape="false"/>];
	if(edbJSON.length>0)
		edbJSON = edbJSON[0];
	else
		edbJSON = false;

	var ruleJSON = [<s:property value="process.DDmappingJSON" escape="false"/>];
	if(ruleJSON.length>0)
		ruleJSON = ruleJSON[0];
	else
		ruleJSON = false;

	var dbParams = {
		obj: "<s:property value="process.dbconfig" escape="false"/>",
		host: "<s:property value="process.dbconfig.host" escape="false"/>",
		port: "<s:property value="process.dbconfig.port" escape="false"/>",
		type: "<s:property value="process.dbconfig.type" escape="type"/>",
		database_name: "<s:property value="process.dbconfig.database_name" escape="false"/>",
		username: "<s:property value="process.dbconfig.username" escape="false"/>",
		password: "<s:property value="process.dbconfig.password" escape="false"/>",
	};
	</script>
	<script type="text/javascript" src="<s:url value="js/lib/d3/d3.min.js" />"></script>
	<script type="text/javascript" src="<s:url value="js/step-dbtemplate-edb-mapping.mappingtool.js" />"></script>
	<script type="text/javascript" src="<s:url value="js/step-dbtemplate-edb-mapping.db.js" />"></script>
	<script type="text/javascript" src="<s:url value="js/step-dbtemplate-edb-mapping.js" />"></script>
</body>
</html>