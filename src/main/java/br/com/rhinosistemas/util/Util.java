package br.com.rhinosistemas.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util {

	public static Date convertStringToDate(String data) {

		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}
	public static String formataData(String data) {
		
		try {
			DateFormat formato = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm");
			DateFormat formatoRetorno = new SimpleDateFormat("MM-dd");
			Date date = formato.parse(data);
			
			return formatoRetorno.format(date);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Date stringToDateSemHoras(Date data) {
		Calendar dataSemHora = Calendar.getInstance();
		dataSemHora.setTime(data);
		dataSemHora.set(Calendar.HOUR, 0);
		dataSemHora.set(Calendar.MINUTE, 0);
		dataSemHora.set(Calendar.SECOND, 0);
		dataSemHora.set(Calendar.MILLISECOND, 0);
		
		return dataSemHora.getTime();
	}
	
	public static Date stringToDateFinalDia(Date data) {
        Calendar dataSemHora = Calendar.getInstance();
        dataSemHora.setTime(data);
        dataSemHora.set(Calendar.HOUR_OF_DAY, 23);
        dataSemHora.set(Calendar.MINUTE, 59);
        dataSemHora.set(Calendar.SECOND, 59);
        dataSemHora.set(Calendar.MILLISECOND, 59);
        
        return dataSemHora.getTime();
    }

}
