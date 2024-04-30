package com.nju.urs.web;

import com.nju.urs.recommendation.model.vo.StudentInfo;
import com.nju.urs.service.model.dto.RecommendPage;
import com.nju.urs.service.model.param.RecommendParam;
import com.nju.urs.service.service.AdmissionService;
import io.jsonwebtoken.lang.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UrsWebApplication.class)
public class RecommendationTest {
    @Autowired
    AdmissionService admissionService;

    @Test
    public void HighRisk(){
        RecommendParam param=new RecommendParam();

        StudentInfo studentInfo=new StudentInfo();
        studentInfo.setScore(666);
        studentInfo.setRank(420);
        studentInfo.setProvince("江苏");
        studentInfo.setSubjects("111000");

        param.setPageNo(1);
        param.setPageSize(20);
        param.setStudentInfo(studentInfo);

        RecommendPage res=admissionService.recommendHighRisk(param);

        Assert.notNull(res);
    }

    @Test
    public void MidRisk(){
        RecommendParam param=new RecommendParam();

        StudentInfo studentInfo=new StudentInfo();
        studentInfo.setScore(666);
        studentInfo.setRank(420);
        studentInfo.setProvince("江苏");
        studentInfo.setSubjects("111000");

        param.setPageNo(1);
        param.setPageSize(20);
        param.setStudentInfo(studentInfo);

        RecommendPage res=admissionService.recommendMidRisk(param);

        Assert.notNull(res);
    }

    public void LowRisk(){
        RecommendParam param=new RecommendParam();

        StudentInfo studentInfo=new StudentInfo();
        studentInfo.setScore(666);
        studentInfo.setRank(420);
        studentInfo.setProvince("江苏");
        studentInfo.setSubjects("111000");

        param.setPageNo(1);
        param.setPageSize(20);
        param.setStudentInfo(studentInfo);

        RecommendPage res=admissionService.recommendLowRisk(param);

        Assert.notNull(res);
    }

}
