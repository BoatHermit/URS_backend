package com.nju.urs.common.utils;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Objects;
import java.util.TreeMap;

public class ElParser {
    public static String parse(String elString, TreeMap<String,Object> map){
        String expressionString= String.format("#{%s}",elString);
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        map.forEach(context::setVariable);

        Expression expression = parser.parseExpression(expressionString, new TemplateParserContext());
        StringBuilder result = new StringBuilder();
        result.append(elString).append(": ");
        result.append(expression.getValue(context)).append(";");
        return result.toString();
    }
}
