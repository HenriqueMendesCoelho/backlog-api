package com.fiapster.backlog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Value;

import com.fiapster.backlog.util.DbUtilBacklog;

public class ConfigSystemDAO {
	
	@Value("${spring.profiles.active}")
	private static String profile;
	
	private Connection connection;
	
	public ConfigSystemDAO() {
        connection = DbUtilBacklog.getConnection();
    }
	
	public void reprocessarNivel(int nivel) throws Exception {
		
		try {
			PreparedStatement preparedStatement = null;
			if(profile == "dev") {
				preparedStatement = connection
	                    .prepareStatement("UPDATE sysusers SET nivel = IF(FLOOR(pontos/?)<=1,1,FLOOR(pontos/?))");
			}else {
				preparedStatement = connection
	                    .prepareStatement("UPDATE sysusers SET nivel = CASE WHEN FLOOR(pontos/?)<=1 THEN 1 ELSE FLOOR(pontos/?) END");
			}
			
			
			
			preparedStatement.setInt(1, nivel);
			preparedStatement.setInt(2, nivel);
			preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			throw new Exception(e);
		}
		
	}

}
