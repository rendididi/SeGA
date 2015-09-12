joint.shapes.sega = {};

/*
 *  SeGA JointJs Component: Ports
 *  Code by: glp
 *
 */

joint.shapes.sega.PortsModelInterface=$.extend(true,new Object(), joint.shapes.basic.PortsModelInterface,{

});

joint.shapes.sega.PortsViewInterface=$.extend(true,new Object(),joint.shapes.basic.PortsViewInterface,{
     /*update: function() {

        // First render ports so that `attrs` can be applied to those newly created DOM elements
        // in `ElementView.prototype.update()`.
        this.renderPorts();
        joint.dia.ElementView.prototype.update.apply(this, arguments);
    },*/

    renderPorts: function() {
        var $inPorts = this.$('.inPorts').empty();
        var $outPorts = this.$('.outPorts').empty();

        var portTemplate = _.template(this.model.portMarkup);
        var portANDTemplate = _.template(this.model.portANDMarkup);
        var portXORTemplate = _.template(this.model.portXORMarkup);
        var portORTemplate = _.template(this.model.portORMarkup);

        _.each(_.filter(this.model.ports, function(p) { return p.type === 'in';}), function(port, index) {

            switch(port.jointmode){
                case 'XOR':
                    $inPorts.append(V(portXORTemplate({ id: index, port: port })).node);
                    //console.log("jo break;intXOR");

                case 'AND':
                    $inPorts.append(V(portANDTemplate({ id: index, port: port })).node);
                    //console.log('jointAND');
                    break;
                case 'OR':
                    $inPorts.append(V(portORTemplate({ id: index, port: port })).node);
                    //console.log('jointOR');
                    break;
                default:
                    $inPorts.append(V(portTemplate({ id: index, port: port })).node);
            }
            
        });
        _.each(_.filter(this.model.ports, function(p) { return p.type === 'out'; }), function(port, index) {

            switch(port.splitemode){
                case 'XOR':
                    $outPorts.append(V(portXORTemplate({ id: index, port: port })).node);
                    //console.log("spliteXOR");
                    break;
                case 'AND':
                    $outPorts.append(V(portANDTemplate({ id: index, port: port })).node);
                    //console.log('spliteAND');
                    break;
                case 'OR':
                    $outPorts.append(V(portORTemplate({ id: index, port: port })).node);
                    //console.log('spliteOR');
                    break;
                default:
                    $outPorts.append(V(portTemplate({ id: index, port: port })).node);
            }
            
        });

    }
});

/*
 *  SeGA JointJs Component: Task
 *  Code by: glp
 *
 */

