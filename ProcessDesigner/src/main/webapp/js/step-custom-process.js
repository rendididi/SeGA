;var step4=(function(){
	// init
	var globleData="";

	var graph = new joint.dia.Graph;
	var current_selected='';//current selected cell
	$('#task-attrs').hide();//task input panel
	$('#link-attrs').hide();//link input panel
	var paper = new joint.dia.Paper({
    	el:$('#draw'),
    	width:"100%",
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
	

	graph.on("change",function(){
		paper.fitToContent({
			minWidth:document.body.clientWidth ,
			minHeight:600,
		})
	})
	
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
	if(e.keyCode==46&&current_selected!=''){
		current_selected.model.remove();
		current='';
		$('#task-attrs').hide();
		$('#link-attrs').hide();
	}
})


	/*click event*/
	paper.on('cell:pointerclick',function(evt,x,y){
    	if(current_selected!=""){
        	current_selected.unhighlight();
    	}
    	evt.highlight();
    	current_selected=evt;;
    if(evt.model.isLink()){
        $('#link-attrs').show();
        $('#task-attrs').hide();
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

	paper.on('blank:pointerclick',function(evt,x,y){
		if(current_selected!=""){
			current_selected.unhighlight();
			current_selected='';
			$('#task-attrs').hide();
			$('#link-attrs').hide();
		}
	})


	/*set attrs*/

	$('#taskName').blur(function(){

		current_selected.model.setText($(this).val());
		current_selected.model.attr("data").name=$(this).val();
		//current_selected.update();
	});
	$('#splitMode').blur(function(){
		console.log(current_selected.model.attr("data/splitemode",$('#splitMode').val()));
	});
	$('#joinMode').blur(function(){
		$('#joinMode').val();
		current_selected.model.attr("data/jointmode",$('#joinMode').val());
	})
	$('#taskdsp').blur(function(){
		current_selected.model.attr("data/description",$('#taskdsp').val());
	})
	$("#expression").blur(function(){
		current_selected.model.prop("expression",$("#expression").val());
	})




	$("button#btn_viewjson,button#btn-navbar-submit").on("click",function(e){
		var json = JSON.stringify(graph.toJSON());
		var svg = paper.exportSvg();
		$("#modal_viewjson .modal-body p").html(json);
		$("#modal_viewjson input#processJSON").val(json);
		$("#modal_viewjson input#input-svg").val(svg);
		$("#modal_viewjson").modal();
	});
	function getcc(){
		return current_selected;
	}
	return {
		current_selected:getcc
	}
})();