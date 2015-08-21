var mapping_tool = {
  _svg: null,
  _suggest_line: null,
  _diagonal: d3.svg.diagonal()
    .source(function(d) { return {"x":d.source.y, "y":d.source.x}; })            
    .target(function(d) { return {"x":d.target.y, "y":d.target.x}; })
    .projection(function(d) { return [d.y, d.x]; }),

  init: function(){
    $($.proxy(function(){
      //init svg element
      this._svg = d3.select("#mappingbox").append("svg:svg")
        .attr("id","svg_mapping_tool")
        .style("position","absolute")
        .style("top", 0)
        .style("left", $("#entity_tree").width());
      this.resize();

      //register events
      $(window).resize($.proxy(this.resize, this));
      $("#entity_tree").on("ready.jstree",$.proxy(this.resize, this));
      $("#db_tables").on("sega.db.table_state_change",$.proxy(this.resize, this));

      $("#entity_tree").on("select_node.jstree",$.proxy(this.drawSuggestion, this));
      $("#db_tables").on("sega.db.selected",$.proxy(this.drawSuggestion, this));
    },this));
  },

  resize: function(){
    this.clear();
    this._svg.attr("width", $("#db_tables").outerWidth(true) - $("#db_tables").outerWidth(false))
        .attr("height", Math.max($("#db_tables").height(), $("#entity_tree").height()));
  },

  drawSuggestion: function() {
    var tree = $("#entity_tree").jstree(true);
    var tree_node_dom = $(tree.get_node(tree.get_selected(), true));
    var db_row_dom = $("#db_tables>table>tbody>tr.active");
    
    if(db_row_dom.length==0 || tree_node_dom.length==0)
      return;

    var src = {
      x: 0,
      y: tree_node_dom.position().top+16
    };
    var tgt = {
      x: this._svg.attr("width"),
      y: db_row_dom.offset().top - $("#db_tables").offset().top + db_row_dom.height()/2
    };
    var path_data = [{
      source: src,
      target: tgt
    }];
    
    this.clear();

    function onMouseOver(e){
      d3.select(this).classed("active", true);
    }

    function onMouseOut(e){
      d3.select(this).classed("active", false);
    }

    function onMouseClick(e){
      $("#svg_mapping_tool").trigger("sega.mapping.svg.link.click");
    }

    this._suggest_line = this._svg
      .append("g")
      .append("path")
      .data(path_data)
      .attr("d", this._diagonal)
      .attr("stroke", "#beebff")
      .attr("stroke-width", 32)
      .attr("fill", "none")
      .classed("suggest_line", true)
      .on("mouseover", onMouseOver)
      .on("mouseout", onMouseOut)
      .on("click", onMouseClick);
    return true;
  },

  clear: function(){
    this._svg.select("*").remove();
    this._suggest_line = null;
  }

}.init();

$(function(){
  $("#svg_mapping_tool").on("sega.mapping.svg.link.click", function(){
    $("#mapping_panel").show().height(70);
    $("#mapping_panel #entity_path span").text($("#entity_tree").jstree(true).get_selected_node_sega_path());
    var db_dom = $("#db_tables>table>tbody>tr.active");
    $("#mapping_panel #db_path span").text(db_dom.parent().parent().find("caption").text()+"/"+db_dom.find("td.name").text());
  });
  $("#db_tables").on("sega.db.selected sega.db.table_state_change", function(){
    $("#mapping_panel").height(0);
  });
  $("#entity_tree").on("select_node.jstree", function(){
    $("#mapping_panel").height(0);
  });

});