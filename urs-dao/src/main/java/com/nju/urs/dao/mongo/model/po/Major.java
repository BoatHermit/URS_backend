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
    @Field("degree")
    private String degree;
    @Field("hightitle")
    private String highTitle;
    @Field("level1_name")
    private String level1Name;
    @Field("level2_name")
    private String level2Name;
    @Field("level3_name")
    private String level3Name;
    @Field("name")
    private String name;
    @Field("spcode")
    private String code;
    @Field("view_week")
    private String viewWeek;
    @Field("special_id")
    private String specialId;
}
