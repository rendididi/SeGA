/**
 * Created by Administrator on 2015/7/27.
 */

require.config({
    paths:{
        'jquery':'../vendor/jquery',
        'bootstrap':'../vendor/bootstrap',
        'jstree':'../vendor/jstree'

    },
    shim:{
        "bootstrap": {
            deps: ["jquery"],
            exports: "bootstrap"
        },
        'jstree':{
            deps:['jquery'],
            init:function(){
            }
        }
    }
    /*enforceDefine:true*/

});
require(["jquery","jstree","bootstrap"],function($){
/*    $.getJSON('../js/entityData.json',function(data){
        alert(data);
    })*/
    $('#entityTree').jstree({
        "core" : {"themes" : {
            "variant" : "large"
        },
            // so that create works
            "check_callback" : true
        },
        "plugins" : ["contextmenu"]
    });
    $('#entityTree').on("changed.jstree", function (e, data) {
        console.log(data.selected);
    });
    /*$('button').on('click', function () {
        $('#entityTree').jstree(true).select_node('child_node_1');
        $('#entityTree').jstree('select_node', 'child_node_1');
        $.jstree.reference('#entityTree').select_node('child_node_1');
    });*/
    return 0;
})
