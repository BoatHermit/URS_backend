package com.nju.urs.dao.mongo.model.po;

import com.nju.urs.dao.mongo.model.po.Inner.ScoreInner;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Document(collection = "score")
public class Score {
    @MongoId
    private String id;
    @Field("province")
    private String province;
    @Field("student_type")
    private String studentType;
    @Field("score_type")
    private String scoreType;
    @Field("data")
    private List<ScoreInner> data;
}
