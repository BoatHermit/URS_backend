package com.nju.urs.web;

import com.nju.urs.dao.mongo.model.vo.SimpleMajor;
import com.nju.urs.service.model.param.MajorFilterParam;
import com.nju.urs.service.model.vo.CompleteMajor;
import com.nju.urs.service.model.vo.MajorConditions;
import com.nju.urs.service.service.MajorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UrsWebApplication.class)
public class MajorTest {
    @Autowired
    MajorService majorService;

    //code存在
    @Test
    public void getByCode1(){
        String code="010101";
        CompleteMajor major = majorService.getMajorByCode(code);
        Assert.assertNotNull(major);
    }

    //code不存在
    @Test
    public void getByCode2(){
        String code="000000";
        CompleteMajor major = majorService.getMajorByCode(code);
        Assert.assertEquals(null,major);
    }

    @Test
    public void filter(){
        MajorFilterParam param=new MajorFilterParam();
        MajorConditions conditions=new MajorConditions();
        conditions.setLevel1Name("本科");
        conditions.setLevel2Name("法学");
        param.setPageNo(1);
        param.setPageSize(20);
        param.setKeyword("哲学");
        param.setConditions(conditions);

        List<SimpleMajor> res=majorService.getMajorsByConditions(param);
        Assert.assertNotNull(res);
    }

    @Test
    public void filter_count(){
        MajorFilterParam param=new MajorFilterParam();
        MajorConditions conditions=new MajorConditions();
        conditions.setLevel1Name("本科");
        conditions.setLevel2Name("法学");
        param.setPageSize(20);
        param.setKeyword("哲学");
        param.setConditions(conditions);

        int res=majorService.countPagesByConditions(param);
        Assert.assertEquals(1,res);
    }


}