joint.shapes.sega.Task = joint.shapes.basic.Generic.extend(_.extend({}, joint.shapes.sega.PortsModelInterface, {

    markup: '<g class="rotatable"><g class="scalable"><rect class="body"/></g><text class="label"/><g class="inPorts"/><g class="outPorts"/></g>',
    portMarkup: '<g class="port port<%= id %>"><circle class="port-body"/></g>',
    portXORMarkup: '<g class="port port<%= id %>"><circle class="port-body"/></g>',
    portANDMarkup: '<g class="port port<%= id %>"><circle class="port-body"/></g>',
    portORMarkup: '<g class="port port<%= id %>"><circle class="port-body"/></g>',
   
    defaults: joint.util.deepSupplement({

        type: 'sega.Task',
        size: { width: 150, height: 60 },
        
        inPorts: ["in"],
        outPorts: ["out"],

        attrs: {
            '.': { magnet: false },
            '.body': {
                width: 150, height: 60,
                fill: '#3C4C53',
                rx: 4, ry: 6
            },
            '.port-body': {
                r: 10,
                magnet: true,
                stroke: '#ffffff',
                'stroke-width': 3,
                fill: '#f4a915',
            },
            text: {
                'pointer-events': 'none'
            },
            '.label': { 
                text: 'Task', 
                'ref-x': .5, 
                'ref-y': 0.4, 
                ref: '.body', 
                'text-anchor': 'middle', 
                fill: '#ffffff', 
                "font-family": "Open Sans, Microsoft Yahei, Microsoft Yahei UI Light" ,
                "font-size": "16px",
                "font-weight": "300",
                   
            },
            data:{
                name:'name',
                read:[],
                write:[],
                description:'',
                jointmode:'XOR',
                splitemode:'XOR',
                isSyncPoint:false
            }
        },
        

    }, joint.shapes.basic.Generic.prototype.defaults),

    getPortAttrs: function(portName, index, total, selector, type) {

        var attrs = {};

        var portClass = 'port' + index;
        var portSelector = selector + '>.' + portClass;
        var portLabelSelector = portSelector + '>.port-label';
        var portBodySelector = portSelector + '>.port-body';

        attrs[portLabelSelector] = { text: portName };
        attrs[portBodySelector] = { port: { id: portName || _.uniqueId(type) , type: type } };
        attrs[portSelector] = { ref: '.body', 'ref-y': (index + 0.5) * (1 / total) };

        if (selector === '.outPorts') { 
            attrs[portSelector]['ref-dx'] = 0; 
            attrs[portBodySelector]["port"]["splitemode"]="XOR";
        }else{
            attrs[portBodySelector]["port"]["jointmode"]="XOR";
        }
        //console.log(attrs);
        return attrs;
    },
    setText: function(text) {
        
        var new_width = text.length*15+30;
        if(new_width<150)
            new_width = 150;
        this.attributes.size = {width:new_width,height:this.attributes.size.height};
        
        var label_attr = _.clone(this.attr(".label"));
        label_attr.text = text;
        
        this.attr(".label", label_attr);

    },
    setPortType: function(model) {
        model.ports.out.splitemode=model.attr("data").splitemode;
        model.ports.in.jointmode=model.attr("data").jointmode;
    }
}));


joint.shapes.sega.TaskView = joint.dia.ElementView.extend(
    _.extend({},joint.shapes.sega.PortsViewInterface,{
        initialize: function() {
            joint.dia.ElementView.prototype.initialize.apply(this, arguments);
            this.listenTo(this.model, 'change:attrs', function(cell) {
                
                this.model.setPortType(this.model);
                            
                this.update();
                this.resize();
            });
        }
    })
);

/*
 *  SeGA JointJs Component: Service
 *  Code by: glp
 *
 */

