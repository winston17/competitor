package com.yzc.bigdata.competitor.entity.node;

import com.yzc.bigdata.competitor.entity.base.AbstractEntity;
import lombok.*;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.math.BigDecimal;

/**
 * @Classname ProjectCp
 * @Author lizonghuan
 * @Description 依法询价项目 竞争对手
 * @Date-Time 2019/6/11-9:40
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity(label = "ProjectCp")
public class ProjectCp extends AbstractEntity {

    //unique
    @Property(name = "projectId")
    @Index
    private String projectId;

    @Property(name = "projectType")
    private Integer projectType;

    @Property(name = "projectName")
    @Index
    private String projectName;


    @Property(name = "bulletinId")
    @Index
    private String bulletinId;

    @Property(name = "bulletinName")
    @Index
    private String bulletinName;


    @Property(name = "expStatus")
    private Integer expStatus;

    @Property(name = "dealStatus")
    private Integer dealStatus;

    @Property(name = "signUpTime")
    private String signUpTime;

    @Property(name = "expTime")
    private String expTime;

    @Property(name = "dealTime")
    private String dealTime;

    @Property(name = "tendWay")
    private Integer tendWay;

    @Property(name = "dealMoney")
    private String dealMoney;

    @Property(name = "sourceType")
    private Integer sourceType;

}
