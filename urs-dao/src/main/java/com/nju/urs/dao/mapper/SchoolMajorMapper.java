package com.nju.urs.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nju.urs.dao.model.po.SchoolMajor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolMajorMapper extends BaseMapper<SchoolMajor> {
    SchoolMajor selectBySchoolIdAndMajorId(int schoolId, int majorId);
}
