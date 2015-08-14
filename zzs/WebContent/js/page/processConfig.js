/**
 * Created by Administrator on 2015/7/27.
 */
var graph=new joint.dia.Graph

var link = new joint.dia.Link({
	 attrs:{
            '.connection': { stroke: '#666666' },
            '.marker-target': { fill: '#666666', d: 'M 10 0 L 0 5 L 10 10 z' },
            '.tool-remove':{}
        }
});

var cc='';
$('#taskConfigForm').css("display","none");
$('#conConfigForm').css("display","none");
var paper = new joint.dia.Paper({
    el:$('#draw'),
    width:2000,
    height:1000,
    model:graph,
    gridSize:1,
    defaultLink:new joint.dia.Link({
        attrs:{
            '.connection': { stroke: '#666666' },
            '.marker-target': { fill: '#666666', d: 'M 10 0 L 0 5 L 10 10 z' },
            '.tool-remove':{}
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
var json=graph.toJSON();
paper.on('cell:pointerclick',function(evt,x,y){
    if(cc!=""){
        cc.unhighlight();
    }
    evt.highlight();
    cc=evt;;
    if(evt.model.isLink()){
        $('#conConfigForm').show();
        $('#taskConfigForm').hide();
        evt.model.prop('expression',"expression1");
        $('#linkId').val(evt.model.id);
        $('#sourceId').val(evt.model.attributes.source.id);
        $('#targetId').val(evt.model.attributes.target.id);


    }else{

        $('#taskid').val(evt.model.id);
        $('#conConfigForm').hide();
        $('#taskConfigForm').show();
        $('#taskName').val(evt.model.attributes.attrs['.label'].text);
        /*        $('#splitMode').children("option").each(function(){
         if($(this).text()=='XOR'){
         this.selected="selected"
         }
         });*/
        $('#taskdsp').val(evt.model.prop("data").description);
        $('#splitMode').children("option").each(function(){
            if($(this).text()==evt.model.prop("data").splitemode){
                this.selected="selected"
            }
        });
        $('#joinMode').children("option").each(function(){
            if($(this).text()==evt.model.prop("data").jointmode){
                this.selected="selected"
            }
        })

    }
    /*  alert(JSON.stringify(evt.model.toJSON()))*/
})
paper.on('cell:pointerdblclick',function(evt,x,y){
    if(!evt.model.isLink()){
        $('#modal1').modal('show');
    }

});

