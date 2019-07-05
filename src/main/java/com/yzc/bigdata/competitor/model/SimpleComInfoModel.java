package com.yzc.bigdata.competitor.model;

import lombok.*;

/**
 * @Classname SimpleComInfoModel
 * @Author lizonghuan
 * @Description 公司简单信息
 * @Date-Time 2019/6/17-18:00
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SimpleComInfoModel {

    private String comId;

    private String comName;

    private int count;

    public SimpleComInfoModel(String comId, String comName){
        this.comId = comId;
        this.comName = comName;
    }
}
