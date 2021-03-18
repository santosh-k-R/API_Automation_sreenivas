package com.cisco.services.api_automation.utils;

import java.sql.*;
import org.json.JSONArray;

public class DBUtils {

    private static String pgHost = System.getenv("pgHostName");
    private static String pgDB = System.getenv("pg_dbName");
    private static String pgUserName = System.getenv("pg_username");
    private static String pgPassword = System.getenv("pg_password");
    private static String pgSqlClass = "jdbc:postgresql://";
    private static String mysqlHost = System.getenv("mysql_hostName");
    private static String mysqllDB = System.getenv("mysql_dbName");
    private static String mysqllUserName = System.getenv("mysql_userName");
    private static String mysqllPassword = System.getenv("mysql_password");
    private static String mysqlClass = "jdbc:mysql://";


    public static ResultSet getResultSet(String query) throws SQLException {
        ResultSet rs = null;
        try (Connection conn = DriverManager.getConnection(getURL(pgSqlClass), pgUserName, pgPassword)) {
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
        }
        return rs;
    }

    public static ResultSet executeQuery(String query) throws SQLException {
        ResultSet rs = null;
        try (Connection conn = DriverManager.getConnection(getURL(pgSqlClass), pgUserName, pgPassword)) {
            Statement st = conn.createStatement();
            st.execute(query);
        }
        return rs;
    }

    public static String getURL(String sqlClass) {
        return sqlClass + pgHost + "/" + pgDB;
    }
    
    public static JSONArray getMySqlResultSet(String query) throws Exception {
		JSONArray result = new JSONArray();
        ResultSet rs = null;
        try (Connection conn = DriverManager.getConnection(getMySqlURL(mysqlClass), mysqllUserName, mysqllPassword)) {
    	    System.out.println("Opened database successfully");
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
			result = Commons.convertResultSetIntoJSON(rs);
			rs.close();
			st.close();
			conn.close();
        }
        return result;
    }
    
    public static String getMySqlURL(String sqlClass) {
        return sqlClass + mysqlHost + "/" + mysqllDB+"?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false";
    }


}
