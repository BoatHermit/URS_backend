package com.nju.urs.dao.mongo.model.po.Inner;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class JobRate {
    @Field("id")
    private String id;
    @Field("special_id")
    private String specialId;
    @Field("year")
    private String year;
    @Field("rate")
    private String rate;

}
