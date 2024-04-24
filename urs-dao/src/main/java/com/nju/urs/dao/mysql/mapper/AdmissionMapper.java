package com.nju.urs.dao.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nju.urs.dao.mysql.model.po.Admission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionMapper extends BaseMapper<Admission> {
    List<Admission> findBySchoolIdAndProvince(String schoolId, String province);

    List<Admission> findByProvince(String province);
}
