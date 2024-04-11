package com.nju.urs.dao.mongo.model.po.Inner;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class JobRate {
    private String id;
    private String specialId;
    private String year;
    private String rate;

}
