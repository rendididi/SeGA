var graph=new joint.dia.Graph

var cc='';
$('#taskConfigForm').css("display","none");
$('#conConfigForm').css("display","none");

var glpElementView = joint.dia.ElementView.extend({

    initialize: function() {

        joint.dia.ElementView.prototype.initialize.apply(this, arguments);
        this.listenTo(this.model, 'change:attrs', this.resize);
        this.model.on('mouseover', function(el, path) {
            console.log(el);

        }, this);
    }
});


var paper = new joint.dia.Paper({
    el:$('#draw'),
    width:2000,
    height:1000,
    model:graph,
    gridSize:1,
//    elementView: glpElementView,
    defaultLink: new joint.shapes.sega.Link(),
    linkView: joint.shapes.sega.LinkView,
    interactive: {"vertexAdd": false},
    validateConnection: function(cellViewS, magnetS, cellViewT, magnetT, end, linkView) {
            // Prevent loop linking
            
            return (magnetS !== magnetT);
    }
});

paper.on('cell:mouseover', function(evt, x, y) {
    if(evt.model.attributes.type=="devs.Model"){
        V(evt.el).findOne(".inPorts>g>circle").attr("display","block");
    }
});

paper.on('cell:mouseout', function(evt, x, y) {
    if(evt.model.attributes.type=="devs.Model"){
        V(evt.el).findOne(".inPorts>g>circle").attr("display","none");
    }
});


