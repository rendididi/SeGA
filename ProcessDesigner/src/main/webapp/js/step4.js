;var step4=(function(){
	// init
	var globleData="";

	var graph = new joint.dia.Graph;
	var cc='';
	$('#task-attrs').hide();
	$('#link-attrs').hide();
	var paper = new joint.dia.Paper({
    	el:$('#draw'),
    	width:2000,
    	height:600,
    	model:graph,
    	gridSize:1,
//    	elementView: glpElementView,
    	defaultLink: new joint.shapes.sega.Link(),
    	linkView: joint.shapes.sega.LinkView,
    	interactive: {"vertexAdd": false},
    	validateConnection: function(cellViewS, magnetS, cellViewT, magnetT, end, linkView) {
            // Prevent loop linking
            
            return (magnetS !== magnetT);
    	}
	});
	
	graph.fromJSON(process_json);
	var taskTool=document.getElementById("taskShape");
	var startTool = document.getElementById("startShape");
	var endTool = document.getElementById("endShape");
	taskTool.ondragend=dragEvent;
	startTool.ondragend=dragEvent;
	endTool.ondragend=dragEvent;

	function dragEvent(ev){
		console.log($("#draw").offset());
		console.log(ev.pageY);
		console.log(ev.pageX);
		var x=ev.pageX-$("#draw").offset().left;
    	var y=ev.pageY-$("#draw").offset().top;
    	var newCell='';
    	var elements = graph.getElements();
    	var startNum=0;
    	var endNum=0;

    	for(var i=0;i<elements.length;i++){
    		if(elements[i].prop("type")=="sega.Start"){
    			startNum++
    		}else if(elements[i].prop("type")=="sega.End"){
    			endNum++;
    		}
    	}

    	switch(ev.target.attributes.id.value){
    		case "taskShape":
    			newCell= new joint.shapes.sega.Task({
        			position : {x: x-75,y: y-30}
   				});
   				break;
   			case "startShape":
   				if(startNum==0){
   					newCell= new joint.shapes.sega.Start({
        				position : {x: x-17,y: y-17}
   					});
   				}else{
   					alert("Start Node is exist");
   				}
    			
   				break;
   			case "endShape":
   				if(endNum==0){
   					newCell= new joint.shapes.sega.End({
        				position : {x: x-17,y: y-17}
   					});
   				}else{
   					alert("End Node is exist");
   				}
   				break;

    	}
    	graph.addCell(newCell);
	}

	$(document).keyup(function(e){
	if(e.keyCode==46&&cc!=''){
		cc.model.remove();
	}
})

	//
	var my_task = new joint.shapes.sega.Task({
    	position: {x:500,y:30}
	});


	var my_start = new joint.shapes.sega.Start({
    	position: {x:300,y:30}
	})
	console.log('draggalbe'+my_start.is_draggable)
	var my_end = new joint.shapes.sega.End({
    	position: {x:700,y:30}
	});
	//graph.addCells([rect, rect2,rect3,rect4]);
	graph.addCells([my_task, my_start, my_end]);

	/*click event*/
	paper.on('cell:pointerclick',function(evt,x,y){
    	if(cc!=""){
        	cc.unhighlight();
    	}
    	evt.highlight();
    	cc=evt;;
    if(evt.model.isLink()){
        $('#link-attrs').show();
        $('#task-attrs').hide();
        evt.model.prop('expression',"expression1");
        $('#linkId').val(evt.model.id);
        $('#sourceId').val(evt.model.attributes.source.id);
        $('#targetId').val(evt.model.attributes.target.id);


    }else if(evt.model.prop("type")=="sega.Task"){

        $('#taskid').val(evt.model.id);
        $('#link-attrs').hide();
        $('#task-attrs').show();
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


	/*set attrs*/

	$('#taskName').blur(function(){

		cc.model.setText($(this).val());
		cc.model.attr("data").name=$(this).val();
		//cc.update();
	});
	$('#splitMode').blur(function(){
		console.log(cc.model.attr("data/splitemode",$('#splitMode').val()));
	});
	$('#joinMode').blur(function(){
		$('#joinMode').val();
		cc.model.attr("data/jointmode",$('#joinMode').val());
	})
	$('#taskdsp').blur(function(){

	})
	$("#expression").blur(function(){
		cc.model.attr("expression",$("#expression").val());
	})




	$("button#btn_viewjson").on("click",function(e){
		$("#modal_viewjson .modal-body p").html(JSON.stringify(graph.toJSON()));
		$("#modal_viewjson").modal();
	});
	function getcc(){
		return cc;
	}
	return {
		cc:getcc
	}
})()