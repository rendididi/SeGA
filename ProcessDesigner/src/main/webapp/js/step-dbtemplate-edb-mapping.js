var dbt, edb;

$(function() {
  /*
   var $affix = $(".toolbox"), 
       $parent = $affix.parent(), 
       resize = function() { $affix.width($parent.width()); };
   $(window).resize(resize); 
   resize();
  if(db_info){
    populateDBTable(
        db_info.tables, 
        db_info.columns,
        db_info.keys
      );
  }
  */
  $("button#btn-navbar-submit").on("click",function(e){
    var rules_json = JSON.stringify(mapping_tool.mapping_rules);
    var db_json = JSON.stringify(edb.getJSON());
    //$("#modal_viewjson .modal-body p").html(json);
    $("#hidden-databaseJSON").val(db_json);
    $("#hidden-ruleJSON").val(rules_json);
    $("#form-submit").submit();
  });
  
  $("#btn-db-config").click(function(){
    $("#span-db-msg").html("");
    $("#db-config-modal").modal();
  });

  $("#btn-db-test-connection").click(testDBConnection);
  $("#btn-db-save").click(saveDBConnection);
  $("#btn-db-save-import").click(true, saveDBConnection);
  $("#btn-db-import").click(loadDBSchema);

  edb = new DBTable($("#edb"));
  dbt = new DBTable($("#db_template"));

  if(dbtJSON){
    dbt.populateDBTable(dbtJSON, {mapbox:"right"});
  }
  if(edbJSON){
    edb.populateDBTable(edbJSON, {mapbox:"left"});
  }

  mapping_tool.init(dbt, edb);
  
  if(dbtJSON&& edbJSON){ // TODO: Load rules if exist
    mapping_tool.loadRules(ruleJSON);
  }

  if(dbParams.obj){
    $("#sel-edb-type").val(dbParams.type);
    $("#input-edb-host").val(dbParams.host);
    $("#input-edb-port").val(dbParams.port);
    $("#input-edb-user").val(dbParams.username);
    $("#input-edb-password").val(dbParams.password);
    $("#input-edb-dbname").val(dbParams.database_name);
    $("#input-db-conn-string").val("jdbc:mysql://"+$("#input-edb-host").val()+":"+$("#input-edb-port").val()+"/"+$("#input-edb-dbname").val());
  }
});

function testDBConnection(){
  $("#span-db-msg").text("Testing...");
  
  $.ajax({
    method: "POST",
    url: "/edb-conn-test",
    data: {
        "dbconfig.type": $("#sel-edb-type").val(),
        "dbconfig.host": $("#input-edb-host").val(),
        "dbconfig.port": $("#input-edb-port").val(),
        "dbconfig.username": $("#input-edb-user").val(),
        "dbconfig.password": $("#input-edb-password").val(),
        "dbconfig.database_name": $("#input-edb-dbname").val()
    },
    success: function(msg){
      if($.trim(msg)==="true"){
        $("#span-db-msg").html("Success<span class=\"glyphicon glyphicon-ok\" aria-hidden=\"true\"></span>");
      }else{
        $("#span-db-msg").html("Fail<span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span>");
      }
    }
  });
}

function saveDBConnection(evt){
  $.ajax({
    method: "POST",
    url: "/edb-conn-save",
    data: {
      "dbconfig.type": $("#sel-edb-type").val(),
      "dbconfig.host": $("#input-edb-host").val(),
      "dbconfig.port": $("#input-edb-port").val(),
      "dbconfig.username": $("#input-edb-user").val(),
      "dbconfig.password": $("#input-edb-password").val(),
      "dbconfig.database_name": $("#input-edb-dbname").val()
    },
    success: function(msg){
      if($.trim(msg)==="true"){
        $("#input-db-conn-string").val("jdbc:mysql://"+$("#input-edb-host").val()+":"+$("#input-edb-port").val()+"/"+$("#input-edb-dbname").val());
        $("#span-db-msg").html("Success<span class=\"glyphicon glyphicon-ok\" aria-hidden=\"true\"></span>");
        $("#db-config-modal").modal('hide');
        
      }else{
        $("#span-db-msg").html("Fail<span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span>");
      }
      
      if(evt.data)
        loadDBSchema();
    }
  });
}

function loadDBSchema(){
  
  $.ajax({
    method: "POST",
    url: "/edb-load-schema",
    data: {
      "dbconfig.type": $("#sel-edb-type").val(),
      "dbconfig.host": $("#input-edb-host").val(),
      "dbconfig.port": $("#input-edb-port").val(),
      "dbconfig.username": $("#input-edb-user").val(),
      "dbconfig.password": $("#input-edb-password").val(),
      "dbconfig.database_name": $("#input-edb-dbname").val()
    },
    success: function(msg){
      if(msg.loadSchemaResult){
        db_json = {
          tables: $.parseJSON(msg.dbconfig.tables_json),
          columns: $.parseJSON(msg.dbconfig.columns_json),
          keys: $.parseJSON(msg.dbconfig.keys_json)
        };
        
        mapping_tool.removeAllRules();
        edb.populateDBTable( db_json, false );
        autoMap();
      }else{
        $("#btn-db-import").popover({
          placement: "bottom",
          html: true,
          title: '<span class="text-danger"><strong>Error</strong></span>'+
                '<button type="button" id="close" class="close" onclick="$(&quot;#btn-db-import&quot;).popover(&quot;hide&quot;);">&times;</button>',
          content: "Failed to import database schema. Please check your database configuration.",
          trigger: "manual"
        });

        $("#btn-db-import").popover("show");
      }
    }
  });
}

function autoMap(){
  for(var i=0, len=dbt.getJSON().columns.length;i<len;i++){
    var column1 =dbt.getJSON().columns[i];
    var r1 = dbt.findRow(column1.TABLE_NAME, column1.COLUMN_NAME);
    var r2 = edb.findRow(column1.TABLE_NAME, column1.COLUMN_NAME);
    if(r1 && r2) {
      mapping_tool._doMap(r1, r2);
    }
  }

}
