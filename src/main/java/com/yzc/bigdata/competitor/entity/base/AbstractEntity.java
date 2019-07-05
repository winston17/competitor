package com.yzc.bigdata.competitor.entity.base;

/**
 * @Classname AbstractEntity
 * @Author lizonghuan
 * @Description 还有id和hashcode equals, nodeEntity类可以从此类继承
 * @Date-Time 2019/5/27-14:48
 * @Version 1.0
 */
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;

/**
 * The abstract entity which contains the basic fields such
 * as id, hashCode and equals function
 */
@Getter
@Setter
public abstract class AbstractEntity {

    @Id
    @GeneratedValue
    private Long id;

    //最后更新时间
    @Property(name = "lastUpdateTime")
    private String lastUpdateTime;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        if (this.id == null) {
            // For newly created entity, id will be null
            return false;
        }

        AbstractEntity entity = (AbstractEntity) obj;
        return this.id.equals(entity.id);
    }

    @Override
    public int hashCode() {
        return id == null ? super.hashCode() : id.hashCode();
    }
}
