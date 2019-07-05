package com.yzc.bigdata.competitor.dao;

import com.yzc.bigdata.competitor.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2019/7/1.
 */
@Repository
public interface CACompanyMapper {

    List<Staff> getStaffInfoByName(@Param("companyName") String companyName);
    List<CAQualifications> getQualificationInfoByName(@Param("companyName") String companyName);
    List<CAGoodRecords> getGoodRecordsInfoByName(@Param("companyName") String companyName);
}
