//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.11 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.10.05 时间 11:51:32 AM CST 
//


package org.jta.flow;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * <p>Connections complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="Connections"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="Connection" type="{http://jta.org/flow}Connection" maxOccurs="unbounded" minOccurs="0" form="qualified"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Connections", propOrder = {
    "connection"
})
public class Connections {

    @XmlElement(name = "Connection")
    protected List<Connection> connection;

    /**
     * Gets the value of the connection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the connection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConnection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Connection }
     * 
     * 
     */
    public List<Connection> getConnection() {
        if (connection == null) {
            connection = new ArrayList<Connection>();
        }
        return this.connection;
    }
    public void setConnection(JSONObject processJSON){
    	JSONArray cells = processJSON.getJSONArray("cells");
    	
    	for(int i=0;i<cells.size();i++){
    		JSONObject cell = cells.getJSONObject(i);
    		
    		if(cell.getString("type").equals("sega.Link")){
    			String id = cell.getString("id");
    			String expression = cell.getString("expression");
    			String from = cell.getJSONObject("source").getString("id");
    			String to = cell.getJSONObject("target").getString("id");
    			
    			Connection con =new Connection();
    			con.setID(id);
    			con.setExpression(expression);
    			con.setFromNodeID(from);
    			con.setToNodeID(to);
    			
    			this.getConnection().add(con);
    		}
    	}
    }
}
