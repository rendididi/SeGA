<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="zh-CN">
<head>
	<%@include file="/partials/common_header.jspf" %>
	<link href="<s:url value="css/step-entity-edb-mapping.css" />" rel="stylesheet">
	<link href="<s:url value="css/icomoon-font.css" />" rel="stylesheet">
	<link href="<s:url value="css/icomoon-font2.css" />" rel="stylesheet">
	<link rel="stylesheet" href="<s:url value="js/lib/jstree/themes/default/style.css" />" />

</head>
<body>
  <header>
  	<%int step=4; %>
  	<%@include file="/partials/navbar.jspf" %>
  	<%@include file="/partials/step_detail.jspf" %>
  </header>



  <section class="container" id="mappingbox">
  	<div id="entity_tree" class="col-md-4">
  		
  	</div>
  	<div id="db_tables" class="col-md-4 col-md-offset-4">
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

  <section>
    <s:form action="step-entity-edb-mapping-submit" id="form-submit">
      <s:hidden name="process.EDmappingJSON" id="hidden-ruleJSON"></s:hidden>
      <s:hidden name="process.entityJSON" id="hidden-entityJSON"></s:hidden>
    </s:form>
  </section>
  
  <script type="text/javascript">
var entity_json = <s:property value="process.entityJSON" escape="false"/>;
var database_json = <s:property value="process.databaseJSON" escape="false"/>;
var ed_rules = <s:property value="process.EDmappingJSON" escape="false"/>;
  </script>
  <script type="text/javascript" src="<s:url value="js/lib/d3/d3.min.js" />"></script>
  <script type="text/javascript" src="<s:url value="js/lib/jstree/jstree.js" />"></script>
  <script type="text/javascript" src="<s:url value="js/lib/jstree/jstree.sega.js" />"></script>
  <script type="text/javascript" src="<s:url value="js/step-entity-edb-mapping.js" />"></script>
  <script type="text/javascript" src="<s:url value="js/step-entity-edb-mapping.mappingtool.js" />"></script>

</body>
</html>
