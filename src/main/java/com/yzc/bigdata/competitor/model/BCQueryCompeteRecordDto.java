package com.yzc.bigdata.competitor.model;

import lombok.*;

/**
 * @Classname BCQueryCompeteRecordDto
 * @Author lizonghuan
 * @Description 分页查询两企业竞争记录输入模型
 * @Date-Time 2019/6/25-9:11
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BCQueryCompeteRecordDto {
    private String comId1;

    private String comId2;

    private Integer pageIndex;

    private Integer pageSize;

    private String order;  //排序规则 字段##规则
}
