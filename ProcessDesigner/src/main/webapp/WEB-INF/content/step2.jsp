<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="zh-CN">
<head>
	<%@include file="/partials/common_header.jspf" %>
	<link href="<s:url value="css/step2.css" />" rel="stylesheet">
  <link href="<s:url value="css/icomoon-font.css" />" rel="stylesheet">
	<link rel="stylesheet" href="<s:url value="js/lib/jstree/themes/default/style.css" />" />
	<script src="<s:url value="js/lib/jstree/jstree.js" />"></script>
  <script src="<s:url value="js/lib/jstree/jstree.sega.js" />"></script>
</head>
<body>
	<header>
		<%int step=2; %>
		<%@include file="/partials/navbar.jspf" %>
		<%@include file="/partials/step_detail.jspf" %>
	</header>
	<section class="container">
		<div id="entity_tree" class="col-lg-7">
			
		</div>

		<div class="col-lg-4 col-lg-offset-1 toolbox_container">
      <div class="toolbox" data-spy="affix" data-offset-top="400" data-offset-bottom="0">
  			<div class="form-group">
  				<label>Name</label>
  				<div>
  				  <input type="text" class="form-control node-name"/>
  				</div>
  			</div>

  			<div class="form-group">
  				<label>Node</label>
          <div>
  				  <select class="selectpicker form-control node-type" data-dropup-auto="false" data-hide-disabled="true">
  				    <option data-content="<span class='node_artifact node_icon'>Artifact</span>" value="artifact">Artifact</option>
  				    <option data-content="<span class='node_artifact_n node_icon'>Artifact 1-n</span>" value="artifact_n">Artifact 1-n</option>
  				    <option data-content="<span class='node_attribute node_icon'>Attribute</span>" value="attribute">Attribute</option>
              <option data-content="<span class='node_group node_icon'>Group</span>" value="group">Attribute Group</option>
              <option data-content="<span class='node_key node_icon'>Key</span>" value="key">Key</option>
  				  </select>
          </div>
  			</div>

        <div class="form-group">
          <label>Type</label>
          <div>
            <select class="selectpicker form-control value-type" data-dropup-auto="false">
              <option value="auto">Auto</option>
              <option value="number">Number</option>
              <optgroup label="String">
                <option value="string-string">String - 1 line</option>
                <option value="string-textarea">String - TextArea</option>
              </optgroup>
              <option value="file">File</option>
            </select>
          </div>
        </div>

        <div class="form-group create">
          <label>Create
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
          </label>
          <button type="button" class="btn btn-primary btn-md btn-block" data-type="artifact">
            <span class="icon-artifact"></span>
            Artifact
          </button>
          <button type="button" class="btn btn-primary btn-md btn-block" data-type="artifact_n">
            <span class="icon-artifact_n">
              <span class="path1"></span><span class="path2"></span><span class="path3"></span><span class="path4"></span><span class="path5"></span><span class="path6"></span><span class="path7"></span><span class="path8"></span><span class="path9"></span><span class="path10"></span><span class="path11"></span><span class="path12"></span>
            </span>
            Artifact_n
          </button>
          <button type="button" class="btn btn-primary btn-md btn-block" data-type="attribute">
            <span class="icon-attribute"></span>
            Attribute
          </button>
          <button type="button" class="btn btn-primary btn-md btn-block" data-type="group">
            <span class="icon-group"></span>
            Attribute Group
          </button>
          <button type="button" class="btn btn-primary btn-md btn-block" data-type="key">
            <span class="icon-key"></span>
            Key
          </button>
        </div>

        <div class="form-group">
          <label>Delete
            <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
          </label>
          <button type="button" class="btn btn-danger btn-md btn-block remove-btn">
            Delete
          </button>
        </div>

        <div class="form-group">
          <label>Submit
            <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
          </label>
          <button type="button" class="btn btn-success btn-md btn-block" id="btn_viewjson">
            Submit
          </button>
        </div>
      </div>
 		</div><!-- End if Toolbox-->
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
var entity_json = <s:property value="entityJSON" escape="false"/>;
  </script>	
  <script type="text/javascript" src="<s:url value="js/step2.js" />"></script>
</body>
</html>
