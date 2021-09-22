package com.fiapster.backlog.util;

/**
 * @author Henrique
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtilBacklog {
	
private static Connection connection = null;
	
	public static Connection getConnection() {
		if (connection != null)
            return connection;
        else {
            try {
                @SuppressWarnings("unused")
				Properties prop = new Properties();
                
                String user = "root";
                String pass = "8u3T7&HQ$5o^x##";
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/backlog", user, pass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return connection;
        }
	}

}
