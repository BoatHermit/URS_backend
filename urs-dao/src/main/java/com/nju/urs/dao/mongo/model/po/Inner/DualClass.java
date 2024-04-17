package com.nju.urs.dao.mongo.model.po.Inner;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class DualClass {
    @Field("id")
    private String id;
    @Field("school_id")
    private String schoolId;
    @Field("class")
    private String className;
}
