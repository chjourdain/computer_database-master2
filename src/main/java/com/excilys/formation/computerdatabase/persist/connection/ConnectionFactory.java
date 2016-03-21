package com.excilys.formation.computerdatabase.persist.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.excilys.formation.computerdatabase.persist.dao.exception.DAOConfigurationException;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

/**
 * Class to manage the connection, create return and close the connection.
 * 
 * @author charles
 *
 */
public class ConnectionFactory {
    private static final String CONFIG_FILENAME = "config_bd.properties";
    private static String userName;
    private static String userPsswd;
    private static String url;
    private static String driver = "";
    private static ConnectionFactory instance = null;
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class);
    private BoneCP connectionPool = null;

    static {
	try {
	    Properties prop = loadProp(CONFIG_FILENAME);
	    url = prop.getProperty("url", "");
	    userName = prop.getProperty("user", "");
	    userPsswd = prop.getProperty("password", "");
	    driver = prop.getProperty("driver", "");
	} catch (Exception e) {
	    LOGGER.error(e);
	}
	try {
	    Class.forName(driver);
	} catch (ClassNotFoundException e) {
	    throw new DAOConfigurationException("driver not found in classpath.", e);
	}
    }

    private ConnectionFactory() {
	try {

	    BoneCPConfig config = new BoneCPConfig();
	    config.setJdbcUrl(url);
	    config.setUsername(userName);
	    config.setPassword(userPsswd);
	    config.setMinConnectionsPerPartition(5);
	    config.setMaxConnectionsPerPartition(10);
	    config.setPartitionCount(2);
	    connectionPool = new BoneCP(config);
	} catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DAOConfigurationException("Configs error.", e);
	}
    }

    public static synchronized ConnectionFactory getConnectionManager() {
	if (instance == null) {
	    instance = new ConnectionFactory();
	}
	return instance;
    }

    /**
     * method which load the properties of the connection
     * 
     * @param filename
     * @return properties which must contain user & password url and driver use
     *         to connect
     * @throws IOException
     */
    public static Properties loadProp(String filename) throws IOException {
	Properties properties = new Properties();
	InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream(filename);
	if (input != null) {
	    properties.load(input);
	    return properties;
	}
	LOGGER.error("Impossible de charger le fichier de conf");
	return null;
    }

    public Connection getConn() {

	try {
	    return connectionPool.getConnection();
	} catch (SQLException e) {
	    LOGGER.error(e);
	    throw new RuntimeException();
	}
    }

    public void closeConnection(Connection conn, Statement stmt) {
	closeConnection(conn, stmt, null);
    }

    public void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
	try {
	    if (conn != null) {
		conn.close();
	    }
	    if (stmt != null) {
		stmt.close();
	    }
	    if (rs != null) {
		rs.close();
	    }
	} catch (SQLException e) {
	    String message = new StringBuilder("Couldn't close jdbc connection: ").append(e.getMessage()).toString();
	    throw new RuntimeException(message);
	}
    }
}
