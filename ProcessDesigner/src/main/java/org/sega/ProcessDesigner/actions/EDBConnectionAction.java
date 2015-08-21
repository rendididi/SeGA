package org.sega.ProcessDesigner.actions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.struts2.json.JSONUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class EDBConnectionAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2169348924554203286L;
	
	private String hostName;
	private String portName;
	private String userName;
	private String password;
	private String dbName;
	private String edbType;
	private boolean testResult;
	private boolean saveResult;
	private boolean loadSchemaResult;
	private String tables_json;
	private String columns_json;
	private String keys_json;
	
	
	
	private static String driver = "com.mysql.jdbc.Driver";
	
	public String test() {
		testResult=false;
		
		if("mysql".equalsIgnoreCase(edbType)){
			testResult = testMysql();
		}

		return SUCCESS;
	}
	
	public String save() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("edb.hostName", hostName);
		session.put("edb.portName", portName);
		session.put("edb.userName", userName);
		session.put("edb.password", password);
		session.put("edb.dbName", dbName);
		session.put("edb.edbType", edbType);
		setSaveResult(true);
		return SUCCESS;
	}
	
	public String load() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		hostName = (String) session.get("edb.hostName");
		portName = (String) session.get("edb.portName");
		userName = (String) session.get("edb.userName");
		password = (String) session.get("edb.password");
		dbName = (String) session.get("edb.dbName");
		edbType = (String) session.get("edb.edbType");

		return SUCCESS;
	}
	
	public String loadSchema() {
		save();
		
		loadSchemaResult = false;
		Connection conn = null;
		String url = "jdbc:mysql://"+hostName+":"+portName+"/"+dbName;
		String sql_tables = String
				.format("select TABLE_SCHEMA, TABLE_NAME, TABLE_COMMENT from information_schema.tables where TABLE_SCHEMA=\"%s\";",
						dbName);
		String sql_columns = String
				.format("select TABLE_NAME, COLUMN_NAME, DATA_TYPE, COLUMN_TYPE, COLUMN_KEY, COLUMN_COMMENT from information_schema.columns where TABLE_SCHEMA=\"%s\";",
						dbName);
		String sql_keys = String
				.format("select CONSTRAINT_NAME, TABLE_NAME, COLUMN_NAME, REFERENCED_TABLE_NAME, REFERENCED_COLUMN_NAME from information_schema.KEY_COLUMN_USAGE where TABLE_SCHEMA=\"%s\";",
						dbName);
		
		try {
			DbUtils.loadDriver(driver);
			conn = DriverManager.getConnection(url, userName, password);
			QueryRunner qr = new QueryRunner();
			List<Map<String, Object>> tables = qr.query(conn,sql_tables, new MapListHandler());
			List<Map<String, Object>> columns = qr.query(conn,sql_columns, new MapListHandler());
			List<Map<String, Object>> keys = qr.query(conn,sql_keys, new MapListHandler());
			tables_json = JSONUtil.serialize(tables, false);
			columns_json = JSONUtil.serialize(columns, false);
			keys_json = JSONUtil.serialize(keys, false);
			
			DbUtils.closeQuietly(conn);
		} catch (Exception ex){
			return ERROR;
		}
		loadSchemaResult = true;
		return SUCCESS;
	}
	
	
	/* Private Methods */
	private boolean testMysql(){
		String url = "jdbc:mysql://"+hostName+":"+portName+"/"+dbName;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, userName, password);
			if (conn.isClosed())
				return false;
			conn.close();
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	/* Getter and Setters	 */

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEdbType() {
		return edbType;
	}

	public void setEdbType(String edbType) {
		this.edbType = edbType;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public boolean isTestResult() {
		return testResult;
	}

	public void setTestResult(boolean testResult) {
		this.testResult = testResult;
	}

	public boolean isSaveResult() {
		return saveResult;
	}

	public void setSaveResult(boolean saveResult) {
		this.saveResult = saveResult;
	}

	public boolean isLoadSchemaResult() {
		return loadSchemaResult;
	}

	public void setLoadSchemaResult(boolean loadSchemaResult) {
		this.loadSchemaResult = loadSchemaResult;
	}

	public String getTables_json() {
		return tables_json;
	}

	public void setTables_json(String tables_json) {
		this.tables_json = tables_json;
	}

	public String getColumns_json() {
		return columns_json;
	}

	public void setColumns_json(String columns_json) {
		this.columns_json = columns_json;
	}

	public String getKeys_json() {
		return keys_json;
	}

	public void setKeys_json(String keys_json) {
		this.keys_json = keys_json;
	}


}
