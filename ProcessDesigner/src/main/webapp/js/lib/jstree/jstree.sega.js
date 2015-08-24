/**
 * ### SeGA plugin
 *
 * Rewrite get_json
 */
/*globals jQuery, define, exports, require */
(function (factory) {
	"use strict";
	if (typeof define === 'function' && define.amd) {
		define('jstree.sega', ['jquery','jstree'], factory);
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
	 * stores all defaults for the sega plugin
	 * @name $.jstree.defaults.sega
	 * @plugin sega
	 */
	$.jstree.defaults.sega = {
		/**
		 * Indicates if the comparison should be case sensitive. Default is `false`.
		 * @name $.jstree.defaults.sega.case_sensitive
		 * @plugin sega
		 */
		case_sensitive : false,
		/**
		 * A callback executed in the instance's scope when a new node is created and the name is already taken, the two arguments are the conflicting name and the counter. The default will produce results like `New node (2)`.
		 * @name $.jstree.defaults.sega.duplicate
		 * @plugin sega
		 */
		duplicate : function (name, counter) {
			return name + ' (' + counter + ')';
		}
	};

	var mapIcon = document.createElement("span");
	mapIcon.className = "sega.jstree.mapicon";

	$.jstree.plugins.sega = function (options, parent) {
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
				var i, j, tmp = null, chk = mapIcon.cloneNode(true);
				for(i = 0, j = obj.childNodes.length; i < j; i++) {
					if(obj.childNodes[i] && obj.childNodes[i].className && obj.childNodes[i].className.indexOf("jstree-anchor") !== -1) {
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
				console.log(tmp);
				if(tmp && tmp.tagName === "I") {
					tmp.style.backgroundColor = "transparent";
					tmp.style.backgroundImage = "none";
					tmp.appendChild(chk);
					console.log(chk);
				}
			}
			return obj;
		};

	};//End of $.jstree.plugins.sega
	


	// $.jstree.defaults.plugins.push("sega");
}));
