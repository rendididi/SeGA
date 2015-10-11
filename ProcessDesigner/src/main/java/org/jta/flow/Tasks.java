//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.11 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.10.05 时间 11:51:32 AM CST 
//


package org.jta.flow;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.VariableElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * <p>Tasks complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="Tasks"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Task" type="{http://jta.org/flow}Task" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Tasks", propOrder = {
    "task"
})
public class Tasks {

    @XmlElement(name = "Task")
    protected List<Task> task;

    /**
     * Gets the value of the task property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the task property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTask().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Task }
     * 
     * 
     */
    public List<Task> getTask() {
        if (task == null) {
            task = new ArrayList<Task>();
        }
        return this.task;
    }
    
    public void setTask(JSONObject processJSON,JSONArray bindJSON){
    	JSONArray cells = processJSON.getJSONArray("cells");
    	for(int i = 0; i<cells.size();i++){
    		JSONObject cell = cells.getJSONObject(i);
    		if(!cell.getString("type").equals("sega.Link")){
    			String id = cell.getString("id");
    			String width = cell.getJSONObject("size").getString("width");
    			String height = cell.getJSONObject("size").getString("height");
    			String positionX = cell.getJSONObject("position").getString("x");
    			String positionY = cell.getJSONObject("position").getString("y");
    			String name = null;
    			String description=null;
    			String joinMode = null;
    			String splitMode = null;
    			Read read = new Read();
    			Write write = new Write();
    			
    			if(!cell.getString("type").equals("sega.Task")){
    				if(cell.getString("type").equals("sega.Start")){
    					name = "start";
    					description = "start node";
    				}
    				if(cell.getString("type").equals("sega.End")){
    					name = "end";
    					description = "end node";
    				}
    				joinMode = "XOR";
        			splitMode = "XOR";
    			}
    			else{
    				JSONObject data = cell.getJSONObject("attrs").getJSONObject("data");
        			name = data.getString("name");
        			description = data.getString("description");
        			joinMode = data.getString("jointmode");
        			splitMode = data.getString("splitemode");
        			
        			
        			for(int j = 0 ;j<bindJSON.size();j++){
        				JSONObject bindTask = bindJSON.getJSONObject(j);
        				if(bindTask.getString("task").equals(id)){
        					
        					
        					if(bindTask.get("read")!=null){
        						
        						List<String> readJson = (List<String>) bindTask.get("read");
        						
        						for(int k = 0 ;k<readJson.size();k++){
            						read.getAttributeID().add(readJson.get(k));
            					}
        					}
        					
        					if(bindTask.get("write")!=null){
        						List<String> writeJson = (List<String>) bindTask.get("write");
            					
            					for(int k = 0 ;k<writeJson.size();k++){
            						write.getAttributeID().add(writeJson.get(k));
            					}
        					}
        				}
        			}
    			}
    			
    			
    			
    			Task tempTask = new Task();
    			tempTask.setName(name);
    			tempTask.setID(id);
    			tempTask.setDescription(description);
    			tempTask.setWidth(Integer.parseInt(width));
    			tempTask.setHeight(Integer.parseInt(height));
    			tempTask.setPositionX(Integer.parseInt(positionX));
    			tempTask.setPositionY(Integer.parseInt(positionY));
    			tempTask.setJoinMode(RouteMode.fromValue(joinMode));
    			tempTask.setSplitMode(RouteMode.fromValue(splitMode));
    			tempTask.setRead(read);
    			tempTask.setWrite(write);
    			
    			this.getTask().add(tempTask);
    			
    		}
    	}
    }
    
}
