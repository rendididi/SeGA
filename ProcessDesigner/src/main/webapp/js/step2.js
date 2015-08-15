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
      'data' : [{"id":"j1_1","text":"公租房申请表","type":"artifact","data":{"value_type":"auto"},"children":[{"id":"j1_4","text":"id","type":"key","data":{"value_type":"auto"},"children":[]},{"id":"j1_5","text":"申请人信息","type":"artifact","data":{"value_type":"auto"},"children":[{"id":"j1_6","text":"id","type":"key","data":{"value_type":"auto"},"children":[]},{"id":"j1_7","text":"姓名","type":"attribute","data":{"value_type":"auto"},"children":[]},{"id":"j1_8","text":"身份证号码","type":"attribute","data":{"value_type":"auto"},"children":[]},{"id":"j1_9","text":"民族","type":"attribute","data":{"value_type":"auto"},"children":[]},{"id":"j1_10","text":"婚姻状况","type":"attribute","data":{"value_type":"auto"},"children":[]}]}]}]
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
   var $affix = $(".toolbox"), 
       $parent = $affix.parent(), 
       resize = function() { $affix.width($parent.width()); };
   $(window).resize(resize); 
   resize();


  /* Handle "Create" buttons */
  $(".create button").on("click",function(e){
    var type =this.getAttribute("data-type");
    var sel = tree.get_selected();
    if(canCreate(sel, type)){
      var newNodeText = type==="key"?"id":"New "+type;
      var newNodeId = tree.create_node(sel, {text: newNodeText, type: type, data:{"value_type":"auto"}});
      tree.open_node(sel);
      if(type === "artifact"||type === "artifact_n"){
        tree.create_node(newNodeId, {text:"id",type:"key", data:{"value_type":"auto"}});
        tree.open_node(newNodeId);
      }
    }
  });

  function canCreate(node, type){
    if(!node||!type)
      return false;
    if(!tree.get_rules(node)||!tree.get_rules(node).valid_children)
      return false;
    if(tree.get_rules(node).valid_children.indexOf(type)>-1)
      return true;
    return false;
  }

  /* Handle "View JSON" */
  $("button#btn_viewjson").on("click",function(e){
    $("#modal_viewjson .modal-body p").html(JSON.stringify(tree.get_sega_json()));
    $("#modal_viewjson").modal();
  });

  /* Handle modify node*/
  $("#entity_tree").on("select_node.jstree", function(e, data){
    /* node name*/
    $(".toolbox .node-name").val(data.node.text).focus();

    /* node type*/
    $(".toolbox .node-type").val(data.node.type);
    $(".toolbox .node-type option").each(function(){
      if(canCreate(tree.get_parent(data.node), $(this).attr("value"))){
        $(this).attr("disabled", false);
      }else {
        $(this).attr("disabled", true);
      }
    });
    $(".toolbox .node-type").selectpicker('render');

    /* value type*/
    var val_type = "auto";
    if(data.node.data){
      if(data.node.data.value_type){
        val_type = data.node.data.value_type;
      }
    }
    $(".toolbox .value-type").val(val_type);
    if(data.node.type!=="attribute"){
      $(".toolbox .value-type").attr("disabled","true");
    }else{
      $(".toolbox .value-type").removeAttr("disabled");
    }
    $(".toolbox .value-type").selectpicker('render');
    
  });

  $(".toolbox .node-name").change(saveNodeInfo);
  $(".toolbox .node-type").change(saveNodeInfo);
  $(".toolbox .value-type").change(saveNodeInfo);

  function saveNodeInfo(){
    var sel = tree.get_selected();
    if(!sel)
      return;

    tree.rename_node(sel, $(".toolbox .node-name").val());

    tree.set_type(sel, $(".toolbox .node-type").val());

    if(!tree._model.data[sel].data)
      tree._model.data[sel].data = {};
    $.extend(tree._model.data[sel].data, {value_type: $(".toolbox .value-type").val()});
  }

  /* Handle remove*/
  $(".toolbox .remove-btn").on("click", function(){
    var sel = tree.get_selected();
    if(!sel)
      return;
    tree.delete_node(sel);
  });

});