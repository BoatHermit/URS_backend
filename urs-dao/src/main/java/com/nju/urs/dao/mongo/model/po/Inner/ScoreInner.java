package com.nju.urs.dao.mongo.model.po.Inner;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class ScoreInner {
    @Field("score")
    private String score;
    @Field("num")
    private String num;
    @Field("total")
    private String total;
    @Field("rank_range")
    private String rankRange;
    @Field("batch_name")
    private String batchName;
    @Field("controlscore")
    private String controlScore;
    @Field("appositive_fraction")
    private List<AppositiveFraction> appositiveFractions;
}
