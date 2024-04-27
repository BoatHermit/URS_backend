package com.nju.urs.recommendation.model.vo;

import com.nju.urs.common.enums.Province;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentInfo {
    private int score;
    private int rank;
    private String province;
    /**
     * 六位，依次代表物理、化学、生物、历史、政治、地理
     */
    private String subjects;

    public String toString() {
        return "{score: " + score + ", rank: " + rank +
                ", provinceId: " + Province.getIdByName(province) + ", subjects: " + subjects + "}";
    }
}
