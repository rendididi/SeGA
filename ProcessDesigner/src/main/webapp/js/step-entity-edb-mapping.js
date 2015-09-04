var tree = null;


$(function() {

  $('#entity_tree').jstree({
    "core" : {
      "animation" : false,
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
    "plugins" : [  "types", "wholerow", "sega" ]

  });

  tree = $("#entity_tree").jstree(true);
  

  populateDBTable(
    database_json.tables,
    database_json.columns,
    database_json.keys
  );

  $("#entity_tree").on("ready.jstree" ,function(){
    tree.open_all();
    mapping_tool.init();
    mapping_tool.loadRules(ed_rules);
  });

  $("button#btn-navbar-submit").on("click",function(e){
    var rules_json = JSON.stringify(mapping_tool.mapping_rules);
    $("#hidden-ruleJSON").val(rules_json);
    $("#form-submit").submit();
  });

});



//TODO: wrap db component

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

    table_dom.append($("<thead><tr><th class='mapping_status'></th><th>Key</th><th>Name</th><th>Type</th></tr></thead>"));

    var tbody_dom = $("<tbody/>").appendTo(table_dom);

    for(var j=0,len_j=columns.length;j<len_j;j++){

      if(columns[j].TABLE_NAME == tables[i].TABLE_NAME){

        var tr_dom = $("<tr/>")
          .attr("data-column-name", columns[j].COLUMN_NAME)
          .click(function(){
            selectTableRow($(this));
          })
          .appendTo(tbody_dom);

        $("<td/>")
          .addClass("isMapped")
          .appendTo(tr_dom);

        var key_dom = $("<td/>").appendTo(tr_dom);
        var isKey = lookUpKey(columns[j], keys);
        if(isKey.isPK) {
          $("<span/>")
            .text("PK")
            .addClass("PK")
            .appendTo(key_dom);
          tr_dom.attr("isPK", true);
        }
        else if(isKey.isFK) {
          $("<span/>")
            .text("FK")
            .addClass("FK")
            .attr("data-fk-ref-table", isKey.refFK.table)
            .attr("data-fk-ref-column", isKey.refFK.column)
            .appendTo(key_dom);
          tr_dom.attr("isFK", true);
          tr_dom.attr("refFK-table", isKey.refFK.table);
          tr_dom.attr("refFK-column", isKey.refFK.column);
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
  $("#db_tables>table>tbody>tr").removeClass("active");
  $("#db_tables").trigger("sega.db.table_state_change");
}

function selectTableRow(row){
  //open table
  var table_dom = $("#db_tables table[data-table-name='"+row.parent().parent().attr("data-table-name")+"']");
  if(table_dom.hasClass("closed")){
    $("#db_tables>table")
      .addClass("closed");
    table_dom.removeClass("closed");
    $("#db_tables").trigger("sega.db.table_state_change");
  }
  // activate row
  var column = row.attr("data-column-name");
  $("#db_tables>table>tbody>tr").removeClass("active");
  row.addClass("active");
  $("#db_tables").trigger("sega.db.selected");
}

function mapTableRow(row){
  var td = row.children("td.isMapped");
  td.html("");
  $("<span/>")
    .addClass("sega-jstree-mapicon glyphicon glyphicon-ok")
    .appendTo(td);
}

function demapTableRow(row){
  var td = row.children("td.isMapped");
  td.html("");
}

function findRow(table, column) {
  var r = $("#db_tables table[data-table-name='"+table+"'] tr[data-column-name='"+column+"']");
  return r.length>0? r: false;
}