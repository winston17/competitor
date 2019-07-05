package com.yzc.bigdata.competitor.model;

import lombok.*;

/**
 * @Classname BCCompanyContactModel
 * @Author lizonghuan
 * @Description 企业联系方式
 * @Date-Time 2019/6/20-16:09
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BCCompanyContactModel {
    private String contactUser; //联系人名称

    private String contactWay; //联系方式

    private String address; //联系地址
}
