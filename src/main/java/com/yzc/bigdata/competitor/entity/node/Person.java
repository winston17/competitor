package com.yzc.bigdata.competitor.entity.node;

import com.yzc.bigdata.competitor.entity.base.AbstractEntity;
import lombok.*;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.math.BigDecimal;

/**
 * @Classname Person
 * @Author lizonghuan
 * @Description Person Test
 * @Date-Time 2019/6/11-14:32
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity(label = "Person")
public class Person extends AbstractEntity {

    @Property(name = "name")
    @Index
    private String name;

    @Property(name = "age")
    private int age;

    @Property(name = "nickName")
    @Index
    private String nickName;

    @Property(name = "money")
    private String money;

    private int count;

    public Person(String name, int age, String nickName){
        this.name = name;
        this.age = age;
        this.nickName = nickName;
    }
    public Person(String name, String money){
        this.name = name;
        this.money = money;
    }


}
