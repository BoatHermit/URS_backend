package com.nju.urs.recommendation.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
public class SimpleAdmission implements Comparable<SimpleAdmission> {
    private int rank;
    private int score;
    private int year;

    @Override
    public int compareTo(@NotNull SimpleAdmission o) {
        return Integer.compare(this.getYear(),o.getYear());
    }
}
