package com.fiapster.backlog.methods;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;



public class ObterDataEHora {
	
	public String obterDataEHora(){
		Instant now = Instant.now();
		ZonedDateTime date = now.atZone(ZoneId.of("America/Sao_Paulo"));
		
		String dia = (date.getDayOfMonth() < 10) ? "0"+ date.getDayOfMonth() : Integer.toString(date.getDayOfMonth());
		String mes = (date.getMonthValue() < 10) ? "0"+ date.getMonthValue() : Integer.toString(date.getMonthValue());
		
		
		String data = dia+"-"+mes+"-"+date.getYear();
		
		return data + "|" + obterHora();
	}
	
	public String obterHora() {
		Instant now = Instant.now();
		ZonedDateTime date = now.atZone(ZoneId.of("America/Sao_Paulo"));
		
		String hours = (date.getMonthValue() < 10) ? "0"+ date.getHour() : Integer.toString(date.getHour());
		String minutes = (date.getMinute() < 10) ? "0"+ date.getMinute() : Integer.toString(date.getMinute());
		String seconds = (date.getSecond() < 10) ? "0"+ date.getSecond() : Integer.toString(date.getSecond());
		
		return hours + ":" + minutes + ":" + seconds;
	}
	
	public double obterIntevaloHoraEmMinutos(long hora) {
		double intervalo = obterDataAgora() - hora;
		intervalo = intervalo / 1000 / 60;
		
		return intervalo;
	}
	
	public long obterDataAgora() {
		Date d = new Date();
		
		return d.getTime();
	}
	
}
