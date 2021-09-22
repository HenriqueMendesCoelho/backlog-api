package com.fiapster.backlog.methods;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;



public class ObterDataEHora {
	
	public String obterDataEHora(){
		LocalDate date = LocalDate.now();
		Calendar c = Calendar.getInstance();
		String mes = "";
		String hours = "";
		String minutes = "";
		String seconds = "";
		
		if(date.getMonthValue() < 10) {
			mes = "0"+date.getMonthValue();
		}
		
		if(c.get(Calendar.HOUR_OF_DAY) < 10) {
			hours = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
			hours = "0"+hours;
		}else {
			hours = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
		}
		
		if(c.get(Calendar.MINUTE) < 10) {
			minutes = Integer.toString(c.get(Calendar.MINUTE));
			minutes = "0"+minutes;
		}else {
			minutes = Integer.toString(c.get(Calendar.MINUTE));
		}
		
		if(c.get(Calendar.SECOND) < 10) {
			seconds = "0"+ Integer.toString(c.get(Calendar.SECOND));
		}else {
			seconds = Integer.toString(c.get(Calendar.SECOND));
		}
		
		
		String data = date.getDayOfMonth()+"-"+mes+"-"+date.getYear();
		
		return data + "|" + hours + ":" + minutes + ":" + seconds;
	}
	
	public String obterHora() {
		Calendar c = Calendar.getInstance();
		
		String hours = "";
		String minutes = "";
		String seconds = "";
		
		if(c.get(Calendar.HOUR_OF_DAY) < 10) {
			hours = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
			hours = "0"+hours;
		}else {
			hours = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
		}
		
		if(c.get(Calendar.MINUTE) < 10) {
			minutes = Integer.toString(c.get(Calendar.MINUTE));
			minutes = "0"+minutes;
		}else {
			minutes = Integer.toString(c.get(Calendar.MINUTE));
		}
		
		if(c.get(Calendar.SECOND) > 10) {
			seconds = "0"+ Integer.toString(c.get(Calendar.SECOND));
		}else {
			seconds = Integer.toString(c.get(Calendar.SECOND));
		}
		
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
