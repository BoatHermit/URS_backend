package com.nju.urs.dao.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nju.urs.dao.mysql.model.po.SchoolMajor;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolMajorMapper extends BaseMapper<SchoolMajor> {
    SchoolMajor findBySchoolIdAndMajorId(int schoolId, int majorId);
}
