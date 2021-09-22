package com.fiapster.backlog.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtilCurso {
	
private static Connection connectionCliente = null;
	
	public static Connection getConnection() {
		if (connectionCliente != null)
            return connectionCliente;
        else {
            try {
                @SuppressWarnings("unused")
				Properties prop = new Properties();
                
                String user = "root";
                String pass = "8u3T7&HQ$5o^x##";
                Class.forName("com.mysql.cj.jdbc.Driver");
                connectionCliente = DriverManager.getConnection("jdbc:mysql://localhost:3306/curso", user, pass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return connectionCliente;
        }
	}

}
