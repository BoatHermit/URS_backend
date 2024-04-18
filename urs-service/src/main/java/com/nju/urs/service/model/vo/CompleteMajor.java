package com.nju.urs.service.model.vo;

import com.nju.urs.dao.mongo.model.po.Inner.Impress;
import com.nju.urs.dao.mongo.model.po.Inner.JobDetail;
import com.nju.urs.dao.mongo.model.po.Inner.JobRate;
import com.nju.urs.dao.mongo.model.po.Inner.ProfessionalSalary;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

public class CompleteMajor {
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
    private String mostEmployedPosition;
    private String mostEmploymentArea;
    private String mostEmploymentIndustry;
    private String name;
    private List<ProfessionalSalary> professionalSalary;
    private String selAdv;
    private List<String> specialSchool;
    private String type;
    private String typeDetail;
}
