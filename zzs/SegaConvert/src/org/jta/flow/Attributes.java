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
 * <p>Attributes complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="Attributes"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Attribute" type="{http://jta.org/flow}Attribute" maxOccurs="unbounded" minOccurs="0" form="qualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Attributes", propOrder = {
    "attribute"
})
public class Attributes {

    @XmlElement(name = "Attribute")
    protected List<Attribute> attribute;

    /**
     * Gets the value of the attribute property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attribute property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttribute().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Attribute }
     * 
     * 
     */
    public List<Attribute> getAttribute() {
        if (attribute == null) {
            attribute = new ArrayList<Attribute>();
        }
        return this.attribute;
    }
    
    public void setAttributes(JSONObject entityJSON){
    	
    	String id = entityJSON.getString("id");
    	String name = entityJSON.getString("text");
    	JSONObject data = entityJSON.getJSONObject("data");
    	String type = data.getString("value_type");
    	JSONArray children = entityJSON.getJSONArray("children");
    	
    	if(type.equals("number")){
    		type = "int";
    	}
    	else{
    		type = "String";
    	}
    	
    	Attribute entity = new Attribute();
    	entity.setID(id);
    	entity.setName(name);
    	entity.setType(type);
    	entity.setDefaultValue("");
    	entity.setDescription("");
    	
    	this.getAttribute().add(entity);
    	
    	if(children.size()>0){
    		for(int i = 0;i<children.size();i++){
    			this.setAttributes(children.getJSONObject(i));
    		}
    	}
    }

}
