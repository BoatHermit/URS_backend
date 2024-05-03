package com.nju.urs.dao.mongo.model.po.Inner;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class AppositiveFraction {
    @Field("year")
    private Integer year;
    @Field("score")
    private String score;
    @Field("rank_range")
    private String rankRange;
}
