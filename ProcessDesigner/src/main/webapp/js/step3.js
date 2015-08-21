var tree = null;


$(function() {

  $('#entity_tree').jstree({
    "core" : {
      "animation" : 200,
      "check_callback" : true,
      "themes" : {
        "stripes" : true,
        "dots" : false
      },
      'data' : entity_json
    },
    "types" : {
      "#" : {
        "max_children" : 1,
        "valid_children" : [ "artifact" ]
      },
      "artifact" : {
        "icon" : "images/tree_icons/artifact.svg",
        "valid_children" : [ "artifact", "attribute", "artifact_n","group","key" ]
      },
      "artifact_n" : {
        "icon" : "images/tree_icons/artifact_n.svg",
        "valid_children" : [ "artifact", "attribute", "artifact_n","group","key" ]
      },
      "attribute" : {
        "icon": "images/tree_icons/attribute.svg",
        "valid_children" : []
      },
      "group" : {
        "icon" : "images/tree_icons/group.svg",
        "valid_children" : ["attribute"]
      },
      "key" : {
        "icon": "images/tree_icons/key.svg",
        "valid_children" : []
      }
    },
    "plugins" : [  "dnd", "state", "types", "wholerow", "sega" ]

  });

  tree = $("#entity_tree").jstree(true);

});


$(function() {
  /*
   var $affix = $(".toolbox"), 
       $parent = $affix.parent(), 
       resize = function() { $affix.width($parent.width()); };
   $(window).resize(resize); 
   resize();
  */
  
  $("#btn-db-config").click(function(){
    $("#span-db-msg").html("");
    $("#db-config-modal").modal();
  });

  $("#btn-db-test-connection").click(testDBConnection);
  $("#btn-db-save").click(saveDBConnection);
  $("#btn-db-import").click(loadDBSchema);
});

function testDBConnection(){
  $("#span-db-msg").text("Testing...");
  
  $.ajax({
    method: "POST",
    url: "/edb-conn-test",
    data: {
      edbType: $("#sel-edb-type").val(),
      hostName: $("#input-edb-host").val(),
      portName: $("#input-edb-port").val(),
      userName: $("#input-edb-user").val(),
      password: $("#input-edb-password").val(),
      dbName: $("#input-edb-dbname").val()
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

function saveDBConnection(){
  $.ajax({
    method: "POST",
    url: "/edb-conn-save",
    data: {
      edbType: $("#sel-edb-type").val(),
      hostName: $("#input-edb-host").val(),
      portName: $("#input-edb-port").val(),
      userName: $("#input-edb-user").val(),
      password: $("#input-edb-password").val(),
      dbName: $("#input-edb-dbname").val()
    },
    success: function(msg){
      if($.trim(msg)==="true"){
        $("#input-db-conn-string").val("jdbc:mysql://"+$("#input-edb-host").val()+":"+$("#input-edb-port").val()+"/"+$("#input-edb-dbname").val());
        $("#span-db-msg").html("Success<span class=\"glyphicon glyphicon-ok\" aria-hidden=\"true\"></span>");
        $("#db-config-modal").modal('hide');
        
      }else{
        $("#span-db-msg").html("Fail<span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span>");
      }
    }
  });
}

function loadDBSchema(){
  $.ajax({
    method: "POST",
    url: "/edb-load-schema",
    data: {
      edbType: $("#sel-edb-type").val(),
      hostName: $("#input-edb-host").val(),
      portName: $("#input-edb-port").val(),
      userName: $("#input-edb-user").val(),
      password: $("#input-edb-password").val(),
      dbName: $("#input-edb-dbname").val()
    },
    success: function(msg){
      if(msg.loadSchemaResult){
        db_info = {
          tables: $.parseJSON(msg.tables_json),
          columns: $.parseJSON(msg.columns_json),
          keys: $.parseJSON(msg.keys_json)
        };
        
        populateDBTable(
          db_info.tables, 
          db_info.columns,
          db_info.keys
        );
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

function populateDBTable(tables, columns, keys){
  $("#db_tables").empty();
  
  for(var i=0, len=tables.length;i<len;i++){

    var table_dom = $("<table/>")
      .attr("data-table-name", tables[i].TABLE_NAME)
      .addClass("table")
      .addClass("table-condensed")
      .addClass("table-striped")
      .addClass("closed")
      .appendTo("#db_tables");

    var cap_dom = $("<caption/>")
      .text(tables[i].TABLE_COMMENT?tables[i].TABLE_COMMENT:tables[i].TABLE_NAME)
      .click(function(){
        toggleTable($(this).parent().attr("data-table-name"));
      })
      .appendTo(table_dom);

    table_dom.append($("<thead><tr><th class='mapping_status'></th><th>Key</th><th>Name</th><th>Type</th>"));

    var tbody_dom = $("<tbody/>").appendTo(table_dom);

    for(var j=0,len_j=columns.length;j<len_j;j++){

      if(columns[j].TABLE_NAME == tables[i].TABLE_NAME){

        var tr_dom = $("<tr/>")
          .attr("data-column-name", columns[j].COLUMN_NAME)
          .click(function(){
            selectTableRow($(this));
          })
          .appendTo(tbody_dom);

        $("<td/>").appendTo(tr_dom);
        var key_dom = $("<td/>").appendTo(tr_dom);
        var isKey = lookUpKey(columns[j], keys);
        if(isKey.isPK) {
          $("<span/>")
            .text("PK")
            .addClass("PK")
            .appendTo(key_dom);
        }
        else if(isKey.isFK) {
          $("<span/>")
            .text("FK")
            .addClass("FK")
            .attr("data-fk-ref-table", isKey.refFK.table)
            .attr("data-fk-ref-column", isKey.refFK.column)
            .appendTo(key_dom);
        }

        $("<td class='name'/>")
          .text(columns[j].COLUMN_COMMENT?columns[j].COLUMN_COMMENT:columns[j].COLUMN_NAME)
          .appendTo(tr_dom);

        $("<td/>")
          .text(columns[j].COLUMN_TYPE)
          .appendTo(tr_dom);;
         
      }
    }
  }

  $().trigger("sega.db.table_state_change");
}

function lookUpKey(column, keys){
  var result = {
    isPK:false,
    isFK:false,
    refFK: {table: "", column: ""}
  };
  for(var i=0,len=keys.length; i<len; i++){
    if(column.COLUMN_NAME == keys[i].COLUMN_NAME){
      if(keys[i].CONSTRAINT_NAME === "PRIMARY" && !keys[i].REFERENCED_TABLE_NAME && !keys[i].REFERENCED_COLUMN_NAME){
        result.isPK = true;
      }else{
        result.isFK = true;
        result.refFK.table = keys[i].REFERENCED_TABLE_NAME;
        result.refFK.column = keys[i].REFERENCED_COLUMN_NAME;
      }
    }
  }

  return result;
}

function toggleTable(table){
  var table_dom = $("#db_tables table[data-table-name='"+table+"']");
  if(table_dom.hasClass("closed")){
    $("#db_tables>table")
      .addClass("closed");
    table_dom.removeClass("closed");
  }else {
    $("#db_tables>table")
      .addClass("closed");
  }

  $("#db_tables").trigger("sega.db.table_state_change");
}

function selectTableRow(row){
  var column = row.attr("data-column-name");
  $("#db_tables>table>tbody>tr").removeClass("active");
  row.addClass("active");
  $("#db_tables").trigger("sega.db.selected");
}

