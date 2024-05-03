package com.nju.urs.service.model.dto;

import com.nju.urs.dao.mongo.model.po.Inner.AppositiveFraction;
import com.nju.urs.dao.mongo.model.po.Inner.ScoreInner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreInfo {
    private String score;
    private String num;
    private String rankRange;
    private String batchName;
    private String controlScore;
    private List<AppositiveFraction> appositiveFractions;

    public ScoreInfo(ScoreInner scoreInner) {
        this.score = scoreInner.getScore();
        this.num = scoreInner.getNum();
        this.rankRange = scoreInner.getRankRange();
        this.batchName = scoreInner.getBatchName();
        this.controlScore = scoreInner.getControlScore();
        this.appositiveFractions = scoreInner.getAppositiveFractions();
    }
}
