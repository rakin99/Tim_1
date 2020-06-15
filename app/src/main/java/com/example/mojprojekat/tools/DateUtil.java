package com.example.mojprojekat.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	static SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
	static SimpleDateFormat fmtTime = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	static SimpleDateFormat fmtTimeWithSecond = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public static GregorianCalendar convertFromDMY(String dd_mm_yy) throws ParseException {

        // this actually works, got rid of the original code idea
        String[] splitDate = dd_mm_yy.split("-");
        int days = Integer.parseInt(splitDate[0]);
        int month = (Integer.parseInt(splitDate[1]) - 1);
        int year = Integer.parseInt(splitDate[2]);

        // dates go in properly
        GregorianCalendar dateConverted = new GregorianCalendar(year, month, days);
        String finalDate = format(dateConverted);
        return dateConverted;
    }
    
    public static GregorianCalendar convertFromDMYHM(String dd_mm_yy_hh_mm) throws ParseException{

        // this actually works, got rid of the original code idea
        String[] datumVreme = dd_mm_yy_hh_mm.split(" ");
        String[] splitDate=datumVreme[0].split("-");
        int days = Integer.parseInt(splitDate[0]);
        int month = (Integer.parseInt(splitDate[1]) - 1);
        int year = Integer.parseInt(splitDate[2]);
        String[] splitTime=datumVreme[1].split(":");
        int sat= Integer.parseInt(splitTime[0]);
        int minut=Integer.parseInt(splitTime[1]);

        // dates go in properly
        GregorianCalendar dateConverted = new GregorianCalendar(year, month, days, sat, minut);
        String finalDate = formatTime(dateConverted);
        return dateConverted;
    }
    
    public static GregorianCalendar convertFromDMYHMS(String dd_mm_yy_hh_mm_ss) throws ParseException{

        // this actually works, got rid of the original code idea
        String[] datumVreme = dd_mm_yy_hh_mm_ss.split(" ");
        String[] splitDate=datumVreme[0].split("-");
        int days = Integer.parseInt(splitDate[0]);
        int month = (Integer.parseInt(splitDate[1]) - 1);
        int year = Integer.parseInt(splitDate[2]);
        String[] splitTime=datumVreme[1].split(":");
        int sat= Integer.parseInt(splitTime[0]);
        int minut=Integer.parseInt(splitTime[1]);
        int sekund=Integer.parseInt(splitTime[1]);

        // dates go in properly
        GregorianCalendar dateConverted = new GregorianCalendar(year, month, days, sat, minut, sekund);
        String finalDate = formatTimeWithSecond(dateConverted);
        return dateConverted;
    }

    public static String format(GregorianCalendar date) throws ParseException{

       fmt.setCalendar(date);
        String dateFormatted = fmt.format(date.getTime());
        return dateFormatted;
    }
    
    public static String formatTime(GregorianCalendar date) throws ParseException{

        fmtTime.setCalendar(date);
         String dateFormatted = fmtTime.format(date.getTime());
         return dateFormatted;
     }
    
    public static String formatTimeWithSecond(GregorianCalendar date) throws ParseException{

        fmtTimeWithSecond.setCalendar(date);
         String dateFormatted = fmtTimeWithSecond.format(date.getTime());
         return dateFormatted;
     }
    
    public static boolean CompareDate(GregorianCalendar veciDatum,GregorianCalendar manjiDatum) {
    	int i=veciDatum.compareTo(manjiDatum);
    	if (i==1) {
    		return true;
    	}
    	return false;
    }
    
    public static boolean Compare(GregorianCalendar veciDatum,GregorianCalendar manjiDatum) {
    	int i=veciDatum.compareTo(manjiDatum);
    	if (i==0) {
    		return true;
    	}
    	return false;
    }
    
    public static String convert(String vreme) {
    	String [] niz=vreme.split("T");
    	String [] nizDatum=niz[0].split("-");
    	String v=niz[1];
    	String s=nizDatum[2]+"-"+nizDatum[1]+"-"+nizDatum[0]+" "+v;
    	return s;
    }

    public static GregorianCalendar getGregorianCalendarFromDate(Date date) throws ParseException {
        GregorianCalendar calendar=new GregorianCalendar(date.getYear()+1900,date.getMonth(), date.getDate(),date.getHours(),date.getMinutes(),date.getSeconds());
        return calendar;
    }

    public static GregorianCalendar getNow() throws ParseException {
        String mesec="";
        String dan="";
        String godina="";
        String sat="";
        String minut="";
        String sekund="";
        GregorianCalendar danasnjiDatum=new GregorianCalendar();
        mesec=String.valueOf(danasnjiDatum.get(GregorianCalendar.MONTH)+1);
        dan=String.valueOf(danasnjiDatum.get(GregorianCalendar.DAY_OF_MONTH));
        godina=String.valueOf(danasnjiDatum.get(GregorianCalendar.YEAR));
        sat=String.valueOf(danasnjiDatum.get(GregorianCalendar.HOUR_OF_DAY));
        minut=String.valueOf(danasnjiDatum.get(GregorianCalendar.MINUTE));
        sekund=String.valueOf(danasnjiDatum.get(GregorianCalendar.SECOND));
        System.out.println(formatTimeWithSecond(danasnjiDatum));
        return danasnjiDatum;
    }
	
}
