package com.nju.urs.service.model.dto;

import com.nju.urs.dao.mongo.model.po.Inner.ScoreInner;
import com.nju.urs.dao.mongo.model.po.Score;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreTable {
    private List<String> scores;
    private List<String> nums;
    private List<String> totals;
    public ScoreTable(Score score) {
        this.scores = new ArrayList<>();
        this.nums = new ArrayList<>();
        this.totals = new ArrayList<>();
        for (ScoreInner scoreInner : score.getData()) {
            this.scores.add(scoreInner.getScore());
            this.nums.add(scoreInner.getNum());
            this.totals.add(scoreInner.getTotal());
        }
    }
}
