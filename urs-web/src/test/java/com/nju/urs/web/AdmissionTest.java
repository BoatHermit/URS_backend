package com.nju.urs.web;

import com.nju.urs.recommendation.model.vo.StudentInfo;
import com.nju.urs.service.model.dto.SchoolAdmissionPage;
import com.nju.urs.service.model.param.SchoolAdmissionParam;
import com.nju.urs.service.service.AdmissionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UrsWebApplication.class)
public class AdmissionTest {
    @Autowired
    AdmissionService admissionService;

    @Test
    public void SchoolAdmission(){
        SchoolAdmissionParam param=new SchoolAdmissionParam();
        StudentInfo studentInfo=new StudentInfo();
        studentInfo.setScore(666);
        studentInfo.setRank(420);
        studentInfo.setProvince("江苏");
        studentInfo.setSubjects("111000");

        param.setPageNo(1);
        param.setPageSize(20);
        param.setSchoolId("111");
        param.setStudentInfo(studentInfo);

        SchoolAdmissionPage res=admissionService.schoolAdmission(param);
        Assert.assertNotNull(res);
    }
}
