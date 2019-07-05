package com.yzc.bigdata.competitor.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Classname BCCompeteRecordModel
 * @Author lizonghuan
 * @Description 竞争记录
 * @Date-Time 2019/6/19-14:58
 * @Version 1.0
 */
@Getter
@Setter
@ToString
public class BCCompeteRecordModel {
    private String comId; //竞争企业id
    private String comName; //竞争企业名称
    private String bulletinId; //公告id
    private String bulletinName; //公告名称
    private String publishTime; //时间[格式：yyyy-MM-dd HH:mm:ss]
    private String tenderId; //业主id
    private String tenderName; //业主名称
    private String tenderContactUser; //联系人
    private String tenderContactWay; //联系方式
    private String tenderAddress; //地址

}
