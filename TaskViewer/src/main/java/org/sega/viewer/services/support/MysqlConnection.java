package org.sega.viewer.services.support;

import org.sega.viewer.models.DatabaseConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Raysmond<i@raysmond.com>
 */
public class MysqlConnection {
    private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private static final String MYSQL_CONNECTION_URL = "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";

    private Connection connection = null;
    private DatabaseConfiguration edb;

    public MysqlConnection(DatabaseConfiguration edb) throws UnSupportEdbException {
        if (edb == null || !edb.getType().equals("mysql")) {
            throw new UnSupportEdbException();
        }

        this.edb = edb;
    }

    public Connection open() throws ClassNotFoundException, SQLException, UnSupportEdbException {
        if (connection == null) {
            String dbUrl = String.format(MYSQL_CONNECTION_URL, edb.getHost(), edb.getPort(), edb.getDatabase_name());
            String user = edb.getUsername();
            String password = edb.getPassword();

            Class.forName(MYSQL_DRIVER);
            connection = DriverManager.getConnection(dbUrl, user, password);
        }

        return connection;
    }

    public void close() throws SQLException {
        this.connection.close();
    }
}
