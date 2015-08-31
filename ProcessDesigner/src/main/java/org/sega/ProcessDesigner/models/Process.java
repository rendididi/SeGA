/**
 * 
 */
package org.sega.ProcessDesigner.models;

import java.io.Serializable;

/**
 * @author glp
 *
 */
public class Process implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3211178272317488371L;

	private long id;
	private String processJSON;
	private String processXML;
	private String entityJSON;
	private String databaseJSON;
	private String DDmappingJSON;
	private String EDmappingJSON;
	private String processImageUrl;
	private String description;
	private String name;
	private ProcessTemplate template;
	private DatabaseConfiguration dbconfig;
	
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
	public String getProcessXML() {
		return processXML;
	}
	public void setProcessXML(String processXML) {
		this.processXML = processXML;
	}
	public String getEntityJSON() {
		return entityJSON;
	}
	public void setEntityJSON(String entityJSON) {
		this.entityJSON = entityJSON;
	}
	public String getDatabaseJSON() {
		return databaseJSON;
	}
	public void setDatabaseJSON(String databaseJSON) {
		this.databaseJSON = databaseJSON;
	}
	public String getEDmappingJSON() {
		return EDmappingJSON;
	}
	public void setEDmappingJSON(String eDmappingJSON) {
		EDmappingJSON = eDmappingJSON;
	}
	public String getProcessImageUrl() {
		return processImageUrl;
	}
	public void setProcessImageUrl(String processImageUrl) {
		this.processImageUrl = processImageUrl;
	}
	public ProcessTemplate getTemplate() {
		return template;
	}
	public void setTemplate(ProcessTemplate template) {
		this.template = template;
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
	public String getDDmappingJSON() {
		return DDmappingJSON;
	}
	public void setDDmappingJSON(String dDmappingJSON) {
		DDmappingJSON = dDmappingJSON;
	}
	public DatabaseConfiguration getDbconfig() {
		return dbconfig;
	}
	public void setDbconfig(DatabaseConfiguration dbconfig) {
		this.dbconfig = dbconfig;
	}
}
