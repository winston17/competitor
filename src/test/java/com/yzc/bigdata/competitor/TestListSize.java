package com.yzc.bigdata.competitor;

import com.yzc.bigdata.competitor.model.InsertModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Classname TestListSize
 * @Author lizonghuan
 * @Description 测试List大小
 * @Date-Time 2019/7/1-11:14
 * @Version 1.0
 */

public class TestListSize {
    public static void main(String[] args) {
        String a = UUID.randomUUID().toString();
        System.out.println(a);
//        List<InsertModel> insertModelList = generateInsertModelList();
//        int i = 50000;
//        for(InsertModel insertModel1 : insertModelList){
//            System.out.println(i++);
//            System.out.println(insertModel1);
//        }
    }

    public static List<InsertModel> generateInsertModelList(){
        List<InsertModel> insertModelList = new ArrayList<>();
        InsertModel insertModel;
        int i = 0;
        for(i = 0; i < 5000000; i++){
            System.out.println(i);
            insertModel = new InsertModel("652CE742DE0698D46DDB394ADFF75ECD",
                    "652CE742DE0698D46DDB394ADFF75ECD",
                    "中国机械进出口（集团）有限公司",
                    "7A4D82EB1F28D0A14F6247DBEB07A244",
                    "北京铭成嘉业科技发展有限公司",
                    7,
                    "东直门医院中西医结合精准外科综合诊疗平台建设第一批医疗设备采购项目",
                    "A9AF51AA6119F55EA7F27C309DCFFF00",
                    null,
                    null,
                    1,
                    "2019-06-24",
                    null,
                    "2019-06-24",
                    1,
                    "10.0",
                    3,
                    "鸠摩智",
                    "0551-62221123",
                    "安徽省合肥市包河区包河大道236号",
                    "鸠摩智",
                    "0551-62221123",
                    "安徽省合肥市包河区包河大道236号");
            insertModelList.add(insertModel);
        }
        return insertModelList;
    }



}
