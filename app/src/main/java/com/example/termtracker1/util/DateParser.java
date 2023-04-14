package com.example.termtracker1.util;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DateParser {
    public static Date dateFromString(String dtString){
        Date date=null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(dtString);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
