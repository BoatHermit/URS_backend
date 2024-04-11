package com.nju.urs.dao.mongo.model.po;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "major")
public class Major {
    @MongoId
    private String id;
    private String degree;
    private String highTitle;
    private String level1Name;
    private String level2Name;
    private String level3Name;
    private String name;
    @Field("spcode")
    private String spCode;
    private String viewWeek;
    private String specialId;
}
