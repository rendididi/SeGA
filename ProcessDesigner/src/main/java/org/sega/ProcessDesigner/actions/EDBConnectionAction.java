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
import org.hibernate.Session;
import org.sega.ProcessDesigner.models.DatabaseConfiguration;
import org.sega.ProcessDesigner.util.HibernateUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class EDBConnectionAction extends ProcessDesignerSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2169348924554203286L;
	
	private boolean testResult;
	private boolean saveResult;
	private boolean loadSchemaResult;
	private DatabaseConfiguration dbconfig;

	private static String driver = "com.mysql.jdbc.Driver";
	
	public String test() {
		testResult=false;
		
		if("mysql".equalsIgnoreCase(dbconfig.getType())){
			testResult = testMysql();
		}

		return SUCCESS;
	}
	
	public String save() {
		//update session process
		org.sega.ProcessDesigner.models.Process sp = (org.sega.ProcessDesigner.models.Process) getSession().get("process");

		Session hb_session = HibernateUtil.getSessionFactory().getCurrentSession();
		hb_session.beginTransaction();
		if(sp.getDbconfig()==null){
			hb_session.save(dbconfig);
			hb_session.flush();
			sp.setDbconfig(dbconfig);
		}else{
			sp.getDbconfig().setType(dbconfig.getType());
			sp.getDbconfig().setHost(dbconfig.getHost());
			sp.getDbconfig().setPort(dbconfig.getPort());
			sp.getDbconfig().setDatabase_name(dbconfig.getDatabase_name());
			sp.getDbconfig().setUsername(dbconfig.getUsername());
			sp.getDbconfig().setPassword(dbconfig.getPassword());
		}
		hb_session.saveOrUpdate(sp);
		hb_session.getTransaction().commit();
		
		//save persisted process in session
		getSession().put("process", sp);
		
		setSaveResult(true);
		return SUCCESS;
	}
	
	public String loadSchema() {
		save();
		
		loadSchemaResult = false;
		Connection conn = null;
		String url = "jdbc:mysql://"+dbconfig.getHost()+":"+dbconfig.getPort()+"/"+dbconfig.getDatabase_name();
		String sql_tables = String
				.format("select TABLE_SCHEMA, TABLE_NAME, TABLE_COMMENT from information_schema.tables where TABLE_SCHEMA=\"%s\";",
						dbconfig.getDatabase_name());
		String sql_columns = String
				.format("select TABLE_NAME, COLUMN_NAME, DATA_TYPE, COLUMN_TYPE, COLUMN_KEY, COLUMN_COMMENT from information_schema.columns where TABLE_SCHEMA=\"%s\";",
						dbconfig.getDatabase_name());
		String sql_keys = String
				.format("select CONSTRAINT_NAME, TABLE_NAME, COLUMN_NAME, REFERENCED_TABLE_NAME, REFERENCED_COLUMN_NAME from information_schema.KEY_COLUMN_USAGE where TABLE_SCHEMA=\"%s\";",
						dbconfig.getDatabase_name());
		
		try {
			DbUtils.loadDriver(driver);
			conn = DriverManager.getConnection(url, dbconfig.getUsername(), dbconfig.getPassword());
			QueryRunner qr = new QueryRunner();
			List<Map<String, Object>> tables = qr.query(conn,sql_tables, new MapListHandler());
			List<Map<String, Object>> columns = qr.query(conn,sql_columns, new MapListHandler());
			List<Map<String, Object>> keys = qr.query(conn,sql_keys, new MapListHandler());
			dbconfig.setTables_json(JSONUtil.serialize(tables, false));
			dbconfig.setColumns_json(JSONUtil.serialize(columns, false));
			dbconfig.setKeys_json(JSONUtil.serialize(keys, false));
			DbUtils.closeQuietly(conn);
		} catch (Exception ex){
			return ERROR;
		}
		loadSchemaResult = true;
		return SUCCESS;
	}
	
	
	/* Private Methods */
	private boolean testMysql(){
		String url = "jdbc:mysql://"+dbconfig.getHost()+":"+dbconfig.getPort()+"/"+dbconfig.getDatabase_name();
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, dbconfig.getUsername(), dbconfig.getPassword());
			if (conn.isClosed())
				return false;
			conn.close();
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	/* Getter and Setters	 */



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

	public DatabaseConfiguration getDbconfig() {
		return dbconfig;
	}

	public void setDbconfig(DatabaseConfiguration dbconfig) {
		this.dbconfig = dbconfig;
	}


}
