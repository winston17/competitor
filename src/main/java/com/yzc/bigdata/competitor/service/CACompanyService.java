package com.yzc.bigdata.competitor.service;

import com.yzc.bigdata.competitor.dao.CACompanyMapper;
import com.yzc.bigdata.competitor.model.CACompany;
import com.yzc.bigdata.competitor.model.PageQueryModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2019/7/1.
 */
@Service
public class CACompanyService {
    @Resource
    CACompanyMapper mapper;

    public CACompany getDetial(String companyName){
        CACompany caCompany = new CACompany();
        caCompany.setGoodRecords(mapper.getGoodRecordsInfoByName(companyName));
        caCompany.setStaffs(mapper.getStaffInfoByName(companyName));
        caCompany.setQualifications(mapper.getQualificationInfoByName(companyName));
        return  caCompany;
    }

}
