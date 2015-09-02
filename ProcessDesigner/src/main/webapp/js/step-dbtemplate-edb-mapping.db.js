var DBTable = function(dom) {

var _dom = dom;

this.getDom = function(){
  return _dom;
};

var _json = {};
this.getJSON = function(){
  return _json;
};


this.populateDBTable = function(databaseJSON, option){
  _json = databaseJSON;
  var tables = databaseJSON.tables, 
  	columns = databaseJSON.columns, 
  	keys = databaseJSON.keys;
  	
  _dom.empty();
  
  for(var i=0, len=tables.length;i<len;i++){

    var table_dom = $("<table/>")
      .attr("data-table-name", tables[i].TABLE_NAME)
      .addClass("table")
      .addClass("table-condensed")
      .addClass("table-striped")
      .addClass(option&&option.closed?"closed":"")
      .appendTo(_dom);

    var cap_dom = $("<caption/>")
      .text(tables[i].TABLE_COMMENT?tables[i].TABLE_COMMENT:tables[i].TABLE_NAME)
      .appendTo(table_dom);
    if(option&&option.closable){
      cap_dom.click(function(){
        toggleTable($(this).parent().attr("data-table-name"));
      });
    }

    if(!(option&&option.mapbox=="right")){
      table_dom.append($("<thead><tr><th class='mapping_status'></th><th>Key</th><th>Name</th><th>Type</th></tr></thead>"));
    }else{
      table_dom.append($("<thead><tr><th>Key</th><th>Name</th><th>Type</th><th class='mapping_status'></th></tr></thead>"));
    }

    var tbody_dom = $("<tbody/>").appendTo(table_dom);

    for(var j=0,len_j=columns.length;j<len_j;j++){

      if(columns[j].TABLE_NAME == tables[i].TABLE_NAME){
        var thisDB= this;
        var tr_dom = $("<tr/>")
          .attr("data-column-name", columns[j].COLUMN_NAME)
          .click(function(){
            thisDB.selectTableRow($(this)); //ugly
          })
          .appendTo(tbody_dom);

        if(!(option&&option.mapbox=="right")){
          $("<td/>")
            .addClass("isMapped")
            .appendTo(tr_dom);
        }

        var key_dom = $("<td/>").appendTo(tr_dom);
        var isKey = this.lookUpKey(columns[j], keys);
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
          .appendTo(tr_dom);

        if(option&&option.mapbox=="right"){
          $("<td/>")
            .addClass("isMapped")
            .appendTo(tr_dom);
        }
         
      }
    }
  }

  _dom.trigger("sega.db.table_state_change");
}

this.lookUpKey = function(column, keys){
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
};

this.selectTableRow = function(row){
  // activate row
  var column = row.attr("data-column-name");
  _dom.find("table>tbody>tr").removeClass("active");
  row.addClass("active");
  _dom.trigger("sega.db.selected");
};

this.mapTableRow = function(row){
  var td = row.children("td.isMapped");
  td.html("");
  $("<span/>")
    .addClass("sega-mapicon glyphicon glyphicon-ok")
    .appendTo(td);
};

this.demapTableRow = function(row){
  var td = row.children("td.isMapped");
  td.html("");
}

this.findRow = function(table, column) {
  var r = _dom.find("table[data-table-name='"+table+"'] tr[data-column-name='"+column+"']");
  return r.length>0? r: false;
}

}; // end of class DBTable