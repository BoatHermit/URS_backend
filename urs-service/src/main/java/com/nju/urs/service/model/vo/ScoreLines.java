package com.nju.urs.service.model.vo;

import com.nju.urs.dao.mysql.model.vo.SimpleAdmission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreLines {
    private List<Integer> scores;
    private List<Integer> rank;
    private List<Integer> year;
}
