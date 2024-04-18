package com.nju.urs.common.utils;

public class FuzzySearch {
    public static String getRegex(String keyword) {
        StringBuilder regex = new StringBuilder("(?:");
        String[] strings = keyword.split(" ");
        for (String string : strings) {
            regex.append(".*").append(string);
        }
        regex.append(".*)");
        return regex.toString();
    }
}
