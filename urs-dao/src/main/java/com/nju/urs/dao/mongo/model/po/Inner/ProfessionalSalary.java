package com.nju.urs.dao.mongo.model.po.Inner;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class ProfessionalSalary {
    @Field("salaryavg")
    private Integer salaryAvg;
    @Field("majorsalaryavg")
    private List<Integer> majorSalaryAvg;
    @Field("allmajorsalaryavg")
    private List<Integer> allMajorSalaryAvg;
}
