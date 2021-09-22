package com.fiapster.backlog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.fiapster.backlog.util.DbUtilBacklog;

public class ConfigSystemDAO {
	
	private Connection connection;
	
	public ConfigSystemDAO() {
        connection = DbUtilBacklog.getConnection();
    }
	
	public void reprocessarNivel(int nivel) throws Exception {
		
		try {
			PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE sysusers SET nivel = IF(FLOOR(pontos/?)<=1,1,FLOOR(pontos/?))");
			
			preparedStatement.setInt(1, nivel);
			preparedStatement.setInt(2, nivel);
			preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			throw new Exception(e);
		}
		
	}

}
