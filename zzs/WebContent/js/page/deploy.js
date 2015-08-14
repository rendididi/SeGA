/**
 * Created by Administrator on 2015/7/28.
 */

/*entity*/
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


/*process*/
var graph=new joint.dia.Graph
var paper = new joint.dia.Paper({
    el:$('#draw'),
    width:2000,
    height:1000,
    model:graph,
    gridSize:1,
    defaultLink:new joint.dia.Link({
        attrs:{
            '.connection': { stroke: '#666666' },
            '.marker-target': { fill: '#666666', d: 'M 10 0 L 0 5 L 10 10 z' }
        }
    })
})

function task(name,x,y){
    var rect = new joint.shapes.devs.Model({
        position: { x: x, y:y },
        size: { width: 60, height: 60 },
        inPorts: ['',],
        outPorts: [''],
        attrs: {
            '.label': { text: name, 'ref-x': .4, 'ref-y': .2 },
            rect: { fill: '#fff' },
            '.inPorts circle': { fill: '#eeeeee','r':5 },
            '.outPorts circle': { fill: '#aaaaaa','r':5 }
        },
        data:{
            name:name,
            read:[],
            write:[],
            description:'aaa',
            jointmode:'XOR',
            splitemode:'XOR',
            isSyncPoint:false
        }
    });
    return rect;
}
var rect= new task("提交申请",50,50)
var rect2 = new task("区局公示",200,50);
var rect3 = new task("初审",200,200);


graph.addCells([rect, rect2,rect3]);