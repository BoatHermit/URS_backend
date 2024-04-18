package com.nju.urs.common.utils;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class QueryUtils {
    public static String getFuzzyRegex(String keyword) {
        StringBuilder regex = new StringBuilder("(?:");
        String[] strings = keyword.split(" ");
        for (String string : strings) {
            regex.append(".*").append(string);
        }
        regex.append(".*)");
        return regex.toString();
    }

    public static void addCondition(Query query, String fieldName, String value) {
        if (value != null && !value.isEmpty()) {
            query.addCriteria(Criteria.where(fieldName).is(value));
        }
    }
}
