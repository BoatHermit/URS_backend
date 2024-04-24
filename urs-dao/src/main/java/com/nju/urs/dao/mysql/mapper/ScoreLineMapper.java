package com.nju.urs.dao.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nju.urs.dao.mysql.model.po.ScoreLine;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreLineMapper extends BaseMapper<ScoreLine> {
    List<ScoreLine> findBySchoolIdAndProvinceNameAndSubject(String schoolId, String province, int subject);
    List<ScoreLine> findBySchoolId(String schoolId);
}
