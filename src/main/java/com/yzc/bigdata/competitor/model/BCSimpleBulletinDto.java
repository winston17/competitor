package com.yzc.bigdata.competitor.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Classname BCSimpleBulletinDto
 * @Author lizonghuan
 * @Description 基本公告信息类, 企业招标数据, 企业中标数据
 * @Date-Time 2019/6/19-14:43
 * @Version 1.0
 */
@Getter
@Setter
@ToString
public class BCSimpleBulletinDto {//  公告id
    //  公告id
    private String bulletinId;

    //  公告名称
    private String bulletinName;

    //  公告时间[格式 yyyy-MM-dd HH:mm:ss]
    private String publishTime;

    //是否中标
    private int isWin;

    //中标金额
    private BigDecimal money;

    //业主id
    private String tenderId;

    //业主名称
    private String tenderName;

    //联系人
    private String tenderContactUser;

    //联系方式
    private String tenderContactWay;

    //地址
    private String tenderAddress;

    //公告来源[1、站内招标2、站内采购 3、站外]
    private int sourceType;
}
