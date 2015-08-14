joint.shapes.sega = {};

/*
 *  SeGA JointJs Component: Task
 *  Code by: glp
 *
 */

joint.shapes.sega.Task = joint.shapes.basic.Generic.extend(_.extend({}, joint.shapes.basic.PortsModelInterface, {

    markup: '<g class="rotatable"><g class="scalable"><rect class="body"/></g><text class="label"/><g class="inPorts"/><g class="outPorts"/></g>',
    portMarkup: '<g class="port port<%= id %>"><circle class="port-body"/></g>',

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
                fill: '#f4a915'
            },
            text: {
                'pointer-events': 'none'
            },
            '.label': { 
                text: 'Model', 
                'ref-x': .5, 
                'ref-y': 0.4, 
                ref: '.body', 
                'text-anchor': 'middle', 
                fill: '#ffffff', 
                "font-family": "Open Sans, Microsoft Yahei" ,
                "font-size": "14px",
                "font-weight": "300",
                   
            }
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

        if (selector === '.outPorts') { attrs[portSelector]['ref-dx'] = 0; }

        return attrs;
    },
    setText: function(text) {
        
        var new_width = text.length*10;
        if(new_width<150)
            new_width = 150;
        this.attributes.size = {width:new_width,height:this.attributes.size.height};
        
        var label_attr = _.clone(this.attr(".label"));
        label_attr.text = text;
        
        this.attr(".label", label_attr);

    },
    setPortType: function(type, port) {
        
    }
}));


joint.shapes.sega.TaskView = joint.dia.ElementView.extend(
    _.extend({},joint.shapes.basic.PortsViewInterface,{
        initialize: function() {
            joint.dia.ElementView.prototype.initialize.apply(this, arguments);
            this.listenTo(this.model, 'change:attrs', function(cell) {
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
            '.connection' : { stroke:"#F4A915", 'stroke-width' :  2 },
            '.marker-target': { stroke: '#F4A915', fill: '#F4A915', d: 'M 10 0 L 0 5 L 10 10 z' }
        },
        router: { name: 'manhattan' },
        connector: { name: 'rounded' }
    }
});

joint.shapes.sega.LinkView = joint.dia.LinkView.extend({
    defaults: {
        type: 'sega.LinkView',
        interactive: {"vertexAdd": false}
    },

    //check the restrictions here
    _afterArrowheadMove: function(){
        joint.dia.LinkView.prototype._afterArrowheadMove.apply(this, arguments);
        console.log(this.model.get("target"));
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