package com.yzc.bigdata.competitor;

import com.yzc.bigdata.competitor.repository.CompanyCpRepository;
import com.yzc.bigdata.competitor.repository.PersonRepository;
import com.yzc.bigdata.competitor.repository.ProjectCpRepository;
import com.yzc.bigdata.competitor.script.TimingUpdate;
import com.yzc.bigdata.competitor.service.CompanyService;
import com.yzc.bigdata.competitor.service.EsService;
import com.yzc.bigdata.competitor.service.ProjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @Classname IntialInsertNeo4j
 * @Author lizonghuan
 * @Description 初始化插入Neo4j
 * @Date-Time 2019/7/1-11:05
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IntialInsertNeo4j {

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


    @Test
    public void onSiteInitialInsertNeo4j(){

    }

    @Test
    public void offSiteInitialInsertNeo4j(){

    }

}
