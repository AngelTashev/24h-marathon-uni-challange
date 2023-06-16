package util;

import models.basic.Cashier;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static constants.StringsConstants.DATE_REGEX;
import static constants.StringsConstants.ID_REGEX;

public class GeneralUtil {

    public static int returnId(String str) {
        if (str == null || str.isEmpty()) return -1;
        Pattern p = Pattern.compile(ID_REGEX);
        Matcher m = p.matcher(str);
        if (!m.find()) return -1;
        return Integer.parseInt(m.group(1));
    }

    public static boolean validateDate(String date) {
        if (date == null || date.isEmpty() || !date.matches(DATE_REGEX)) return false;

        String[] split = date.split("\\.");
        if (split.length != 3) return false;

        int day = Integer.parseInt(split[0]);
        if (day < 1 || day > 31) return false;
        int month = Integer.parseInt(split[1]);
        if (month < 1 || month > 12) return false;
        int year = Integer.parseInt(split[2]);
        return year >= 1970 && year <= 2500;
    }
}
