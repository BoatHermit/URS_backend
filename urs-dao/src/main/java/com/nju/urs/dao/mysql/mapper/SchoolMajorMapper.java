package com.nju.urs.dao.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nju.urs.dao.mysql.model.po.SchoolMajor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolMajorMapper extends BaseMapper<SchoolMajor> {
    SchoolMajor findById(int id);
    SchoolMajor findByKey(int schoolId, int majorId, int provinceId, int subjectId);
}
