package com.yzc.bigdata.competitor.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 不良记录
 * Created by Administrator on 2019/7/1.
 */
@Getter
@Setter
public class CABadRecords {

    public String recordType ;
    public String recordSource ;
    public String recordContent ;
    public String recordDept ;
    public String recordDesc;
}
