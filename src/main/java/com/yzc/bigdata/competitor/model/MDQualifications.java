package com.yzc.bigdata.competitor.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 企业资质
 * Created by Administrator on 2019/7/1.
 */
@Getter
@Setter
@ToString
public class MDQualifications {

    public String companyId;
    public String companyName;
    public String region;
    public String gCQualificatio;
    public String pCQualificatio;

}
