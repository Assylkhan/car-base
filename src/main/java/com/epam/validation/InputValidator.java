package com.epam.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {
//    private static final String EMAIL_REGEX = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@" +
//            "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+(?:[A-Z]{2}|com|org|net|edu|gov|mil|" +
//            "biz|info|mobi|name|aero|asia|jobs|museum)\\b";
    private static Pattern pattern;
    private static final String LOGIN_REGEX = "^[a-zA-Z][a-zA-Z0-9-_\\.]{3,32}$";
    private static final String NAT_REGEX = "\\d{8,12}";
    private static final String MONEY = "\\d+";

    public static boolean nameOrLogin(String login){
        pattern = Pattern.compile(LOGIN_REGEX);
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }

    public static boolean natId(String natId){
        pattern = Pattern.compile(NAT_REGEX);
        Matcher matcher = pattern.matcher(natId);
        return matcher.matches();
    }

    public static boolean money(String money){
        pattern = Pattern.compile(MONEY);
        Matcher matcher = pattern.matcher(money);
        return matcher.matches();
    }

}
