package com.luxoft.bankapp.util;

/**
 * Created by Konrad on 2015-12-19.
 */
public class Validation {
    public static void checkForNull(Object obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
    }

    public static boolean checkIsEmail(String email) { return email.matches("^[A-Za-z\\.-0-9]{2,}@[A-Za-z\\.-0-9]{2,}\\.[A-Za-z]{2,3}$");}

//    public static boolean checkIsAccountNumber(String accountNumber){return accountNumber.matches("^[0-9\\- ]{26}$");}

    public static boolean checkIsAccountNumber(String accountNumber){return accountNumber.matches("[0-9]{0,2}\\s[0-9]{0,4}\\s[0-9]{0,4}\\s[0-9]{0,4}\\s[0-9]{0,4}\\s[0-9]{0,4}\\s[0-9]{0,4}");}


    public static boolean checkIsFullName(String name) {
        return name.matches("([A-Z]\\w+ [A-Z]\\w+)");
    }

    public static boolean checkIsCity(String city) {
        return city.matches("([A-Z]\\w+)");
    }

    public static boolean checkIsPhone(String phone) {
        return phone.matches("^[0-9\\-]{7,11}$");
    }

    public static boolean checkIsInteger(String number) {
        return number.matches("\\d+");
    }

    public static boolean checkIsNumeric(String number) {
        return number.matches("\\d+(\\.\\d+)?");
    }

    public static boolean checkIsExpectedNumber(String number, int lastIndex) {
        if (!checkIsInteger(number))
            return false;

        for (int i=0; i<=lastIndex; i++) {
            if (Integer.parseInt(number) == i)
                return true;
        }
        return false;
    }
}
