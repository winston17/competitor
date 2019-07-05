package com.yzc.bigdata.competitor.entity.relationship;

import com.yzc.bigdata.competitor.entity.base.AbstractEntity;
import lombok.*;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;

import java.math.BigDecimal;

/**
 * @Classname Submit
 * @Author lizonghuan
 * @Description 报名报价中标
 * @Date-Time 2019/6/11-9:22
 * @Version 1.0
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RelationshipEntity(type = "Submit")
public class Submit extends AbstractEntity {

    @Property(name = "dealStatus")
    private Integer dealStatus;

    @Property(name = "dealMoney")
    private String dealMoney;

    @Property(name = "dealTime")
    private String dealTime;

    @Property(name = "contactUserName")
    private String contactUserName;

    @Property(name = "contactUserPhone")
    private String contactUserPhone;



}
