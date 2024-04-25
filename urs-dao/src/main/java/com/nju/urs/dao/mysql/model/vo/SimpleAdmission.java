package com.nju.urs.dao.mysql.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SimpleAdmission implements Serializable, Comparable<SimpleAdmission> {
    private int rank;
    private int score;
    private int year;

    @Override
    public int compareTo(@NotNull SimpleAdmission o) {
        return Integer.compare(this.getYear(),o.getYear());
    }
}
