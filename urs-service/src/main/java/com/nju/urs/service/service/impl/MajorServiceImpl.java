package com.nju.urs.service.service.impl;

import com.nju.urs.common.utils.QueryUtils;
import com.nju.urs.dao.mongo.mapper.MajorInfoMapper;
import com.nju.urs.dao.mongo.mapper.MajorMapper;
import com.nju.urs.dao.mongo.model.po.Major;
import com.nju.urs.service.model.param.MajorFilterParam;
import com.nju.urs.service.model.param.MajorSearchParam;
import com.nju.urs.service.model.vo.CompleteMajor;
import com.nju.urs.service.model.vo.MajorConditions;
import com.nju.urs.service.model.vo.SimpleMajor;
import com.nju.urs.service.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MajorServiceImpl implements MajorService {
    MajorMapper majorMapper;
    MajorInfoMapper majorInfoMapper;

    @Autowired
    public MajorServiceImpl(MajorMapper majorMapper, MajorInfoMapper majorInfoMapper) {
        this.majorMapper = majorMapper;
        this.majorInfoMapper = majorInfoMapper;
    }

    @Override
    public CompleteMajor getMajorById(String id) {
        return null;
    }

    @Override
    public List<SimpleMajor> getMajorsByKeyword(MajorSearchParam param) {
        String regex = QueryUtils.getFuzzyRegex(param.getKeyword());
        Pageable pageable = PageRequest.of(param.getPageNo()-1, param.getPageSize());

        List<Major> majors = majorMapper.findByKeyWord(regex, pageable);
        List<SimpleMajor> simpleMajors = new ArrayList<>();
        for (Major major : majors) {
            SimpleMajor simpleMajor = new SimpleMajor(major);
            simpleMajors.add(simpleMajor);
        }

        return simpleMajors;
    }

    @Override
    public Integer countPagesByKeyword(MajorSearchParam param) {
        String regex = QueryUtils.getFuzzyRegex(param.getKeyword());
        double num = majorMapper.countByKeyword(regex);

        return (int) Math.ceil(num / param.getPageSize());
    }

    private Major wrapConditions(MajorConditions conditions) {
        Major major = new Major();
        major.setLevel1Name(conditions.getLevel1Name());
        major.setLevel2Name(conditions.getLevel2Name());
        major.setLevel3Name(conditions.getLevel3Name());
        return major;
    }

    @Override
    public List<SimpleMajor> getMajorsByConditions(MajorFilterParam param) {
        Major queryMajor = wrapConditions(param.getConditions());
        List<Major> majors = majorMapper.findByConditions(
                param.getPageNo(),param.getPageSize(), queryMajor, param.getKeyword());
        List<SimpleMajor> simpleMajors = new ArrayList<>();
        for (Major major : majors) {
            SimpleMajor simpleMajor = new SimpleMajor(major);
            simpleMajors.add(simpleMajor);
        }
        return simpleMajors;
    }

    @Override
    public Integer countPagesByConditions(MajorFilterParam param) {
        Major queryMajor = wrapConditions(param.getConditions());
        double num = majorMapper.countByConditions(queryMajor, param.getKeyword());
        return (int) Math.ceil(num / param.getPageSize());
    }
}
