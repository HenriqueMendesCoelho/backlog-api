package com.fiapster.backlog.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoggerInterceptor implements HandlerInterceptor {
	
	private static Logger log = LoggerFactory.getLogger(LoggerInterceptor.class);
	
	@Override
	public void afterCompletion(
	  HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) 
	  throws Exception {
	    if (ex != null){
	        ex.printStackTrace();
	    }
	    log.info("[" + response.getStatus() + "]" + "[" + request.getMethod()
	      + "]" + request.getRequestURI() + "[from:" + request.getHeader("x-forwarded-for") + "]");
	}
	
	

}