//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.11 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.10.05 时间 11:51:32 AM CST 
//


package org.jta.flow;

import javax.xml.bind.annotation.XmlRegistry;

import org.sega.ProcessDesigner.util.TimeUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.jta.flow package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.jta.flow
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JTAFlow }
     * 
     */
    public JTAFlow createJTAFlow(String entityJSON,String processJSON,String bindJSON) {
    	JTAFlow jtaFlow = new JTAFlow();
    	Attributes attributes = this.createAttributes(entityJSON);
    	Tasks tasks = this.createTasks(processJSON, bindJSON);
    	Connections cons = this.createConnections(processJSON);
    	Summary summary = this.createSummary(processJSON);
    	
    	jtaFlow.setAttributes(attributes);
    	jtaFlow.setConnections(cons);
    	jtaFlow.setTasks(tasks);
    	jtaFlow.setSummary(summary);
    	jtaFlow.setID("JTA-"+summary.getStartTaskID()+".xml");
    	
        return jtaFlow;
    }

    /**
     * Create an instance of {@link Summary }
     * 
     */
    public Summary createSummary(String processJSON) {
    	String startID=new String();
    	Summary summary = new Summary();
    	summary.setAuthor("user");
    	summary.setCreatedTime(TimeUtil.getTime());
    	summary.setModifiedTime(TimeUtil.getTime());
    	summary.setDescription("");
    	summary.setModifier("user");
    	summary.setVersion("1.0");
    	summary.setName("DEMO");
    	JSONArray cells = JSONObject.fromObject(processJSON).getJSONArray("cells");
    	for(int i=0;i<cells.size();i++){
    		JSONObject cell = cells.getJSONObject(i);
    		if(cell.getString("type").equals("sega.Start")){
    			startID = cell.getString("id");
    		}
    	}
    	summary.setStartTaskID(startID);
    	
        return summary;
    }

    /**
     * Create an instance of {@link Attributes }
     * 
     */
    public Attributes createAttributes(String entityJSON) {
    	Attributes attributes = new Attributes();
    	JSONObject entities = JSONArray.fromObject(entityJSON).getJSONObject(0);
    	attributes.setAttributes(entities);
        return attributes;
    }

    /**
     * Create an instance of {@link Tasks }
     * 
     */
    public Tasks createTasks(String processJSON,String bindJSON) {
    	Tasks tasks = new Tasks();
    	JSONObject process = JSONObject.fromObject(processJSON);
    	JSONArray bind = JSONArray.fromObject(bindJSON);
    	tasks.setTask(process, bind);
        return tasks;
    }

    /**
     * Create an instance of {@link Connections }
     * 
     */
    public Connections createConnections(String processJSON) {
    	Connections cons = new Connections();
    	JSONObject process = JSONObject.fromObject(processJSON);
    	cons.setConnection(process);
        return cons;
    }

    /**
     * Create an instance of {@link Attribute }
     * 
     */
    public Attribute createAttribute() {
        return new Attribute();
    }

    /**
     * Create an instance of {@link Read }
     * 
     */
    public Read createRead() {
        return new Read();
    }

    /**
     * Create an instance of {@link Write }
     * 
     */
    public Write createWrite() {
        return new Write();
    }

    /**
     * Create an instance of {@link Task }
     * 
     */
    public Task createTask() {
        return new Task();
    }

    /**
     * Create an instance of {@link Connection }
     * 
     */
    public Connection createConnection() {
        return new Connection();
    }

}
