package com.nju.urs.dao.mongo.model.po.Inner;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class JobCategory {
    @Field("id")
    private String id;
    @Field("special_id")
    private String specialId;
    @Field("name")
    private String name;
    @Field("detail_job")
    private String detailJob;
    @Field("detail_pos")
    private String detailPos;
    @Field("rate")
    private String rate;
    @Field("area")
    private String area;
    @Field("type")
    private String type;
    @Field("sort")
    private String sort;
}
