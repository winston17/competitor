package com.yzc.bigdata.competitor.dao;

import com.yzc.bigdata.competitor.model.MDQualifications;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/7/1.
 */
@Repository
public interface MDQualificationsMapper {

 MDQualifications getAllMDQualifications(@Param("companyName")String companyName);

}
