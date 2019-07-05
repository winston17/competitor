package com.yzc.bigdata.competitor.model;

import com.yzc.bigdata.competitor.util.baseUtil.EncryptionUtil;
import lombok.*;

/**
 * @Classname InsertModel
 * @Author lizonghuan
 * @Description 从数据源获取数据并插入到neo4j的InsertModel数据封装
 * @Date-Time 2019/6/20-17:16
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class InsertModel {

    private String inviterNewId;

    private String projectId;

    private String submitterNewId;

    private String inviterId;

    private String inviterName;

    private String submitterId;

    private String submitterName;

    private Integer projectType;

    private String projectName;

    private String bulletinId;

    private String bulletinName;

    private Integer expStatus;

    private Integer dealStatus;

    private String signUpTime;

    private String expTime;

    private String dealTime;

    private Integer tendWay;

    private String dealMoney;

    private Integer sourceType;

    //招标联系人
    private String tenderContacter;
    //招标联系电话
    private String tenderContactPhone;
    //招标公司地址
    private String tenderCompanyAddress;

    //投标联系人
    private String bidderContacter;
    //投标联系电话
    private String bidderContactPhone;
    //投标公司地址
    private String bidderCompanyAddress;


    public InsertModel(String projectId, String inviterId, String inviterName,
                       String submitterId, String submitterName, Integer projectType,
                       String projectName, String bulletinId, String bulletinName, Integer expStatus,
                       Integer dealStatus, String signUpTime, String expTime,
                       String dealTime, Integer tendWay, String dealMoney,
                       Integer sourceType, String tenderContacter, String tenderContactPhone,
                       String tenderCompanyAddress, String bidderContacter,
                       String bidderContactPhone, String bidderCompanyAddress){
        this.inviterNewId = EncryptionUtil.encrypByMD5(inviterName);
        this.projectId = projectId;
        this.submitterNewId = EncryptionUtil.encrypByMD5(submitterName);
        this.inviterId = inviterId;
        this.inviterName = inviterName;
        this.submitterId = submitterId;
        this.submitterName = submitterName;
        this.projectType = projectType;
        this.projectName = projectName;
        this.bulletinId = bulletinId;
        this.bulletinName = bulletinName;
        this.expStatus = expStatus;
        this.dealStatus = dealStatus;
        this.signUpTime = signUpTime;
        this.expTime = expTime;
        this.dealTime = dealTime;
        this.tendWay = tendWay;
        this.dealMoney = dealMoney;
        this.sourceType = sourceType;
        this.tenderContacter = tenderContacter;
        this.tenderContactPhone = tenderContactPhone;
        this.tenderCompanyAddress = tenderCompanyAddress;
        this.bidderContacter = bidderContacter;
        this.bidderContactPhone = bidderContactPhone;
        this.bidderCompanyAddress = bidderCompanyAddress;
    }
}
