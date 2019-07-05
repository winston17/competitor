package com.yzc.bigdata.competitor.service;

import com.yzc.bigdata.competitor.dao.MDQualificationsMapper;
import com.yzc.bigdata.competitor.model.MDQualifications;
import com.yzc.bigdata.competitor.model.PageQueryModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2019/7/1.
 */
@Service
public class MDQualificationsService {

    @Resource
    MDQualificationsMapper mdQualificationsMapper;
    /***
     * 分页查询
     * @param
     * @param
     * @return
     */
    public MDQualifications getAllMDQualifications(String companyName){

        MDQualifications allMDQualifications = mdQualificationsMapper.getAllMDQualifications(companyName);

        return  allMDQualifications;
    }

}


