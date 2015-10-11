package org.sega.ProcessDesigner.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.crypto.Data;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class TimeUtil {
	
	public static XMLGregorianCalendar getTime(){
		Date date = new Date();
		GregorianCalendar cal = new GregorianCalendar();
	    cal.setTime(date);
	    XMLGregorianCalendar gc = null;
	    try {
	        gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	    } catch (Exception e) {

	         e.printStackTrace();
	    }
	    
	    return gc;
	}
	
}
