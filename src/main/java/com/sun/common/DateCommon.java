package com.sun.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateCommon {
    public static String Format(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateFormat.FORMAT_01);

        String formatDateTime = date.format(formatter);
        return formatDateTime;
    }
}
