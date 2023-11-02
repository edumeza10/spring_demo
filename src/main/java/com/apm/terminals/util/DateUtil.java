package com.apm.terminals.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static String patternDate = "dd/MM/yyyy";
    private static String patternDateTime = "dd/MM/yyyy HH:mm";

    public DateUtil() {
    }

    public static String parseDate(Date stringDate) {
        if (stringDate != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patternDate);
            return simpleDateFormat.format(stringDate);
        } else {
            return null;
        }
    }

    public static String parseDateTime(Date stringDateTime) {
        if (stringDateTime != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patternDateTime);
            return simpleDateFormat.format(stringDateTime);
        } else {
            return null;
        }
    }
}