joint.shapes.sega.Service = joint.shapes.basic.Generic.extend(_.extend({}, joint.shapes.sega.PortsModelInterface, {

    markup: '<g class="rotatable"><g><g class="body">\
    <path class="border" fill="#5D8599" d="M70.402,4.197c15.623,0,28.332,12.707,28.332,28.332c0,0.086-0.01,0.176-0.016,0.262\
    c-0.013,0.209-0.027,0.418-0.031,0.631l-0.106,4.283l4.29,0.016c10.382,0.029,18.828,8.501,18.828,18.883\
    c0,10.354-8.43,18.827-18.789,18.88l-0.916,0.004H25.165C13.604,75.478,4.197,66.066,4.197,54.508\
    c0-8.957,5.701-16.929,14.184-19.84l2.361-0.807l0.416-2.459c1.029-6.07,6.243-10.477,12.397-10.477\
    c1.961,0,3.856,0.455,5.634,1.349l3.826,1.922l1.847-3.861C49.554,10.53,59.58,4.197,70.402,4.197 M70.402,0\
    C57.458,0,46.313,7.579,41.077,18.523c-2.265-1.14-4.814-1.795-7.521-1.795c-8.308,0-15.19,6.046-16.535,13.973\
    C7.126,34.095,0,43.457,0,54.508c0,13.899,11.266,25.167,25.161,25.179h77.771v-0.004c12.694-0.065,22.965-10.37,22.965-23.077\
    c0-12.727-10.295-23.044-23.014-23.081c0.008-0.336,0.049-0.66,0.049-0.996C102.932,14.563,88.368,0,70.402,0L70.402,0z"/>\
<path fill="#25B395" d="M71.185,104.208c0,1.656-1.344,3-3,3h-6.652c-1.657,0-3-1.344-3-3v-6.652c0-1.656,1.343-3,3-3h6.652\
    c1.656,0,3,1.344,3,3V104.208z"/>\
<rect x="62.921" y="84.603" fill="#37404D" width="4.134" height="5.5"/>\
<rect x="78.787" y="99.007" fill="#37404D" width="21.25" height="3.909"/>\
<rect x="29.538" y="99.007" fill="#37404D" width="21.25" height="3.909"/>\
<path class="inner-body" fill="#FFFFFF" d="M70.375,4.197c15.623,0,28.332,12.707,28.332,28.332c0,0.086-0.01,0.176-0.016,0.262\
    C98.68,33,98.664,33.209,98.66,33.423l-0.105,4.283l4.289,0.016c10.383,0.029,18.828,8.501,18.828,18.883\
    c0,10.354-8.43,18.827-18.789,18.88l-0.916,0.004H25.138C13.577,75.478,4.17,66.067,4.17,54.508c0-8.957,5.701-16.929,14.184-19.84\
    l2.361-0.807l0.416-2.459c1.029-6.07,6.243-10.477,12.397-10.477c1.961,0,3.856,0.455,5.634,1.349l3.826,1.922l1.847-3.861\
    C49.527,10.53,59.552,4.197,70.375,4.197"/>\
    </g></g><text class="label"></text><g class="inPorts"/><g class="outPorts"/></g>',
    portMarkup: '<g class="port port<%= id %>"><circle class="port-body"/></g>',
    portXORMarkup: '<g class="port port<%= id %>"><circle class="port-body"/></g>',
    portANDMarkup: '<g class="port port<%= id %>"><circle class="port-body"/></g>',
    portORMarkup: '<g class="port port<%= id %>"><circle class="port-body"/></g>',
   
    defaults: joint.util.deepSupplement({

        type: 'sega.Service',
        size: { width: 126*0.7, height: 107*0.7 },
        
        inPorts: ["in"],
        outPorts: ["out"],

        attrs: {
            '.': { magnet: false },
            '.body': {
            },
            '.port-body': {
                r: 8,
                magnet: true,
                stroke: '#ffffff',
                'stroke-width': 2,
                fill: '#f4a915',
            },
            text: {
                'pointer-events': 'none'
            },
            '.label': { 
                text: 'Service', 
                'ref-x': .5, 
                'ref-y': 0.4, 
                ref: '.body', 
                'text-anchor': 'middle', 
                fill: '#5D8599', 
                "font-family": "Open Sans, Microsoft Yahei, Microsoft Yahei UI Light" ,
                "font-size": "16px",
                "font-weight": "300",
                   
            },
            data:{
                name:'name',
                read:[],
                write:[],
                description:'',
                jointmode:'XOR',
                splitemode:'XOR',
                isSyncPoint:false
            }
        },
        

    }, joint.shapes.basic.Generic.prototype.defaults),

    getPortAttrs: function(portName, index, total, selector, type) {

        var attrs = {};

        var portClass = 'port' + index;
        var portSelector = selector + '>.' + portClass;
        var portLabelSelector = portSelector + '>.port-label';
        var portBodySelector = portSelector + '>.port-body';

        attrs[portLabelSelector] = { text: portName };
        attrs[portBodySelector] = { port: { id: portName || _.uniqueId(type) , type: type } };
        attrs[portSelector] = { ref: '.body', 'ref-x':0.15,'ref-y': (index + 0.93) * (1 / total) };

        if (selector === '.outPorts') { 
            attrs[portSelector]['ref-x'] = 0.88; 
            attrs[portBodySelector]["port"]["splitemode"]="XOR";
        }else{
            attrs[portBodySelector]["port"]["jointmode"]="XOR";
        }
        //console.log(attrs);
        return attrs;
    },
    setText: function(text) {
        
        var new_width = text.length*11+30;
        if(new_width<126*0.7)
            new_width = 126*0.7;
        //this.attributes.size = {width:new_width,height:this.attributes.size.height};
        
        var label_attr = _.clone(this.attr(".label"));
        label_attr.text = text;
        
        this.attr(".label", label_attr);

    },
    setPortType: function(model) {
        model.ports.out.splitemode=model.attr("data").splitemode;
        model.ports.in.jointmode=model.attr("data").jointmode;
    }
}));


