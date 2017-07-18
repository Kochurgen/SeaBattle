package com.example.vladimir.seabattle.ui.result;


import java.text.SimpleDateFormat;

class TimeFormat{

    private static final String PATTERN_MM_SS = "mm:ss";

    public static String convertTimeStampToDate(long timeStamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_MM_SS);
        return simpleDateFormat.format(timeStamp);
    }

}
