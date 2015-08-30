<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="zh-CN">
<head>
	<%@include file="/partials/common_header.jspf" %>
	<link href="<s:url value="css/step-entity-edb-mapping.css" />" rel="stylesheet">
	<link href="<s:url value="css/icomoon-font.css" />" rel="stylesheet">
	<link rel="stylesheet" href="<s:url value="js/lib/jstree/themes/default/style.css" />" />

</head>
<body>
  <header>
  	<%int step=4; %>
  	<%@include file="/partials/navbar.jspf" %>
  	<%@include file="/partials/step_detail.jspf" %>
  </header>

  <section class="container toolbox">
  	<div class="container form-group">
  		<label class="col-xs-2">Database Connection</label>
  		<div class="col-xs-5">
  			<input type="text" class="form-control" readonly value="" id="input-db-conn-string"/>
  		</div>
  		<button class="btn btn-primary col-xs-2" id="btn-db-config">Configure</button>
  		<button class="btn btn-primary col-xs-2" id="btn-db-import">Import Schema</button>
  	</div>
  	
  	<div class="container form-group">
  		<button class="btn btn-default col-xs-2 ">Toggle Mapping Lines</button>
  		<button class="btn btn-default col-xs-2 ">View Mapping Rules <span class="badge">0</span></button>
  		<button class="btn btn-success col-xs-2 ">Submit</button>
  		
  	</div>
  </section>

  <section class="container" id="mappingbox">
  	<div id="entity_tree" class="col-lg-6">
  		
  	</div>
  	<div id="db_tables" class="col-lg-5 col-lg-offset-1">
  	</div>
  </section>

  <section class="" id="mapping_panel">
    <div class="container vertical-center">
      <div class="col-md-5" id="entity_path">
        <span></span>
      </div>
      <div class="col-md-2 btn-group" id="btn-confirm">
        <button class="btn btn-success btn-block">Confirm</button>
      </div>
      <div class="col-md-5" id="db_path">
        <span></span>
      </div>
    </div>
  </section>

  <div class="modal fade" id="db-config-modal">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title">Confiure Database Connection</h4>
        </div>
        <div class="modal-body">
    			<div>
    				<div class="form-group col-md-12">
    					<label>Database</label>
    					<select class="selectpicker form-control node-type" id="sel-edb-type"
    						data-dropup-auto="false" data-hide-disabled="true">
    						<option	value="mysql">Mysql</option>
    					</select>
    				</div>
    			</div>
    			<div>
            <div class="form-group col-md-8">
    					<label>Host</label>
    					<div>
    						<input type="text" class="form-control" id="input-edb-host" value="127.0.0.1"/>
    					</div>
    				</div>
    				<div class="form-group col-md-4">
    					<label>Port</label>
    					<div>
    						<input type="text" class="form-control" id="input-edb-port" value="3306"/>
    					</div>
    				</div>
    				<div class="form-group col-md-6">
    					<label>User</label>
    					<div>
    						<input type="text" class="form-control" id="input-edb-user" value="root"/>
    					</div>
    				</div>
    				<div class="form-group col-md-6">
    					<label>Password</label>
    					<div>
    						<input type="password" class="form-control" id="input-edb-password" value="root"/>
    					</div>
    				</div>
    				<div class="form-group col-md-12">
    					<label>Database Name</label>
    					<div>
    						<input type="text" class="form-control" id="input-edb-dbname" value="edb"/>
    					</div>
    				</div>
    			</div>
        </div>
        <div class="modal-footer">
          <span class="form-control-static input-xlarge" id="span-db-msg"></span>
          <button type="button" class="btn btn-default" id="btn-db-test-connection">Test Connection</button>
          <button type="button" class="btn btn-primary" id="btn-db-save">Save</button>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->
  
  <script type="text/javascript">
var entity_json = <s:property value="process.entityJSON" escape="false"/>;
var db_info = <s:property value="process.dbJSON" escape="false"/>;
  </script>
  <script type="text/javascript" src="<s:url value="js/lib/d3/d3.min.js" />"></script>
  <script type="text/javascript" src="<s:url value="js/lib/jstree/jstree.js" />"></script>
  <script type="text/javascript" src="<s:url value="js/lib/jstree/jstree.sega.js" />"></script>
  <script type="text/javascript" src="<s:url value="js/step-entity-edb-mapping.js" />"></script>
  <script type="text/javascript" src="<s:url value="js/step-entity-edb-mapping.mappingtool.js" />"></script>

</body>
</html>
