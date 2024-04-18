package com.nju.urs.service.model.vo;

import com.nju.urs.dao.mongo.model.po.Inner.Impress;
import com.nju.urs.dao.mongo.model.po.Inner.JobDetail;
import com.nju.urs.dao.mongo.model.po.Inner.JobRate;
import com.nju.urs.dao.mongo.model.po.Inner.ProfessionalSalary;
import com.nju.urs.dao.mongo.model.po.Major;
import com.nju.urs.dao.mongo.model.po.MajorInfo;
import lombok.Data;

import java.util.List;

@Data
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
    private JobDetail jobDetail;
    private List<JobRate> jobRate;
    private String learnWhat;
    private String mostEmployedPosition;
    private String mostEmploymentArea;
    private String mostEmploymentIndustry;
    private String name;
    private ProfessionalSalary professionalSalary;
    private String selAdv;
    private List<String> specialSchool;
    private String type;
    private String typeDetail;

    public CompleteMajor(MajorInfo major) {
        this.celebrity = major.getCelebrity();
        this.code = major.getCode();
        this.content = major.getContent();
        this.course = major.getCourse();
        this.degree = major.getDegree();
        this.direction = major.getDirection();
        this.doWhat = major.getDoWhat();
        this.impress = major.getImpress();
        this.isWhat = major.getIsWhat();
        this.job = major.getJob();
        this.jobDetail = major.getJobDetail();
        this.jobRate = major.getJobRate();
        this.learnWhat = major.getLearnWhat();
        this.mostEmployedPosition = major.getMostEmployedPosition();
        this.mostEmploymentArea = major.getMostEmploymentArea();
        this.mostEmploymentIndustry = major.getMostEmploymentIndustry();
        this.name = major.getName();
        this.professionalSalary = major.getProfessionalSalary();
        this.selAdv = major.getSelAdv();
        this.specialSchool = major.getSpecialSchool();
        this.type = major.getType();
        this.typeDetail = major.getTypeDetail();
    }
}
