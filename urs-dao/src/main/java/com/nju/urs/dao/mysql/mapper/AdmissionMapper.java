package com.nju.urs.dao.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nju.urs.dao.mysql.model.po.Admission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionMapper extends BaseMapper<Admission> {
    List<Admission> findBySchoolIdAndProvinceId(Integer schoolId, Integer province);

    List<Admission> findByProvinceId(Integer provinceId);
}
