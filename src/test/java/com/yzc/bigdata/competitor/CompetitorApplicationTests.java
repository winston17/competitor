package com.yzc.bigdata.competitor;

import com.yzc.bigdata.competitor.entity.node.CompanyCp;
import com.yzc.bigdata.competitor.entity.node.Person;
import com.yzc.bigdata.competitor.entity.node.ProjectCp;
import com.yzc.bigdata.competitor.entity.relationship.Invite;
import com.yzc.bigdata.competitor.entity.relationship.Submit;
import com.yzc.bigdata.competitor.model.InsertModel;
import com.yzc.bigdata.competitor.repository.CompanyCpRepository;
import com.yzc.bigdata.competitor.repository.PersonRepository;
import com.yzc.bigdata.competitor.repository.ProjectCpRepository;
import com.yzc.bigdata.competitor.script.TimingUpdate;
import com.yzc.bigdata.competitor.service.CompanyService;
import com.yzc.bigdata.competitor.service.EsService;
import com.yzc.bigdata.competitor.service.ProjectService;
import com.yzc.bigdata.competitor.util.JdbcUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompetitorApplicationTests {

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
	public void offSiteInitialInsertNeo4j(){

	}


	@Test
	public void testTestCql(){
		int a = personRepository.testCql();
		System.out.println(a);
	}

	@Test
	public void testAddPersonNode(){
		String name = "高圆圆";
		Integer age = 24;
		System.out.println("高高高");
		personRepository.addPersonNode(name, age);
		System.out.println("俊俊俊");
	}

	@Test
	public void testAddPersonNodeName(){
		String name = "马武";
		Integer age = 24;
		System.out.println("高圆圆");
		Person person = personRepository.addPersonNode(name);
		System.out.println(person);
		System.out.println("111");
	}

	@Test
	public void testAddPersonNodeName2(){
		String name = "马武";
		Integer age = 24;
		System.out.println("高圆圆");
		List<Person> person = personRepository.addPersonNodeName(name);
		System.out.println(person);
		System.out.println("111");
	}



	@Test
	public void testSetPersonNode(){
		String name = "高俊";
		Integer age = 20;
		Person person = personRepository.setPersonNode(name, age, "书记", 170);
		System.out.println(person);
	}

	@Test
	public void testAddPersonEx(){
		String name = "张召忠";
		List<Person> person = personRepository.addPersonEx(name);
		System.out.println(person);
	}

	@Test
	public void testAddPersonTest(){
		String name = "李磊";
		boolean isSmart = true;
		BigDecimal weight = new BigDecimal("65.3");
		Person person = personRepository.addPersonTest(name, isSmart, weight);
		System.out.println(person);
	}


	@Test
	public void testMatchPersonTest(){
		String name = "李磊";
		List<String> result = personRepository.matchPersonTest(name);
		System.out.println(result.getClass());
		System.out.println(result.get(0).getClass());
		System.out.println(result.get(0));
		System.out.println(result);
	}

	@Test
	public void testMatchPersonMap(){
		String name = "李磊";
		List<Map<String, Object>> result = personRepository.matchPersonMap(name);

		System.out.println(result.get(0).get("id"));
		System.out.println(result.get(0).get("labels"));
		System.out.println(result.get(0).get("labels").getClass());
		System.out.println(result.get(0).get("name"));
		System.out.println(result.get(0).get("age"));

		System.out.println(result);
	}

	@Test
	public void testImport(){
		timingUpdate.updateImport();
	}


	@Test
	public void testSelect(){
		String lastUpdateTime = "2019";
		String selectSQL = "select top 100 t.bidderId, u.subName, t.projectType, t.projectName, t.projectId, t.bulletinId, " +
				"t.expStatus, t.dealStatus, \n" +
				"t.signUpTime, t.expTime, t.dealTime, t.dealMoney, t.tendWay, t.companyName, t.companyId,\n" +
				"t.maxUpdateTime from\n" +
				"(select bidderId, projectType, projectName, projectId, bulletinId, expStatus, dealStatus, \n" +
				"signUpTime, expTime, dealTime, dealMoney, tendWay, companyName, companyId, \n" +
				" ( SELECT    MAX(LastUpdateDate)\n" +
				"          FROM      ( VALUES ( signUpTime), ( expTime),\n" +
				"                    ( dealTime) ) AS lastUpdateTime ( LastUpdateDate )\n" +
				"          ) as maxUpdateTime \n" +
				"from T_TaskBidderRegRecord) t \n" +
				"left join User_BiddingSubInfo u on u.BiddingSubInfoId = t.bidderId \n" +
				"where t.maxUpdateTime > '" + lastUpdateTime + "'";
		try{
			Connection conn = JdbcUtil.getConectionYouZC_DW();
			PreparedStatement stmt = conn.prepareStatement(selectSQL);
			System.out.println("selectSQL " + selectSQL);
			ResultSet rs = stmt.executeQuery();
			CompanyCp companyInviter, companyInviterResult, companySubmitter, companySubmitterResult;
			ProjectCp project, projectResult;
			Invite invite;
			Submit submit;
			while(rs.next()){
				companyInviter = new CompanyCp(rs.getString("companyName"), rs.getString("companyId"));
				companySubmitter = new CompanyCp(rs.getString("subName"), rs.getString("bidderId"));
				System.out.println(rs.getString("dealMoney"));
//				project = new ProjectCp(rs.getString("projectId"),
//						rs.getInt("projectType"),
//						rs.getString("projectName"),
//						rs.getString("bulletinId"),
//						rs.getInt("expStatus"),
//						rs.getInt("dealStatus"),
//						rs.getString("signUpTime"),
//						rs.getString("expTime"),
//						rs.getString("dealTime"),
//						rs.getInt("tendWay"),
//						rs.getString("dealMoney"),
//						projectService.getSourceType(rs.getInt("projectType"))
//				);
//				companyInviterResult = companyCpRepository.addCompanyCpNode(companyInviter.getCompanyNewId(),
//						companyInviter.getCompanyId(), companyInviter.getCompanyName());
//				companySubmitterResult = companyCpRepository.addCompanyCpNode(companySubmitter.getCompanyNewId(),
//						companySubmitter.getCompanyId(), companySubmitter.getCompanyName());
//				projectResult = projectCpRepository.addProjectCpNode(project.getProjectId(), project.getProjectType(),
//						project.getProjectName(), project.getBulletinId(), project.getExpStatus(),
//						project.getDealStatus(), project.getSignUpTime(), project.getExpTime(),
//						project.getDealTime(), project.getTendWay(), project.getDealMoney());
//				invite = projectCpRepository.addInviteRelationship(companyInviterResult.getCompanyNewId(), projectResult.getProjectId());
//				submit = projectCpRepository.addSubmitRelationship(companySubmitterResult.getCompanyNewId(), projectResult.getProjectId(),
//						projectResult.getDealStatus(),
//						projectResult.getDealMoney(),
//						projectResult.getDealTime());
			}
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	@Test
	public void testAddPersonEx2(){
		BigDecimal money = new BigDecimal("1000.88");
		String moneyStr = money.toString();
		Person person = personRepository.addPersonEx2("刘强东", moneyStr);
		System.out.println(person);
	}

	@Test
	public void testSelectProject(){
		Date date = new Date();
		String dateStr = date.toString();
		System.out.println(dateStr);
		dateStr = "2019-06-01";
		List<InsertModel> resultList = esService.selectProject(dateStr);
		System.out.println("selectProject List is : ");
		System.out.println(resultList);


	}


}
