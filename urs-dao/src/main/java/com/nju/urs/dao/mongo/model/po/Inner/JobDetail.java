package com.nju.urs.dao.mongo.model.po.Inner;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class JobDetail {
    @Field("1")
    private List<JobCategory> category1;
    @Field("2")
    private List<JobCategory> category2;
    @Field("3")
    private List<JobCategory> category3;
}
