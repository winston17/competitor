package com.yzc.bigdata.competitor;

import com.yzc.bigdata.competitor.model.InsertModel;
import com.yzc.bigdata.competitor.repository.CompanyCpRepository;
import com.yzc.bigdata.competitor.repository.PersonRepository;
import com.yzc.bigdata.competitor.repository.ProjectCpRepository;
import com.yzc.bigdata.competitor.script.TimingUpdate;
import com.yzc.bigdata.competitor.service.CompanyService;
import com.yzc.bigdata.competitor.service.EsService;
import com.yzc.bigdata.competitor.service.ProjectService;
import com.yzc.bigdata.competitor.util.JdbcUtil;
import com.yzc.bigdata.competitor.util.TextUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @Classname IntialInsertNeo4j
 * @Author lizonghuan
 * @Description 初始化插入Neo4j
 * @Date-Time 2019/7/1-11:05
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InitialInsertNeo4j {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    TimingUpdate timingUpdate;

    @Autowired
    CompanyCpRepository companyCpRepository;

    @Autowired
    CompanyService companyService;

    @Autowired
    ProjectCpRepository projectCpRepository;

    @Autowired
    ProjectService projectService;

    @Autowired
    EsService esService;

    //时间格式
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void insertOnSiteLastUpdateTime(){
        Date date = new Date();
        String currentTime = sdf.format(date);
        currentTime = TextUtil.delayDateDays(currentTime, 1);
        System.out.println(currentTime);
        Connection conn = JdbcUtil.getConectionYouZC_DW();
        try{
            timingUpdate.insertLastUpdateTime(currentTime, "onSite", conn);
            conn.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void onSiteInitialInsertNeo4j(){
        Date date;
        //将当前时间写入文件，之后每次都是读取这个文件的最后一条作为更新截断时间
        String currentTime, onSiteLastUpdateTime, offSiteLastUpdateTime;

        //判断neo4j是否已经启动
        boolean neo4jStart = companyService.neo4jStart();

        Connection conn = JdbcUtil.getConectionYouZC_DW();
        try{
            //如果neo4j启动
            if (neo4jStart){
                System.out.println("neo4j启动");
                //获得当前时间
                date = new Date();
                currentTime = sdf.format(date);
                //从头更新站外数据
                timingUpdate.onSiteUpdateImportInsertModel("2000", conn);
                //站外公告完成之后, 将当前时间存下来
                timingUpdate.insertLastUpdateTime(currentTime, "onSite", conn);
            }
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void offSiteInitialInsertNeo4j(){

        Date date;
        //将当前时间写入文件，之后每次都是读取这个文件的最后一条作为更新截断时间
        String currentTime, onSiteLastUpdateTime, offSiteLastUpdateTime;

        //判断neo4j是否已经启动
        boolean neo4jStart = companyService.neo4jStart();

        Connection conn = JdbcUtil.getConectionYouZC_DW();
        try{
            //如果neo4j启动
            if (neo4jStart){
                System.out.println("neo4j启动");
                //获得当前时间
                date = new Date();
                currentTime = sdf.format(date);
                //从头更新站外数据
                timingUpdate.offSiteUpdateImportPatch("2000");
                //站外公告完成之后, 将当前时间存下来
                timingUpdate.insertLastUpdateTime(currentTime, "offSite", conn);
            }
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
