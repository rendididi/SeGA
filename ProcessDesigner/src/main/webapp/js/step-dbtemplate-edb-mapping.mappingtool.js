var mapping_tool = {
  _svg: null,
  _suggest_line: null,
  _diagonal: d3.svg.diagonal()
    .source(function(d) { return {"x":d.source.y, "y":d.source.x}; })            
    .target(function(d) { return {"x":d.target.y, "y":d.target.x}; })
    .projection(function(d) { return [d.y, d.x]; }),

  node1: null,
  node2: null,
  mapping_rules: [],
  db1: false,
  db2: false,

  init: function(db1, db2){
    this.db1 = db1;
    this.db2 = db2;
    

      //init svg element
      this._svg = d3.select("#mappingbox").append("svg:svg")
        .attr("id","svg_mapping_tool")
        .style("position","absolute")
        .style("top", 0)
        .style("left", db1.getDom().find(".isMapped").position().left+24);
      this.resize();

      //register events
      $(window).resize($.proxy(this.resize, this));
      db1.getDom().on("sega.db.table_state_change",$.proxy(this.resize, this));
      db2.getDom().on("sega.db.table_state_change",$.proxy(this.resize, this));

      db1.getDom().on("sega.db.selected",$.proxy(this.onSelectChange, this));
      db2.getDom().on("sega.db.selected",$.proxy(this.onSelectChange, this));

      //$("#svg_mapping_tool").on("sega.mapping.svg.link.refresh", $.proxy(this.onSelectChange, this));

      $("#mapping_panel #btn-confirm").click($.proxy(this.doMap, this));


    return this;
  },

  resize: function(){
    this.clear();
    this._svg.attr("width", 
        this.db2.getDom().outerWidth(true) - this.db2.getDom().outerWidth(false) +
        (this.db1.getDom().outerWidth(true)-this.db1.getDom().find(".isMapped").position().left-24) + 3)
        .attr("height", Math.max(this.db2.getDom().height(), this.db1.getDom().height()));
  },

  drawSuggestion: function(node1, node2) {

    this.clear();
    if(node2.length==0 || node1.length==0)
      return;

    var src = {
      x: 0,
      y: node1.position().top+16
    };
    var tgt = {
      x: this._svg.attr("width"),
      y: node2.offset().top - this.db2.getDom().offset().top + node2.height()/2
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

    var isRule = this.hasRule(node1, node2);
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
      node1.find("td.isMapped span.sega-mapicon").addClass("linked");
      node2.find("td.isMapped span.sega-mapicon").addClass("linked");
    }

    return true;
  },

  clear: function(){
    this._svg.select("*").remove();
    this._suggest_line = null;
    this.db1.getDom().find("td.isMapped span.sega-mapicon").removeClass("linked");
    this.db2.getDom().find("td.isMapped span.sega-mapicon").removeClass("linked");

  },
  
  doMap: function(){
    this._doMap(this.node1, this.node2);
  },

  _doMap: function(d1, d2) {
    var column1 = d1.attr("data-column-name"),
      table1 = d1.parent().parent().attr("data-table-name");   
    var column2 = d2.attr("data-column-name"),
      table2 = d2.parent().parent().attr("data-table-name");
    var rule = {
      db1: {
        column: column1,
        table: table1
      },
      db2: {
        column: column2,
        table: table2
      }
    };

    // validate rule

    // clear related rules
    var filtered_rules = [];
    for(var i=0, rules=this.mapping_rules, len=rules.length; i<len; i++){
      if(table1 == rules[i].db1.table && column1 == rules[i].db1.column){
        this.db1.demapTableRow(this.db1.findRow(rules[i].db1.table, rules[i].db1.column));
        this.db2.demapTableRow(this.db2.findRow(rules[i].db2.table, rules[i].db2.column));
      }else if(table2 == rules[i].db2.table && column2 == rules[i].db2.column) {
        this.db1.demapTableRow(this.db1.findRow(rules[i].db1.table, rules[i].db1.column));
        this.db2.demapTableRow(this.db2.findRow(rules[i].db2.table, rules[i].db2.column));
      }else {
        filtered_rules.push(rules[i]);
      }
    }

    this.mapping_rules = filtered_rules;
    
    // save the rule and update components
    this.mapping_rules.push(rule);
    this.db1.mapTableRow(d1);
    this.db2.mapTableRow(d2);
    this.drawSuggestion(d1, d2);

    // handle checkbox event
    var chk1 = d1.children("td.isMapped");
    var chk2 = d2.children("td.isMapped");
    var handler = $.proxy(function(evt){
      this.db1.selectTableRow(d1);
      this.db2.selectTableRow(d2);
      return false;
    },this);
    chk1.click(handler);
    chk2.click(handler);
    
  },

  onSelectChange:  function(){
    this.node1 = this.db1.getDom().find("table>tbody>tr.active");
    this.node2 = this.db2.getDom().find("table>tbody>tr.active");
    
    this.clear();

    if(this.node1.length<=0|| this.node2.length<=0)
      return;

    this.updatePanel();
    this.drawSuggestion(this.node1, this.node2);

  },

  updatePanel: function(){
    $("#mapping_panel").show().height(70);
    $("#mapping_panel #entity_path span").text(this.node1.parent().parent().find("caption").text()+"/"+this.node1.find("td.name").text());
    $("#mapping_panel #db_path span").text(this.node2.parent().parent().find("caption").text()+"/"+this.node2.find("td.name").text());
  },

  hasRule: function(node1, node2) {
    var column1 = node1.attr("data-column-name"),
      table1 = node1.parent().parent().attr("data-table-name");   
    var column2 = node2.attr("data-column-name"),
      table2 = node2.parent().parent().attr("data-table-name");
    for(var i=0, rules=this.mapping_rules, len=rules.length; i<len; i++){
      if(table1 == rules[i].db1.table && column1 == rules[i].db1.column && 
          table2 == rules[i].db2.table && column2 == rules[i].db2.column) {
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

  removeAllRules: function() {
    for(var i=0, rules=this.mapping_rules, len=rules.length; i<len; i++){
      this.db1.demapTableRow(this.db1.findRow(rules[i].db1.table, rules[i].db1.column));
      this.db2.demapTableRow(this.db2.findRow(rules[i].db2.table, rules[i].db2.column));
    }

    this.mapping_rules = [];
  },

  loadRules: function(rules) {
    if(!rules)
      return;

    this.removeAllRules();
    this.mapping_rules = rules;


    for(var i=0, len=rules.length;i<len;i++){
      var r1 = this.db1.findRow(rules[i].db1.table, rules[i].db1.column);
      var r2 = this.db2.findRow(rules[i].db2.table, rules[i].db2.column);
      if(r1 && r2) {
        this._doMap(r1, r2);
      }
    }
  }
};