function task(name,x,y){
    var rect = new joint.shapes.devs.Model({
        position: { x: x, y:y },
        size: { width: 60, height: 60 },
        inPorts: [''],
        outPorts: [''],
        attrs: {
            '.label': { text: name, 'ref-x': .4, 'ref-y': .2 },
            rect: { fill: '#fff' },
            '.inPorts .port-body': { fill: '#eeeeee','r':5,magnet: 'passive',type:'input'},
            '.outPorts circle': { fill: '#aaaaaa','r':5 , type:'output'}
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

T = joint.shapes.devs.Model.extend({
	defaults: joint.util.deepSupplement({

		 size: { width: 60, height: 60 },
	        inPorts: [''],
	        outPorts: [''],
	        attrs: {
	            '.label': { text: 'name', 'ref-x': .5, 'ref-y': .5 },
	            rect: { fill: '#fff' },
                '.port-body': {r:5},
	            '.inPorts .port-body': { fill: '#eeeeee', 'r':5,type:'input',display:"none"},
	            '.outPorts circle': { fill: '#aaaaaa','r':5}
	        },
	        data:{
	            name:'naasme',
	            read:[],
	            write:[],
	            description:'',
	            jointmode:'XOR',
	            splitemode:'XOR',
	            isSyncPoint:false
	        }

    }, joint.shapes.devs.Model.prototype.defaults)
});

var rect4 = new T({
    position: { x: 400, y:100 },
    inPorts: [''],
    outPorts: ['']
});
rect4.attributes.attrs['.label'].text="ashdkjsahdksahdkashd";


var rect= new task("提交申请",50,50)
var rect2 = new task("区局公示",200,50);
var rect3 = new task("初审",200,200);

//test

var my_task = new joint.shapes.sega.Task({
    position: {x:500,y:30}
});


var my_start = new joint.shapes.sega.Start({
    position: {x:300,y:30}
});

var my_end = new joint.shapes.sega.End({
    position: {x:700,y:30}
});
//graph.addCells([rect, rect2,rect3,rect4]);
graph.addCells([my_task, my_start, my_end]);

my_task.setText("受理");

var json=graph.toJSON();
paper.on('cell:pointerclick',function(evt,x,y){
    if(cc!=""){
        cc.unhighlight();
    }
    //evt.highlight();
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
        $('#taskdsp').val(evt.model.attr("data").description);
        $('#splitMode').children("option").each(function(){
            if($(this).text()==evt.model.attr("data").splitemode){
                this.selected="selected"
            }
        });
        $('#joinMode').children("option").each(function(){
            if($(this).text()==evt.model.attr("data").jointmode){
                this.selected="selected"
            }
        })

    }
  /*  alert(JSON.stringify(evt.model.toJSON()))*/
})
paper.on('cell:pointerdblclick',function(evt,x,y){
    alert(JSON.stringify(evt.model.toJSON().data));
});
$('#taskName').blur(function(){

    cc.model.setText($(this).val());
    cc.model.attr("data").name=$(this).val();
    //cc.update();
});
paper.on("render:done",function(evt,x,y){
    alert(1);

})
$('#splitMode').blur(function(){
    console.log(cc.model.attr("data/splitemode",$('#splitMode').val()));
});
$('#joinMode').blur(function(){
    $('#joinMode').val();
    console.log(cc.model.attr("data/jointmode",$('#joinMode').val()));
})
$('#taskdsp').blur(function(){
   
})


var taskTool=document.getElementById("taskShape");
taskTool.ondragend=function(ev){
    var x=ev.pageX;
    var y=ev.pageY;
    var newRect= new joint.shapes.sega.Task({
        position : {x: x,y: y}
    });
    graph.addCell(newRect);
}

$("#taskRead").click(function(){
    $('#myModalLabel').html("Read");
    $('#reads').show();
    $('#writes').hide();
})

$("#taskWrite").click(function(){
    $('#myModalLabel').html("write");
    $('#reads').hide();
    $('#writes').show();
})
$("#submit").click(function(){
	json=graph.toJSON();
	var data1={
			"data":JSON.stringify(json)
	}
	test(data1);
})

function test(Id){
    $.ajax({
        type: "post",
        url: "http://localhost:8080/BPaaS/in",
        data:Id,
        success: testOk,
        error: testErr
    });
}
function testOk(data){
    alert(data);
}
 
function testErr(){
      alert("testErr");
}

$(document).keyup(function(e){
	if(e.keyCode==46){
		cc.model.remove();
	}
})


function encode_as_img_and_link(){
 // Add some critical information
 $("svg").attr({ version: '1.1' , xmlns:"http://www.w3.org/2000/svg"});

 var svg = $("#draw").html();
 var b64 = Base64.encode(svg); // or use btoa if supported

 // Works in recent Webkit(Chrome)
 $("body").append($("<img src='data:image/svg+xml;base64,\n"+b64+"' alt='file.svg'/>"));

 // Works in Firefox 3.6 and Webit and possibly any browser which supports the data-uri
 $(".toolbar").append($("<a href-lang='image/svg+xml' href='data:image/svg+xml;base64,\n"+b64+"' title='file.svg'>Download</a>"));
}

/**
*
*  Base64 encode / decode
*  http://www.webtoolkit.info/
*
**/
var Base64 = {
    // private property
    _keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
    // public method for encoding
    encode : function (input) {
        var output = "";
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
        var i = 0;
        input = Base64._utf8_encode(input);
        while (i < input.length) {
            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);
            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;
            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }
            output = output +
            this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) +
            this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);
        }
        return output;
    },
    // public method for decoding
    decode : function (input) {
        var output = "";
        var chr1, chr2, chr3;
        var enc1, enc2, enc3, enc4;
        var i = 0;
        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
        while (i < input.length) {
            enc1 = this._keyStr.indexOf(input.charAt(i++));
            enc2 = this._keyStr.indexOf(input.charAt(i++));
            enc3 = this._keyStr.indexOf(input.charAt(i++));
            enc4 = this._keyStr.indexOf(input.charAt(i++));
            chr1 = (enc1 << 2) | (enc2 >> 4);
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
            chr3 = ((enc3 & 3) << 6) | enc4;
            output = output + String.fromCharCode(chr1);
            if (enc3 != 64) {
                output = output + String.fromCharCode(chr2);
            }
            if (enc4 != 64) {
                output = output + String.fromCharCode(chr3);
            }
        }
        output = Base64._utf8_decode(output);
        return output;
    },
    // private method for UTF-8 encoding
    _utf8_encode : function (string) {
        string = string.replace(/\r\n/g,"\n");
        var utftext = "";
        for (var n = 0; n < string.length; n++) {
            var c = string.charCodeAt(n);
            if (c < 128) {
                utftext += String.fromCharCode(c);
            }
            else if((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            }
            else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }
        }
        return utftext;
    },
    // private method for UTF-8 decoding
    _utf8_decode : function (utftext) {
        var string = "";
        var i = 0;
        var c = c1 = c2 = 0;
        while ( i < utftext.length ) {
            c = utftext.charCodeAt(i);
            if (c < 128) {
                string += String.fromCharCode(c);
                i++;
            }
            else if((c > 191) && (c < 224)) {
                c2 = utftext.charCodeAt(i+1);
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                i += 2;
            }
            else {
                c2 = utftext.charCodeAt(i+1);
                c3 = utftext.charCodeAt(i+2);
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                i += 3;
            }
        }
        return string;
    }
}