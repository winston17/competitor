package com.yzc.bigdata.competitor;

import com.yzc.bigdata.competitor.controller.BCCompanyBehaviorController;
import com.yzc.bigdata.competitor.model.BCComNameLikeModel;
import com.yzc.bigdata.competitor.model.BCQueryBidRelationDto;
import com.yzc.bigdata.competitor.model.ComCompeteModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Classname TestController
 * @Author lizonghuan
 * @Description 测试Controller层方法
 * @Date-Time 2019/7/3-18:59
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestController {
    @Autowired
    BCCompanyBehaviorController bcController;

    @Test
    public void testRemoveQuotation(){
        String a = "\"sas''df";
        System.out.println(a);
        System.out.println(bcController.removeQuotation(a));
    }

    @Test
    public void testGetCompete(){
        String a = "31BCD188-5FEC-48E3-9725-36DBD1428A46";
        System.out.println(bcController.getCompete(a));
    }

    @Test
    public void testGetBidRelation(){
//        BCQueryBidRelationDto dto = new BCQueryBidRelationDto("829B275F-52D0-470F-AB2F-0694F0F2DFC1",
//                "安徽安联高速公路有限公司", 1);
//        BCQueryBidRelationDto dto2 = new BCQueryBidRelationDto("31BCD188-5FEC-48E3-9725-36DBD1428A46",
//                "", 2);
        BCQueryBidRelationDto dto2 = new BCQueryBidRelationDto("",
                "", 2);

        System.out.println(bcController.getBidRelation(dto2));
    }

    @Test
    public void testGetTendRecord(){
        String comId = "829B275F-52D0-470F-AB2F-0694F0F2DFC1";
        String comId2 = "";

        System.out.println(bcController.getTendRecord(comId));


    }

    @Test
    public void testGetWinRecord(){
        String comId = "8192FF480DF68063B571E92F7F5DEF7C";
        System.out.println(bcController.getWinRecord(comId));
    }

    @Test
    public void testCompeteRecord(){
        String comId1 = "A42A8229980B1D19290FE8D950193484";
        String comId2 = "D04A103D45DB6E169C34ADCC174BD44D";
        ComCompeteModel comCompeteModel = new ComCompeteModel(comId1, comId2);
        System.out.println(bcController.getCompeteRecord(comCompeteModel));
    }

    @Test
    public void testGetContactsByComId(){
        String comId = "35132CD00A67F8B1598AE39D113ED88F";
        System.out.println(bcController.getContactsByComId(comId));
    }

    @Test
    public void testGetCompanyInfo(){
        BCComNameLikeModel comNameLikeModel = new BCComNameLikeModel("工业", 1);
        System.out.println(bcController.getCompanyInfo(comNameLikeModel));
    }

    @Test
    public void testGetBCCompanyPerformanceById(){
        String comId = "35132CD00A67F8B1598AE39D113ED88F";
        System.out.println(bcController.getBCCompanyPerformanceById(comId));
    }





}
