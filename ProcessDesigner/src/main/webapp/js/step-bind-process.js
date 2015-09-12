/**
 * Created by Administrator on 2015/8/26.
 */
var current_selected='';//current selected cell
var paper;

var bindingUtil = {
    pushRW: function(binding, list){
        if(!binding)
            return;
        if(!binding.task||!binding.entity_attr)
            return;

        if(!this.indexOfRW(binding, list)>-1){
            list.push(binding);
        }    
    },

    removeRW: function(binding, list){
        if(!binding)
            return;
        if(!binding.task||!binding.entity_attr)
            return;

        var index = this.indexOfRW(binding, list);
        if(index>-1){
            list.splice(index,1);
        }    
    },

    indexOfRW: function(binding, list){
        var r = -1;
        for(var i=0,len=list.length;i<len;i++){
            if(list[i].task === binding.task && list[i].entity_attr === binding.entity_attr)
                return i;
        }
        return r;
    },

    toggleRW: function(binding, list){
        if(!binding)
            return;
        if(!binding.task||!binding.entity_attr)
            return;

        var index = this.indexOfRW(binding, list);
        if(index>-1){
            list.splice(index, 1);
        }else if(index==-1){
            list.push(binding);
        }
    }
};

(function(){
    var configApp = angular.module('processConfig',[]);
    configApp.controller("serviceController",function($scope){

    })
    var graph = new joint.dia.Graph;
    
    paper = new joint.dia.Paper({
        el:$('#draw'),
        width:"100%",
        height:600,
        model:graph,
        gridSize:1,
//    	elementView: glpElementView,
        defaultLink: new joint.shapes.sega.Link({
            attrs:{
                '.connection-wrap':{display:"none"},
                '.link-tools':{display:'none'},
            }
        }),
        linkView: joint.shapes.sega.LinkView,
        interactive: false,
        validateConnection: function(cellViewS, magnetS, cellViewT, magnetT, end, linkView) {
            return false;
        },
        validateMagnet: function(cellView, magnet) {
            return false;
        }
    })
    .on("cell:pointerdown",function(evt,x,y){
        if(current_selected){
            current_selected.unhighlight();

        }
        if(evt.model.prop("type")=="sega.Task"){
            current_selected=evt;
            current_selected.highlight();
            $('#entity_tree').jstree(true).redraw(true);
            $('#config-modal').modal();
            $('#config-modal #myModalLabel').html(evt.model.attr(".label/text"))
        }
    })
    .on('blank:pointerdown',function(evt,x,y){
        if(current_selected){
            current_selected.unhighlight();
            current_selected='';
        }
    });
        

    graph.fromJSON(process_json);

    var resize = function(){
        paper.setDimensions(
          Math.max(document.body.clientWidth, paper.getContentBBox().x+paper.getContentBBox().width+600), 
          Math.max(document.body.clientHeight - $("#draw").offset().top,paper.getContentBBox().y+paper.getContentBBox().height+450)
        );
        $(".paper-container").height(document.body.clientHeight - $(".paper-container").offset().top);
        $(".modal-bind .modal-body").height(document.body.clientHeight*0.85);
        $(".modal-bind .modal-body .tab-pane").height(document.body.clientHeight*0.85-60);
    };

    $(function(){
        paper.setOrigin($(".step-title").offset().left, 0);
    $(window).resize(resize);
        resize();
    });

    graph.on("change", resize);

    $("button#btn_viewjson,button#btn-navbar-submit").on("click",function(e){
		var json = JSON.stringify(graph.toJSON());
		var svg = paper.svg;
		$("#modal_viewjson .modal-body p").html(json);
		$("#modal_viewjson input#processJSON").val(json);
		$("#modal_viewjson input#svg").val(svg);
		$("#modal_viewjson").modal();
	});


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
        "plugins" : [  "dnd",  "types", "wholerow", "sega" ]

    });

    tree = $("#entity_tree").jstree(true);

    $("#entity_tree").on("ready.jstree" ,function(){
        tree.open_all();
    });

})();


