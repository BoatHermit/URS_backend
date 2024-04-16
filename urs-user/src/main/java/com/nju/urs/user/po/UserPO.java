package com.nju.urs.user.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection= "user")
public class UserPO {
    //主键
    @Indexed
    @Field("uid")
    String id;
    @Field
    String name;
    @Field("phone")
    String phone;
    @Field("password")
    String password;
    @Field("place")
    String place;
    @Field("score")
    int score;
    @Field("rank")
    int rank;
    @Field("subjects")
    String subjects;

}
