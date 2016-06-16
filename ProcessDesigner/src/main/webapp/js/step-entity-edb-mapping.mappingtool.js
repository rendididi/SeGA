var mapping_tool = {
  _svg: null,
  _layer_line: null,
  _layer_icon: null,
  _suggest_line: null,
  _center_x: 0,
  _center_y: 0,
  _diagonal: d3.svg.diagonal()
    .source(function(d) { return {"x":d.source.y, "y":d.source.x}; })            
    .target(function(d) { return {"x":d.target.y, "y":d.target.x}; })
    .projection(function(d) { return [d.y, d.x]; }),

  entity_node: null,
  entity_nodes: null,
  db_node: null,
  mapping_rules: [],
  _link_icon: null,

  init: function(){
    
      //init svg element
      this._svg = d3.select("#mappingbox").append("svg:svg")
        .attr("id","svg_mapping_tool")
        .style("position","absolute")
        .style("top", 0)
        .style("left", Math.floor($("#entity_tree").width()-5)+"px");
	  this._layer_line = this._svg.append("svg:g").attr("id","layer_line");
	  this._layer_icon = this._svg.append("svg:g").attr("id","layer_icon");
      
      this.resize();
      this.drawLinkIcon();

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
    this._center_x = this._svg.attr("width")/2;
    this._center_y = this._svg.attr("height")/2;
  },

  clear: function(){
	    this._layer_line.select("*").remove();
	    this._suggest_line = null;
	    $("span.sega-jstree-mapicon").removeClass("linked");
  },
  
  drawLinkIcon: function() {
    var x,y,width=92,height=81;
    x = this._svg.attr("width")/2 - width/2;
    y = this._svg.attr("height")/2 - height/2;

    this._layer_icon
      .attr("transform","translate("+x+","+y+")");
	  this._layer_icon
      .append("svg:path")
      .attr("fill","#FFFFFF")
      .attr("d", "M25.999,78.282c-1.072,0-2.063-0.572-2.598-1.5l-20-34.641c-0.536-0.928-0.536-2.072,0-3l20-34.642 C23.937,3.572,24.927,3,25.999,3h40c1.072,0,2.063,0.572,2.598,1.5l20,34.642c0.536,0.928,0.536,2.072,0,3l-20,34.641 c-0.535,0.928-1.525,1.5-2.598,1.5H25.999z");
    this._layer_icon
      .append("svg:path")
      .attr("fill","#beebff")
      .attr("d", "M65.999,6l20,34.642l-20,34.641h-40l-20-34.641L25.999,6H65.999 M65.999,0h-40 c-2.144,0-4.124,1.144-5.196,3l-20,34.642c-1.071,1.856-1.071,4.144,0,6l20,34.641c1.072,1.856,3.053,3,5.196,3h40 c2.144,0,4.124-1.144,5.196-3l20-34.641c1.071-1.856,1.071-4.144,0-6L71.195,3C70.123,1.144,68.143,0,65.999,0L65.999,0z");
    this._layer_icon
      .append("svg:path")
      .attr("fill","#beebff")
      .attr("d", "M67.214,55.586c-17.061,0-21.391-7.356-24.869-13.268c-3.092-5.252-5.533-9.4-18.216-9.4    c-2.209,0-4-1.791-4-4s1.791-4,4-4c17.257,0,21.611,7.398,25.11,13.343c3.066,5.21,5.488,9.326,17.975,9.326c2.209,0,4,1.791,4,4    S69.423,55.586,67.214,55.586z");
    this._layer_icon
      .append("svg:path")
      .attr("fill","#FFFFFF")
      .attr("d", "M24.129,60.365c-4.418,0-8-3.582-8-8s3.582-8,8-8c10.396,0,11.856-2.483,14.769-7.429    c4.726-8.031,10.448-15.239,28.316-15.239c4.418,0,8,3.582,8,8s-3.582,8-8,8c-10.199,0-11.646,2.458-14.527,7.355    C47.937,53.121,42.179,60.365,24.129,60.365z");
    this._layer_icon
      .append("svg:path")
      .attr("fill","#beebff")
      .attr("d", "M24.129,56.365c-2.209,0-4-1.791-4-4s1.791-4,4-4c12.683,0,15.124-4.148,18.216-9.4    c3.479-5.911,7.809-13.268,24.869-13.268c2.209,0,4,1.791,4,4s-1.791,4-4,4c-12.486,0-14.908,4.115-17.975,9.326    C45.74,48.967,41.386,56.365,24.129,56.365z");

  },

  drawLine: function(source, type){

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
    this._suggest_line = this._layer_line
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

  doMap: function(){
    //For Demo
    if(this.entity_nodes.length>1){
        this._doMap2(this.entity_nodes, this.db_node);
      
    }else{
      this._doMap(this.entity_node, this.db_node);  
    }
    
  },

  _doMap: function(e, d, opt) {
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

    if(opt&&!opt.nocheck){
    	if(!this.canMap(e, d))
    		return false;
  	}
  
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
    if(tree.get_node(e).type=="attribute")
      tree.get_node(e).data.mapped_type = d.children().last().text();
    
    // handle checkbox event

    this.updateCheckboxHandler(e,d);
  },
  
  _doMap2: function(ee, d, opt) {
    var tree = $("#entity_tree").jstree(true);
    var column = d.attr("data-column-name"),
      table = d.parent().parent().attr("data-table-name");
    

    // validate rule

    if(opt&&!opt.nocheck){
        if(!this.canMap(e, d))
            return false;
    }
  
    

    
    // save the rule and update components
    
    
    mapTableRow(d);
    
    for(var i=0;i<ee.length;i++){
      this.drawSuggestion2(ee[i],d);
    }
    
  },
  
  updateCheckboxHandler: function(e, d){
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
    this.entity_nodes = $("#entity_tree").jstree(true).get_selected();
    
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
    if(entity.type=="key"&&entity.parent&&entity.parent.parent==$.jstree.root) { //handle main entity
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
    	entity.data.mapTo = table;
        this._doMap(key.id, d.siblings("tr[ispk=true]"), {nocheck: true});
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

    var valid_rules = [];
    for(var i=0, len=rules.length;i<len;i++){
      var tree = $("#entity_tree").jstree(true);
      var e = rules[i].entity.id;
      var entity = tree.get_node(e);
      var d = findRow(rules[i].db.table, rules[i].db.column);

      if(e && d && entity) {
        valid_rules.push(rules[i]);
        tree.sega_map_node(e);
        mapTableRow(d);
        if(entity.type == "key") { //handle main entity
          tree.get_node(entity.parent).data.isMapped = true;
          tree.get_node(entity.parent).data.mapTo = rules[i].db.table;
        }

        if(entity.type == "key" || entity.type == "attribute"){
          entity.data.mapped_type = d.children().last().text();
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

    this.mapping_rules = valid_rules;
  }

};