joint.shapes.sega.ServiceView = joint.dia.ElementView.extend(
    _.extend({},joint.shapes.sega.PortsViewInterface,{
        initialize: function() {
            joint.dia.ElementView.prototype.initialize.apply(this, arguments);
            this.listenTo(this.model, 'change:attrs', function(cell) {
                
                this.model.setPortType(this.model);
                            
                this.update();
                this.resize();
            });
        }
    })
);

/*
 *  SeGA JointJs Component: Link
 *  Code by: glp
 *
 */

joint.shapes.sega.Link = joint.dia.Link.extend({
    defaults: {
        type: 'sega.Link',
        attrs: { 
            '.connection' : { stroke:"#F4A915", 'stroke-width' :  2,fill:'none' },
            '.marker-target': { stroke: '#F4A915', fill: '#F4A915', d: 'M 10 0 L 0 5 L 10 10 z'}
        },
        expression:"",
        router: { name: 'manhattan' },
        connector: { name: 'rounded' }
    }
});

joint.shapes.sega.LinkView = joint.dia.LinkView.extend({
    defaults: {
        type: 'sega.LinkView',
        interactive: {"vertexAdd": false}
    },
    update: function() {

        if(this.options.interactive==false){
            this.model.attr({
                '.connection-wrap':{display:"none"},
                '.link-tools':{display:'none'},
                '.marker-arrowheads':{display:'none'}
            })
        }else{
            this.model.attr({
                '.connection-wrap':{display:""},
                '.link-tools':{display:''},
                '.marker-arrowheads':{display:''}
            })
        }
        // Update attributes.
        _.each(this.model.get('attrs'), function(attrs, selector) {

            var processedAttributes = [];

            // If the `fill` or `stroke` attribute is an object, it is in the special JointJS gradient format and so
            // it becomes a special attribute and is treated separately.
            if (_.isObject(attrs.fill)) {

                this.applyGradient(selector, 'fill', attrs.fill);
                processedAttributes.push('fill');
            }

            if (_.isObject(attrs.stroke)) {

                this.applyGradient(selector, 'stroke', attrs.stroke);
                processedAttributes.push('stroke');
            }

            // If the `filter` attribute is an object, it is in the special JointJS filter format and so
            // it becomes a special attribute and is treated separately.
            if (_.isObject(attrs.filter)) {

                this.applyFilter(selector, attrs.filter);
                processedAttributes.push('filter');
            }

            // remove processed special attributes from attrs
            if (processedAttributes.length > 0) {

                processedAttributes.unshift(attrs);
                attrs = _.omit.apply(_, processedAttributes);
            }

            this.findBySelector(selector).attr(attrs);

        }, this);

        // Path finding
        var vertices = this.route = this.findRoute(this.model.get('vertices') || []);

        // finds all the connection points taking new vertices into account
        this._findConnectionPoints(vertices);

        var pathData = this.getPathData(vertices);

        // The markup needs to contain a `.connection`
        this._V.connection.attr('d', pathData);
        this._V.connectionWrap && this._V.connectionWrap.attr('d', pathData);

        this._translateAndAutoOrientArrows(this._V.markerSource, this._V.markerTarget);

        //partials updates
        this.updateLabelPositions();
        this.updateToolsPosition();
        this.updateArrowheadMarkers();

        delete this.options.perpendicular;
        // Mark that postponed update has been already executed.
        this.updatePostponed = false;

        return this;
    },

    //check the restrictions here
    _afterArrowheadMove: function(){
        joint.dia.LinkView.prototype._afterArrowheadMove.apply(this, arguments);
        //console.log(this.model.get("target"));
        if(this.model.get("target").port === undefined){
            this.model.remove();
        }else if(this.model.get("target").port === "out"){
            this.model.remove();
        }else if(this.model.get("source").port != "out"){
            this.model.remove();
        }
    }
});
/*
 *  SeGA JointJs Component: Start
 *  Code by: glp
 *
 */

