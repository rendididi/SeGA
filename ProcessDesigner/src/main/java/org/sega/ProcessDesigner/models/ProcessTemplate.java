package org.sega.ProcessDesigner.models;

import java.io.Serializable;


public class ProcessTemplate implements Serializable {
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getProcessJSON() {
		return processJSON;
	}
	public void setProcessJSON(String processJSON) {
		this.processJSON = processJSON;
	}
	public String getEntityJSON() {
		return entityJSON;
	}
	public void setEntityJSON(String entityJSON) {
		this.entityJSON = entityJSON;
	}
	public String getDatabaseSQL() {
		return databaseSQL;
	}
	public void setDatabaseSQL(String databaseSQL) {
		this.databaseSQL = databaseSQL;
	}
	public String getEDmappingJSON() {
		return EDmappingJSON;
	}
	public void setEDmappingJSON(String eDmappingJSON) {
		EDmappingJSON = eDmappingJSON;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private long id;
	private String processJSON;
	private String entityJSON;
	private String databaseSQL;
	private String EDmappingJSON;
	private String description;
	private String name;
	
	
}
