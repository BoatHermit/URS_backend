package com.nju.urs.dao.mongo.model.po.Inner;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class DualClass {
    private int id;
    private int school_id;
    private String className;
}
