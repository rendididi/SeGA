package org.sega.ProcessDesigner.models;

import java.util.Date;

import javax.persistence.Entity;


@Entity
public class Log extends BaseModel {

	private Long id;
	private Users user;
	private String content;
    private String date;
    private String descriptions;
    private String operationType;
    private String className;
    private String type;
    
    public Log(){
    	super();
    }
    
    public Log(Users user,String content,String date,String descriptions,String operationType,String className){
    	super();
    	this.user = user;
    	this.content = content;
    	this.date = date;
    	this.descriptions = descriptions;
    	this.operationType = operationType;
    	this.className = className;
    }
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
