package com.yzc.bigdata.competitor.entity.node;

import com.yzc.bigdata.competitor.entity.base.AbstractEntity;
import com.yzc.bigdata.competitor.util.baseUtil.EncryptionUtil;
import lombok.*;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * @Classname CompanyCp
 * @Author lizonghuan
 * @Description 公司机构 竞争对手
 * @Date-Time 2019/6/11-9:39
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity(label = "CompanyCp")
public class CompanyCp extends AbstractEntity {

    //unique
    @Property(name = "companyNewId")
    @Index
    private String companyNewId;

    @Property(name = "companyName")
    @Index
    private String companyName;

    @Property(name = "companyId")
    @Index
    private String companyId;

    @Property(name = "contacter")
    private String contacter;

    @Property(name = "contactPhone")
    private String contactPhone;

    @Property(name = "companyAddress")
    private String companyAddress;

    public CompanyCp(String companyName, String companyId, String contacter, String contactPhone, String companyAddress){
        this.companyName = companyName;
        this.companyId = companyId;
        this.companyNewId = EncryptionUtil.encrypByMD5(companyName);
        this.contacter = contacter;
        this.contactPhone = contactPhone;
        this.companyAddress = companyAddress;
    }

    public CompanyCp(String companyName, String companyId){
        this.companyName = companyName;
        this.companyId = companyId;
        this.companyNewId = EncryptionUtil.encrypByMD5(companyName);
    }




}
