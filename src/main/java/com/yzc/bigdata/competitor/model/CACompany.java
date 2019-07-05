package com.yzc.bigdata.competitor.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 企业搜索影响
 * Created by Administrator on 2019/7/1.
 */
@Getter
@Setter
public class CACompany {

    public String companyId;

    public String companyName ;

    public String region ;

    public String industry ;

    public String legalMan ;

    public String registeredCapital ;

    public String establishedTime ;

    public String uSCCode ;

    public String businessTypes ;

    public String businessScope ;

    public String sourceUrl ;

    public List<Staff> staffs ;

    public List<Credit> credits ;

    public List<CAQualifications> qualifications ;
    public List<CAGoodRecords>  goodRecords ;
    public List<CABadRecords> badRecords ;

}
