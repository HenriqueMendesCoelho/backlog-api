package com.fiapster.backlog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.fiapster.backlog.util.DbUtilBacklog;

public class EmailDAO {
	
private Connection connection;
	
	public EmailDAO() {
        connection = DbUtilBacklog.getConnection();
    }
	
	public void enviaEmailConfig() throws Exception {
		
		try {
			PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE emails SET email_config=?");
			
			preparedStatement.setInt(1, 0);
			preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			throw new Exception(e);
		}
		
	}

}
