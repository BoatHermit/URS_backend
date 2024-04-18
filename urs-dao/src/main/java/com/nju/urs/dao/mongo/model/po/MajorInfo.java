package com.nju.urs.dao.mongo.model.po;

import com.nju.urs.dao.mongo.model.po.Inner.Impress;
import com.nju.urs.dao.mongo.model.po.Inner.JobDetail;
import com.nju.urs.dao.mongo.model.po.Inner.JobRate;
import com.nju.urs.dao.mongo.model.po.Inner.ProfessionalSalary;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Document(collection = "major_info")
public class MajorInfo {
    @MongoId
    private String id;
    @Field("celebrity")
    private String celebrity;
    @Field("code")
    private String code;
    @Field("content")
    private String content;
    @Field("course")
    private String course;
    @Field("degree")
    private String degree;
    @Field("direction")
    private String direction;
    @Field("do_what")
    private String doWhat;
    @Field("impress")
    private List<Impress> impress;
    @Field("is_what")
    private String isWhat;
    @Field("job")
    private String job;
    @Field("jobdetail")
    private JobDetail jobDetail;
    @Field("jobrate")
    private List<JobRate> jobRate;
    @Field("learn_what")
    private String learnWhat;
    @Field("mostemployedeposition")
    private String mostEmployedPosition;
    @Field("mostemploymentarea")
    private String mostEmploymentArea;
    @Field("mostemploymentindustry")
    private String mostEmploymentIndustry;
    @Field("name")
    private String name;
    @Field("professionalsalary")
    private ProfessionalSalary professionalSalary;
    @Field("seladv")
    private String selAdv;
    @Field("specialschool")
    private List<String> specialSchool;
    @Field("type")
    private String type;
    @Field("type_detail")
    private String typeDetail;

}
