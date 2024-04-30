package com.nju.urs.web;

import com.nju.urs.dao.mongo.model.vo.SimpleSchool;
import com.nju.urs.service.model.param.SchoolFilterParam;
import com.nju.urs.service.model.vo.CompleteSchool;
import com.nju.urs.service.model.vo.SchoolConditions;
import com.nju.urs.service.service.SchoolService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UrsWebApplication.class)
public class SchoolTest {
    @Autowired
    SchoolService schoolService;

    //根据id获得学校信息
    //id存在
    @Test
    public void getById1(){
        String id="111";
        CompleteSchool completeSchool=schoolService.getSchoolById(id);
        Assert.assertNotNull(completeSchool);
    }

    //名称信息相符
    @Test
    public void getById2(){
        String id="123";
        CompleteSchool completeSchool=schoolService.getSchoolById(id);
        Assert.assertEquals("南京大学", completeSchool.getSchoolName());
    }

    //id不存在
    @Test
    public void getById3(){
        String id="000";
        CompleteSchool completeSchool=schoolService.getSchoolById(id);
        Assert.assertEquals(null, completeSchool.getSchoolName());
    }

    @Test
    public void filter(){
        SchoolFilterParam param=new SchoolFilterParam();
        SchoolConditions conditions=new SchoolConditions();
        conditions.setProvinceName("江苏");
        conditions.setF985("1");
        param.setPageNo(1);
        param.setPageSize(20);
        param.setKeyword("南京");
        param.setConditions(conditions);

        List<SimpleSchool> res=schoolService.getSchoolsByConditions(param);
        Assert.assertNotNull(res);
    }

    @Test
    public void filter_count(){
        SchoolFilterParam param=new SchoolFilterParam();
        SchoolConditions conditions=new SchoolConditions();
        conditions.setProvinceName("江苏");
        conditions.setF985("1");
        param.setPageSize(20);
        param.setKeyword("南京");
        param.setConditions(conditions);

        int res=schoolService.countPagesByConditions(param);
        Assert.assertEquals(1,res);
    }


}
