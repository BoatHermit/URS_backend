package com.nju.urs.dao.mongo.model.po.Inner;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Impress {
    @Field("id")
    private String id;
    @Field("special_id")
    private String specialId;
    @Field("key_word")
    private String keyWord;
    @Field("img_url")
    private String imgUrl;
}
