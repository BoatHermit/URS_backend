package com.nju.urs.recommendation.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleAdmission {
    private int rank;
    private int score;
    private int year;
}
