package com.nju.urs.dao.mongo.model.po.Inner;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class JobCategory {
    private String id;
    private String specialId;
    private String name;
    private String detailJob;
    private String detailPos;
    private String rate;
    private String area;
    private String type;
    private String sort;
}
