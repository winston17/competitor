package com.yzc.bigdata.competitor.model;

        import lombok.Getter;
        import lombok.Setter;
        import lombok.ToString;

/**
 * @Classname BCSimpleComInfoDto
 * @Author lizonghuan
 * @Description 返回竞争或合作公司信息公司对象信息
 * @Date-Time 2019/6/19-14:25
 * @Version 1.0
 */

@Getter
@Setter
@ToString
public class BCSimpleComInfoDto {

    private String comId;

    private String comName;

    private int count; //竞争次数

    public BCSimpleComInfoDto(String comId, String comName){
        this.comId = comId;
        this.comName = comName;
    }
}