joint.shapes.sega.Start = joint.shapes.basic.Generic.extend(_.extend({}, joint.shapes.basic.PortsModelInterface, {

    markup: '<g class="body"><circle class="outter"/><circle class="inner"/><g class="outPorts"/></g>',
    portMarkup: '<g class="port port<%= id %>"><circle class="port-body"/></g>',

    defaults: joint.util.deepSupplement({

        type: 'sega.Start',
        size: { width: 20, height: 20 },
        
        inPorts: [],
        outPorts: ["out"],

        attrs: {
            '.': { magnet: false },
            '.outter': {
                r: 15,
                stroke: '#F4A915',
                "stroke-width": 2,
                "fill": "#ffffff",
                transform: 'translate(10, 10)'
            },
            '.inner': {
                r: 10,
                fill: '#F4A915',
                transform: 'translate(10, 10)'
            },
            '.port-body': {
                r: 10,
                magnet: true,
                transform: 'translate(0, 0)',
                "fill-opacity": 0
            }
        }

    }, joint.shapes.basic.Generic.prototype.defaults),

    getPortAttrs: function(portName, index, total, selector, type) {

        var attrs = {};

        var portClass = 'port' + index;
        var portSelector = selector + '>.' + portClass;
        var portLabelSelector = portSelector + '>.port-label';
        var portBodySelector = portSelector + '>.port-body';

        attrs[portLabelSelector] = { text: portName };
        attrs[portBodySelector] = { port: { id: portName || _.uniqueId(type) , type: type } };
        attrs[portSelector] = { transform:"translate(10, 10)"};


        return attrs;
    }

}));

joint.shapes.sega.StartView = joint.dia.ElementView.extend(
    _.extend({},joint.shapes.basic.PortsViewInterface,{
        initialize: function() {
            joint.dia.ElementView.prototype.initialize.apply(this, arguments);

        }
    })
);

/*
 *  SeGA JointJs Component: End
 *  Code by: glp
 *
 */

joint.shapes.sega.End = joint.shapes.basic.Generic.extend(_.extend({}, joint.shapes.basic.PortsModelInterface, {

    markup: '<g class="body"><circle class="outter"/><g class="inPorts"/></g>',
    portMarkup: '<g class="port port<%= id %>"><circle class="port-body"/></g>',

    defaults: joint.util.deepSupplement({

        type: 'sega.End',
        size: { width: 20, height: 20 },
        
        inPorts: ["in"],
        outPorts: [],

        attrs: {
            '.': { magnet: false },
            '.outter': {
                r: 15,
                stroke: '#F4A915',
                "stroke-width": 4,
                "fill": "#ffffff",
                transform: 'translate(10, 10)'
            },
            '.port-body': {
                r: 10,
                magnet: true,
                transform: 'translate(0, 0)',
                "fill-opacity": 0
            }
        }

    }, joint.shapes.basic.Generic.prototype.defaults),

    getPortAttrs: function(portName, index, total, selector, type) {

        var attrs = {};

        var portClass = 'port' + index;
        var portSelector = selector + '>.' + portClass;
        var portLabelSelector = portSelector + '>.port-label';
        var portBodySelector = portSelector + '>.port-body';

        attrs[portLabelSelector] = { text: portName };
        attrs[portBodySelector] = { port: { id: portName || _.uniqueId(type) , type: type } };
        attrs[portSelector] = { transform:"translate(10, 10)"};


        return attrs;
    }

}));

joint.shapes.sega.EndView = joint.dia.ElementView.extend(
    _.extend({},joint.shapes.basic.PortsViewInterface,{
        initialize: function() {
            joint.dia.ElementView.prototype.initialize.apply(this, arguments);

        }
    })
);

//扩展paper方法，去除不必要的interactive样式，用于生成svg图形， by zzs
joint.dia.Paper.prototype.exportSvg=function(){
	var newSvg = ($(this.svg).clone(true))[0];
	var svgParent = document.createElement("div");
	svgParent.appendChild(newSvg);
	$(svgParent).find("*").remove(".connection-wrap, .link-tools,.marker-arrowheads");
	return svgParent.innerHTML;
}