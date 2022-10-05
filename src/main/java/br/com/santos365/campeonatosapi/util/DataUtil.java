package br.com.santos365.campeonatosapi.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {

	public static String formataDateEmString(Date date, String mask) {
		DateFormat out = new SimpleDateFormat(mask);  
		   
		String result = out.format(date);
		
		return result;
	}
}
