package com.yzc.bigdata.competitor.model;

import lombok.*;

/**
 * @Classname BCQueryBidRelationDto
 * @Author lizonghuan
 * @Description 企业上下游关系查询输入参数
 * @Date-Time 2019/6/19-14:33
 * @Version 1.0
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BCQueryBidRelationDto {
    private String comId; //企业id

    private String companyName; //企业名称

    private int type; //[1、下游企业 2、上游企业]
}
