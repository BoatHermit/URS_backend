package com.nju.urs.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nju.urs.dao.model.po.Admission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionMapper extends BaseMapper<Admission> {

    List<Admission> selectByProvince(String province);
}
