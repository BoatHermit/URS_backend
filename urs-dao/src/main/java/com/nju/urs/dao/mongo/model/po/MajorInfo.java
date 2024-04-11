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
    private String celebrity;
    private String code;
    private String content;
    private String course;
    private String degree;
    private String direction;
    private String doWhat;
    private List<Impress> impress;
    private String isWhat;
    private String job;
    private List<JobDetail> jobDetail;
    private List<JobRate> jobRate;
    private String learnWhat;
    @Field("mostemployedeposition")
    private String mostEmployedPosition;
    @Field("mostemploymentarea")
    private String mostEmploymentArea;
    @Field("mostemploymentindustry")
    private String mostEmploymentIndustry;
    private String name;
    @Field("professionalsalary")
    private List<ProfessionalSalary> professionalSalary;
    private String selAdv;
    @Field("specialschool")
    private List<String> specialSchool;
    private String type;
    private String typeDetail;

}
