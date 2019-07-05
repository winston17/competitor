package com.yzc.bigdata.competitor.model;

import lombok.*;

/**
 * @Classname BCComNameLikeModel
 * @Author lizonghuan
 * @Description 模糊查询公司名
 * @Date-Time 2019/7/3-17:50
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BCComNameLikeModel {

    private String comNameLike; //公司名模糊查询

    private int type; //[1、投标人2、业主&代理机构]

}
