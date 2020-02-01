package com.example.destek.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

    public static String getStringDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        return format.format(date);
    }
}
