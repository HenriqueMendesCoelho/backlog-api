package com.fiapster.backlog;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class BacklogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BacklogApplication.class, args);
	}
	
	@PostConstruct
	public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }
}