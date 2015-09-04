var mapping_tool = {
  _svg: null,
  _suggest_line: null,
  _diagonal: d3.svg.diagonal()
    .source(function(d) { return {"x":d.source.y, "y":d.source.x}; })            
    .target(function(d) { return {"x":d.target.y, "y":d.target.x}; })
    .projection(function(d) { return [d.y, d.x]; }),

  entity_node: null,
  db_node: null,
  mapping_rules: [],

  init: function(){
    
      //init svg element
      this._svg = d3.select("#mappingbox").append("svg:svg")
        .attr("id","svg_mapping_tool")
        .style("position","absolute")
        .style("top", 0)
        .style("left", $("#entity_tree").width()-5);
      this.resize();

      //register events
      $(window).resize($.proxy(this.resize, this));
      $("#entity_tree").on("ready.jstree",$.proxy(this.resize, this));
      $("#db_tables").on("sega.db.table_state_change",$.proxy(this.resize, this));

      $("#entity_tree").on("select_node.jstree open_node.jstree close_node.jstree open_all.jstree close_all.jstree",$.proxy(this.onSelectChange, this));
      $("#db_tables").on("sega.db.selected",$.proxy(this.onSelectChange, this));

      //$("#svg_mapping_tool").on("sega.mapping.svg.link.refresh", $.proxy(this.onSelectChange, this));

      $("#mapping_panel #btn-confirm").click($.proxy(this.doMap, this));



    return this;
  },

  resize: function(){
    this.clear();
    this._svg.attr("width", $("#db_tables").outerWidth(true) - $("#db_tables").outerWidth(false)+9)
        .attr("height", Math.max($("#db_tables").height(), $("#entity_tree").height()));
  },

  drawSuggestion: function() {
    var tree = $("#entity_tree").jstree(true);
    var tree_node_dom = $(tree.get_node(this.entity_node, true));
    var db_row_dom = this.db_node;

    this.clear();
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
    
    function onMouseOver(e){
      d3.select(this).classed("active", true);
    }

    function onMouseOut(e){
      d3.select(this).classed("active", false);
    }

    var isRule = this.hasRule();
    this._suggest_line = this._svg
      .append("g")
      .classed(isRule?"rule":"candidate", true);
    this._suggest_line
      .append("path")
      .data(path_data)
      .attr("d", this._diagonal)
      .attr("stroke", isRule?"#00a885":"#beebff")
      .attr("stroke-width", 4)
      .attr("stroke-dasharray", isRule?"":"12 8")
      .attr("fill", "none")
      .classed("suggest_line", true)
      .on("mouseover", onMouseOver)
      .on("mouseout", onMouseOut);
    
    $("#svg_mapping_tool").trigger("sega.mapping.svg.link.refresh");
    if(isRule){
      tree_node_dom.children("div.jstree-wholerow").children("span.sega-jstree-mapicon").addClass("linked");
      db_row_dom.find("td.isMapped span.sega-jstree-mapicon").addClass("linked");
    }
    return true;
  },

  clear: function(){
    this._svg.select("*").remove();
    this._suggest_line = null;
    $("span.sega-jstree-mapicon").removeClass("linked");
  },
  
  doMap: function(){
    this._doMap(this.entity_node, this.db_node);
  },

  _doMap: function(e, d) {
    var tree = $("#entity_tree").jstree(true);
    var column = d.attr("data-column-name"),
      table = d.parent().parent().attr("data-table-name");
    var rule = {
      entity: {
        text: tree.get_path(e, "/"),
        path: tree.get_path(e, false, true),
        id: e
      },
      db: {
        column: d.attr("data-column-name"),
        table: d.parent().parent().attr("data-table-name")
      }
    };

    // validate rule

    if(!this.canMap(e, d))
      return false;
  
    // clear related rules
    var filtered_rules = [];
    for(var i=0, rules=this.mapping_rules, len=rules.length; i<len; i++){
      if(e === rules[i].entity.id){
        tree.sega_demap_node(rules[i].entity.id);
        demapTableRow(findRow(rules[i].db.table, rules[i].db.column));
      }else if(table == rules[i].db.table && column == rules[i].db.column) {
        tree.sega_demap_node(rules[i].entity.id);
        demapTableRow(findRow(rules[i].db.table, rules[i].db.column));
      }else {
        filtered_rules.push(rules[i]);
      }
    }

    this.mapping_rules = filtered_rules;
    
    // save the rule and update components
    if(tree.get_node(e).type == "artifact"||tree.get_node(e).type == "artifact_n"){
      rule.ref_db = {
        table: d.attr("refFK-table"),
        column: d.attr("refFK-column")
      };
    }
    this.mapping_rules.push(rule);
    this.doMapEntity(e, d);
    mapTableRow(d);
    this.drawSuggestion();

    // handle checkbox event
    var chk1 = tree.get_node(e, true).children(".jstree-wholerow").children("span.sega-jstree-mapicon");
    var chk2 = d.children("td.isMapped");
    var handler = $.proxy(function(evt){
      selectTableRow(this.db_node);
      tree.deselect_all();
      tree.select_node(this.entity_node);
      evt.stopImmediatePropagation();
      return false;
    },{
      db_node: d,
      entity_node: e
    });
    chk1.on("click mousedown focus click.jstree", handler);
    chk2.click(handler);
    
  },

  onSelectChange:  function(){
    this.entity_node = $("#entity_tree").jstree(true).get_selected()[0];
    this.db_node = $("#db_tables>table>tbody>tr.active");
    
    this.clear();

    if(!this.entity_node || this.db_node.length<=0)
      return;

    this.updatePanel();
    this.drawSuggestion();
  },

  updatePanel: function(){
    $("#mapping_panel").show().height(70);
    $("#mapping_panel #entity_path span").text($("#entity_tree").jstree(true).get_path(this.entity_node, "/"));
    $("#mapping_panel #db_path span").text(this.db_node.parent().parent().find("caption").text()+"/"+this.db_node.find("td.name").text());
  },

  hasRule: function() {
    var column = this.db_node.attr("data-column-name"),
      table = this.db_node.parent().parent().attr("data-table-name");
    for(var i=0, rules=this.mapping_rules, len=rules.length; i<len; i++){
      if(this.entity_node === rules[i].entity.id && table == rules[i].db.table && column == rules[i].db.column) {
        return true;
      }
    }
    return false;
  },

  lookUpRuleByEntity: function(entity){
    for(var i=0, rules=this.mapping_rules, len=rules.length; i<len; i++){
      if(entity == rules[i].entity.id ) {
        return rules[i];
      }
    }
    return false;
  },

  canMap: function(e, d) {
    var tree = $("#entity_tree").jstree(true);
    var p = tree.get_parent_artifact(e);
    var entity = tree.get_node(e);
    var column = d.attr("data-column-name"),
      table = d.parent().parent().attr("data-table-name");
    
    if(p) {   //not root?
      if(entity.type == "key"){   //id?
        var pp = tree.get_parent_artifact(p);
        if(pp){   //sub artifact?
          if(!pp.data.isMapped)  //parent artifact must be mapped
            return false;
          if(!d.attr("isPK"))    // must map to primary key
            return false;
          // TODO: check if db has foreign key to its parent

          return false;
        }else {   // main artifact
          if(!d.attr("isPK"))
            return false;
          return true;
        }
      }else if (entity.type == "artifact"){
        if(!d.attr("isFK"))
          return false;
        if(!p.data.isMapped)
          return false;
        if(tree.get_node(p.id).data.mapTo!=table) 
          return false;
        return true;
      }else if (entity.type == "artifact_n"){
        if(!d.attr("isFK"))
          return false;
        if(!p.data.isMapped)
          return false;
        if(tree.get_node(p.id).data.mapTo!=d.attr("refFK-table")) 
          return false;
        return true;
      }else if(entity.type == "attribute"){ //attribute node
        if(!p.data.isMapped)
          return false;
        if(tree.get_node(p.id).data.mapTo!=table) // must map into same table 
          return false;
        return true;
      }
    }else {
      return false; //ignore root
    }
    return false;
  },

  // map entity cascadely
  doMapEntity: function(e, d) {
    tree.sega_map_node(e);
    var entity = tree.get_node(e);
    var column = d.attr("data-column-name"),
      table = d.parent().parent().attr("data-table-name");
    if(entity.type=="key") { //handle main entity
      tree.sega_map_node(entity.parent);
      tree.get_node(entity.parent).data.mapTo = table;
    }else if(entity.type=="artifact"){ //auto map key
      //find key
      var key = tree.get_key_child(entity.id);
      if(key){
        this._doMap(key.id, findRow(d.attr("refFK-table"),d.attr("refFK-column")));
      }
    }else if(entity.type=="artifact_n"){ //auto map key
      //find key
      var key = tree.get_key_child(entity.id);
      if(key){
        this._doMap(key.id, d.siblings("tr[ispk=true]"));
      }
    }

  },

  // demap entity cascadely
  doDemapEntity: function() {
    //TODO : need think through
  },

  loadRules: function(rules) {
    if(!rules)
      return;

    this.mapping_rules = rules.slice();
    for(var i=0, len=rules.length;i<len;i++){
      var tree = $("#entity_tree").jstree(true);
      var e = rules[i].entity.id;
      var entity = tree.get_node(e);
      var d = findRow(rules[i].db.table, rules[i].db.column);
      
      if(e && d) {
        
        tree.sega_map_node(e);
        mapTableRow(d);
        if(entity.type == "key") { //handle main entity
          tree.get_node(entity.parent).data.isMapped = true;
          tree.get_node(entity.parent).data.mapTo = rules[i].db.table;
        }
        var chk1 = tree.get_node(e, true).children(".jstree-wholerow").children("span.sega-jstree-mapicon");
        var chk2 = d.children("td.isMapped");
        var handler = $.proxy(function(evt){
          selectTableRow(this.db_node);
          tree.deselect_all();
          tree.select_node(this.entity_node);
          evt.stopImmediatePropagation();
          return false;
        },{
          db_node: d,
          entity_node: e
        });
        chk1.on("click mousedown focus click.jstree", handler);
        chk2.click(handler);
      }
    }
  }

};

