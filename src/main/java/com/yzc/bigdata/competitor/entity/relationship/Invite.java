package com.yzc.bigdata.competitor.entity.relationship;

import com.yzc.bigdata.competitor.entity.base.AbstractEntity;
import lombok.*;
import org.neo4j.ogm.annotation.RelationshipEntity;

/**
 * @Classname Invite
 * @Author lizonghuan
 * @Description 发起招标询价
 * @Date-Time 2019/6/11-9:21
 * @Version 1.0
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@RelationshipEntity(type = "Invite")
public class Invite extends AbstractEntity {


}
