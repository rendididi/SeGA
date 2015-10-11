//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.11 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.10.05 时间 11:51:32 AM CST 
//


package org.jta.flow;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Task complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="Task"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="Read" type="{http://jta.org/flow}Read" form="qualified"/&gt;
 *         &lt;element name="Write" type="{http://jta.org/flow}Write" form="qualified"/&gt;
 *         &lt;element name="JoinMode" type="{http://jta.org/flow}RouteMode" form="qualified"/&gt;
 *         &lt;element name="SplitMode" type="{http://jta.org/flow}RouteMode" form="qualified"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="ID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="Description" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="PositionX" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="PositionY" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="Width" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="Height" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Task", propOrder = {
    "read",
    "write",
    "joinMode",
    "splitMode"
})
public class Task {

    @XmlElement(name = "Read")
    protected Read read;
    @XmlElement(name = "Write")
    protected Write write;
    @XmlElement(name = "JoinMode")
    @XmlSchemaType(name = "string")
    protected RouteMode joinMode;
    @XmlElement(name = "SplitMode")
    @XmlSchemaType(name = "string")
    protected RouteMode splitMode;
    @XmlAttribute(name = "ID", namespace = "http://jta.org/flow", required = true)
    protected String id;
    @XmlAttribute(name = "Name", namespace = "http://jta.org/flow", required = true)
    protected String name;
    @XmlAttribute(name = "Description", namespace = "http://jta.org/flow", required = true)
    protected String description;
    @XmlAttribute(name = "PositionX", namespace = "http://jta.org/flow", required = true)
    protected int positionX;
    @XmlAttribute(name = "PositionY", namespace = "http://jta.org/flow", required = true)
    protected int positionY;
    @XmlAttribute(name = "Width", namespace = "http://jta.org/flow", required = true)
    protected int width;
    @XmlAttribute(name = "Height", namespace = "http://jta.org/flow", required = true)
    protected int height;

    /**
     * 获取read属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Read }
     *     
     */
    public Read getRead() {
        return read;
    }

    /**
     * 设置read属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Read }
     *     
     */
    public void setRead(Read value) {
        this.read = value;
    }

    /**
     * 获取write属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Write }
     *     
     */
    public Write getWrite() {
        return write;
    }

    /**
     * 设置write属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Write }
     *     
     */
    public void setWrite(Write value) {
        this.write = value;
    }

    /**
     * 获取joinMode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RouteMode }
     *     
     */
    public RouteMode getJoinMode() {
        return joinMode;
    }

    /**
     * 设置joinMode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RouteMode }
     *     
     */
    public void setJoinMode(RouteMode value) {
        this.joinMode = value;
    }

    /**
     * 获取splitMode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RouteMode }
     *     
     */
    public RouteMode getSplitMode() {
        return splitMode;
    }

    /**
     * 设置splitMode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RouteMode }
     *     
     */
    public void setSplitMode(RouteMode value) {
        this.splitMode = value;
    }

    /**
     * 获取id属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * 设置id属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * 获取name属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * 获取description属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置description属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * 获取positionX属性的值。
     * 
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * 设置positionX属性的值。
     * 
     */
    public void setPositionX(int value) {
        this.positionX = value;
    }

    /**
     * 获取positionY属性的值。
     * 
     */
    public int getPositionY() {
        return positionY;
    }

    /**
     * 设置positionY属性的值。
     * 
     */
    public void setPositionY(int value) {
        this.positionY = value;
    }

    /**
     * 获取width属性的值。
     * 
     */
    public int getWidth() {
        return width;
    }

    /**
     * 设置width属性的值。
     * 
     */
    public void setWidth(int value) {
        this.width = value;
    }

    /**
     * 获取height属性的值。
     * 
     */
    public int getHeight() {
        return height;
    }

    /**
     * 设置height属性的值。
     * 
     */
    public void setHeight(int value) {
        this.height = value;
    }

}
