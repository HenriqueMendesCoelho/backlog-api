package com.fiapster.backlog.util;

import java.util.Base64;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiapster.backlog.core.dto.UserDto;

public class CredencialUtil {
	public static UserDto recuperarUsuario(String token) throws JsonMappingException, JsonProcessingException {
		String tokenSemBearer = token.substring(7);
		String tokenPayloadBase64 = tokenSemBearer.split("[.]")[1];
		String payload = new String(Base64.getDecoder().decode(tokenPayloadBase64));
		
		ObjectMapper mapper = new ObjectMapper();
		
		UserDto user = mapper.readValue(payload, UserDto.class);
		
		if(user.getLogin() == null || user.getAudiencia() == null || user.getExpiracao() == null) {
			throw new IllegalArgumentException();
		}
		
		return user;
	}
}
