package com.nju.urs.common.model.dto;

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
}
