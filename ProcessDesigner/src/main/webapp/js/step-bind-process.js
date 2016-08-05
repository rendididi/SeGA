/**
 * Created by Administrator on 2015/8/26.
 * C:\Users\wxfei\AppData\Roaming\Sublime Text 3\Packages\User
 */
var current_selected='';//current selected cell
var paper;

var bindingUtil = {
    // add Read/Write association
    pushRW: function(task, attr, mode){
        var indexTask = this.indexOfTask(task);
        if(indexTask<0){
            var rule = {task:task};
            rule[mode] = [attr];
            binding_json.push(rule);
        }else{
            if(binding_json[indexTask][mode]){
                var indexOptr = this.indexOfOptr(attr, binding_json[indexTask][mode]);
                if(indexOptr<0){
                    binding_json[indexTask][mode].push(attr);
                }else {
                    binding_json[indexTask][mode][indexOptr] = attr;
                }
            }else {
                binding_json[indexTask][mode] = [attr];
            }
        }
    },

    removeRW: function(task, attr, mode){
        var indexTask = this.indexOfTask(task);
        if(indexTask>-1){
            var indexOptr = this.indexOfOptr(attr, binding_json[indexTask][mode]);
            if(indexOptr>-1){
                binding_json[indexTask][mode].splice(indexOptr,1);
            }
        }    
    },

    indexOfTask: function(task) {
        for(var i=0,len=binding_json.length;i<len;i++){
            if(binding_json[i].task == task)
                return i;
        }
        return -1;
    },

    indexOfOptr: function(attr, list) {
        for(var i=0,len=list.length;i<len;i++){
            if(list[i] == attr)
                return i;
        }
        return -1;
    },

    indexOfRW: function(task, attr, mode){
        var list=binding_json;
        var indexTask = this.indexOfTask(task);
        if(indexTask<0)
            return false;

        if(!list[indexTask][mode])
            return false;

        var indexOptr = this.indexOfOptr(attr, list[indexTask][mode]);
        if(indexOptr<0)
            return false;

        return {indexTask:indexTask, indexOptr:indexOptr};

    },

    toggleRW: function(task, attr, mode){

        var index = this.indexOfRW(task, attr, mode);
        if(!index){
            this.pushRW(task, attr, mode);
        }else{
            this.removeRW(task, attr, mode);
        }
    },

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
        	/*去掉连线上的设置的样式，连线不能修改*/
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
        if(evt.model.prop("type")=="sega.Task"||evt.model.prop("type")=="sega.Service"){
            current_selected=evt;
            current_selected.highlight();
            $('#entity_tree').jstree(true).redraw(true);
            loadActionBinding(current_selected);

            if(evt.model.prop("type")=="sega.Task"){
                $("#action-title").text("Human Task");
                $(".web-service").hide();
                $(".human-task").show();
            }else if(evt.model.prop("type")=="sega.Service"){
                $("#action-title").text("Web Service");
                $(".human-task").hide();
                $(".web-service").show();
            }

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
		var json = JSON.stringify(binding_json);
		$("#modal_viewjson .modal-body p").html(json);
		$("#modal_viewjson input#bindingJSON").val(json);
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

var setActionBinding = function(action){
    var indexTask = bindingUtil.indexOfTask(action.model.id);
    var autoGenerate = $(".human-task .check input").prop("checked");
    var serviceUrl = $(".web-service input").val();
    var syncPoint = $(".sync-point .check input").prop("checked");
    var props = {};
    props.syncPoint = syncPoint;

    if(action.model.prop("type")=="sega.Task"){
        props.autoGenerate = autoGenerate;
        props.type = "task";
    }else{
        props.serviceUrl = serviceUrl;
        props.type = "service";
    }


    if(indexTask<0){
        binding_json.push($.extend({task:action.model.id}, props));
    }else{
        $.extend(binding_json[indexTask], props);
    }
};

var loadActionBinding = function(action) {
    var indexTask = bindingUtil.indexOfTask(action.model.id);

    // load defaults
    $(".human-task .check input").prop("checked", true);
    $(".human-task .check").addClass("on");
    $(".web-service input").val("");
    $(".sync-point .check input").prop("checked", true);
    $(".sync-point .check").addClass("on");


    if(indexTask<0) {
        return;
    }
    if(binding_json[indexTask].autoGenerate!=undefined){
        $(".human-task .check input").prop("checked", binding_json[indexTask].autoGenerate);
        if(binding_json[indexTask].autoGenerate)
            $(".human-task .check").addClass("on");
        else
            $(".human-task .check").removeClass("on");
    }
    if(binding_json[indexTask].serviceUrl!=undefined){
        $(".web-service input").val(binding_json[indexTask].serviceUrl);
    }
    if(binding_json[indexTask].syncPoint!=undefined){
        $(".sync-point .check input").prop("checked", binding_json[indexTask].syncPoint);
        if(binding_json[indexTask].syncPoint)
            $(".sync-point .check").addClass("on");
        else
            $(".sync-point .check").removeClass("on");
    }
};

var defaultActionBinding = function() {
       
        for(var i=0,len=process_json.cells.length; i<len; i++){
        var type = process_json.cells[i].type;
        var id= process_json.cells[i].id;
        if(type=="sega.Task"){
            var indexTask = bindingUtil.indexOfTask(id);
            var taskDefaults = {
                task: id,
                autoGenerate: true,
                syncPoint: true,
                type: "task"
            }
            if(indexTask<0){
                binding_json.push(taskDefaults);
            }else{
                $.extend(taskDefaults, binding_json[indexTask]);
                binding_json[indexTask] = taskDefaults;
            }
        }else if(type=="sega.Service"){
            var indexTask = bindingUtil.indexOfTask(id);
            var serviceDefaults = {
                task: id,
                serviceUrl: "",
                syncPoint: true,
                type: "service"
            }
            if(indexTask<0){
                binding_json.push(serviceDefaults);
            }else{
                $.extend(serviceDefaults, binding_json[indexTask]);
                binding_json[indexTask] = serviceDefaults;
            }
        }
    }
};

$(function(){
    $(".human-task .check").click(function(){
        $(this).toggleClass("on");
        var check = $(this).children("input");
        check.prop("checked", !check.prop("checked"));
        setActionBinding(current_selected);
    });

    $(".sync-point .check").click(function(){
        $(this).toggleClass("on");
        var check = $(this).children("input");
        check.prop("checked", !check.prop("checked"));
        setActionBinding(current_selected);
    });

    $(".web-service input").bind("blur focusout change keyup paste", function(){setActionBinding(current_selected);});

    $("#config-modal").bind("hide.bs.modal", function(){setActionBinding(current_selected);});

    defaultActionBinding();
});

