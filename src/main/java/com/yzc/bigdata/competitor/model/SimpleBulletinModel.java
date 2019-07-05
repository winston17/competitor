package com.yzc.bigdata.competitor.model;

import lombok.*;

/**
 * @Classname SimpleBulletinModel
 * @Author lizonghuan
 * @Description 公告简单信息
 * @Date-Time 2019/6/17-18:03
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SimpleBulletinModel {

    private String bulletinId;

    private String bulletinName;

    private String publishTime;
}
