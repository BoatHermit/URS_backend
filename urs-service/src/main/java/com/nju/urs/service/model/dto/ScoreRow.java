package com.nju.urs.service.model.dto;

import com.nju.urs.dao.mongo.model.po.Inner.ScoreInner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreRow {
    private String score;
    private String num;
    private String total;

    public ScoreRow(ScoreInner scoreInner) {
        this.score = scoreInner.getScore();
        this.num = scoreInner.getNum();
        this.total = scoreInner.getTotal();
    }
}
