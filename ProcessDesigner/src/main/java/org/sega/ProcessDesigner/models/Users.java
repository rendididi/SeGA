package org.sega.ProcessDesigner.models;

import java.util.Date;

import javax.persistence.Entity;


@Entity
public class Users extends BaseModel {

	private Long id;
	private String email;
    private String password;
    private String role;
    
    public Users(){
    	super();
    }
    public Users(Long id){
    	super();
    	this.id = id;
    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
