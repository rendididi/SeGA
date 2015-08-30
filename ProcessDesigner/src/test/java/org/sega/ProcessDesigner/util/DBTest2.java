package org.sega.ProcessDesigner.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

public class DBTest2 {

	public static void main(String[] args) {
		Connection conn = null;
		String driver = "com.mysql.jdbc.Driver";
		String dbName = "edb";
		String url = "jdbc:mysql://127.0.0.1:3306/"+dbName ;
		String sql_tables = String
				.format("select TABLE_SCHEMA, TABLE_NAME, TABLE_COMMENT from information_schema.tables where TABLE_SCHEMA=\"%s\";",
						dbName);
		String sql_columns = String
				.format("select TABLE_NAME, COLUMN_NAME, DATA_TYPE, COLUMN_TYPE, COLUMN_KEY, COLUMN_COMMENT from information_schema.columns where TABLE_SCHEMA=\"%s\";",
						dbName);
		
		try {
			DbUtils.loadDriver(driver);
			conn = DriverManager.getConnection(url, "root", "root");
			QueryRunner qr = new QueryRunner();
			List<Map<String, Object>> tables = qr.query(conn,sql_tables, new MapListHandler());
			List<Map<String, Object>> columns = qr.query(conn,sql_columns, new MapListHandler());
			System.out.println(org.apache.struts2.json.JSONUtil.serialize(tables, false));
			DbUtils.closeQuietly(conn);
		} catch (Exception ex){
			ex.printStackTrace();

		}

	}

}
