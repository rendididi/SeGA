/**
 * ### SeGA plugin for task config
 *
 *
 */
/*globals jQuery, define, exports, require */
(function (factory) {
    "use strict";
    if (typeof define === 'function' && define.amd) {
        define('jstree.sega_ws', ['jquery','jstree'], factory);
    }
    else if(typeof exports === 'object') {
        factory(require('jquery'), require('jstree'));
    }
    else {
        factory(jQuery, jQuery.jstree);
    }
}(function ($, jstree, undefined) {
    "use strict";

    if($.jstree.plugins.sega) { return; }

    /**
     * stores all defaults for the sega_ws plugin
     * @name $.sega_ws
     * @plugin sega_ws
     */
    $.jstree.defaults.sega_ws = {
        /**
         * Indicates if the comparison should be case sensitive. Default is `false`.
         * @name $.jstree.defaults.sega_ws.case_sensitive
         * @plugin sega_ws
         */
        case_sensitive : false,
        /**
         * A callback executed in the instance's scope when a new node is created and the name is already taken, the two arguments are the conflicting name and the counter. The default will produce results like `New node (2)`.
         * @name $.jstree.defaults.sega_ws.duplicate
         * @plugin sega_ws
         */
        duplicate : function (name, counter) {
            return name + ' (' + counter + ')';
        }
    };

    var mapIcon = document.createElement("span");
    mapIcon.className = "sega-jstree-mapicon glyphicon glyphicon-ok";
    var attrHandler = function(evt){
        $(this).toggleClass("active");
        if(!current_selected)
            return;
        var task_id = current_selected.model.id;
        bindingUtil.toggleRW(task_id, evt.data.node.id, evt.data.mode);
    };
    var readIcon = $("<div/>")
        .addClass("sega-checkbox sega-checkbox-read")
        .text("READ")
        .prepend(
            $("<span/>").addClass("glyphicon glyphicon-triangle-top")
        );

    var writeIcon = $("<div/>")
        .addClass("sega-checkbox sega-checkbox-write")
        .text("WRITE")
        .prepend(
            $("<span/>").addClass("glyphicon glyphicon-triangle-bottom")
        );




    $.jstree.plugins.sega_ws = function (options, parent) {
        this.get_sega_json = function (obj, options, flat) {
            obj = this.get_node(obj || $.jstree.root);
            if(!obj) { return false; }

            var tmp = {
                'id' : obj.id,
                'text' : obj.text,
                'type' : obj.type,
                'data' : options && options.no_data ? false : $.extend(true, {}, obj.data)
                //( this.get_node(obj, true).length ? this.get_node(obj, true).data() : obj.data ),
            }, i, j;

            tmp.children = [];


            for(i = 0, j = obj.children.length; i < j; i++) {
                tmp.children.push(this.get_sega_json(obj.children[i], options));
            }

            return (obj.id === $.jstree.root ? tmp.children : tmp);
        };

        this.get_selected_node_sega_path = function () {
            return this.get_path(this.get_selected()).join("/");
        };

        this.redraw_node = function(obj, deep, callback, force_draw) {
            obj = parent.redraw_node.call(this, obj, deep, callback, force_draw);

            if(obj) {
                var i, j, tmp = null, new_read = readIcon.clone(true),new_write=writeIcon.clone(true);
                for(i = 0, j = obj.childNodes.length; i < j; i++) {
                    if(obj.childNodes[i] && obj.childNodes[i].className && obj.childNodes[i].className.indexOf("jstree-wholerow") !== -1) {
                        tmp = obj.childNodes[i];
                        break;
                    }
                }
                if(tmp) {
                    for(i = 0, j = tmp.childNodes.length; i < j; i++) {
                        if(tmp.childNodes[i] && tmp.childNodes[i].className && tmp.childNodes[i].className.indexOf("jstree-checkbox") !== -1) {
                            tmp = tmp.childNodes[i];
                            break;
                        }
                    }
                }

                var node = this.get_node(obj);
                if(tmp && tmp.tagName === "DIV" &&(node.type == "attribute"||node.type == "key"||node.type == "artifact_n")) {
                    $(tmp).append(new_write);
                    $(tmp).append(new_read);
                    new_read.click({node:node, mode:"read"}, attrHandler);
                    new_write.click({node:node, mode:"write"}, attrHandler);
                    
                    if(current_selected) {
                        var task_id = current_selected.model.id;
                        if(bindingUtil.indexOfRW(task_id, node.id, "read")){
                            new_read.addClass("active");
                        }
                        if(bindingUtil.indexOfRW(task_id, node.id, "write")){
                            new_write.addClass("active");
                        }
                    }
                }
            }
            return obj;
        };

        this.sega_map_node = function(obj) {
            obj = this.get_node(obj);
            obj.data.isMapped = true;
            this.redraw_node(obj, false);
        };

        this.sega_demap_node = function(obj) {
            obj = this.get_node(obj);
            obj.data.isMapped = false;
            this.redraw_node(obj, false);
        };

        this.get_parent_artifact = function (obj) {
            obj = this.get_node(obj);
            if(!obj || obj.id === $.jstree.root) {
                return false;
            }
            var tmp = this.get_node(obj.parent);
            return tmp.type==="artifact"||tmp.type==="artifact_n"?tmp:this.get_parent_artifact(tmp);
        };

        this.get_key_child = function (obj) {
            obj = this.get_node(obj);
            var key = false;
            for(var i=0,len=obj.children.length; i<len; i++){
                var chd = tree.get_node(obj.children[i]);
                if(chd.type === "key"){
                    key=chd;
                    break;
                }
            }
            return key;
        };

    };
    
}));
