package com.yzc.bigdata.competitor.repository;

import com.yzc.bigdata.competitor.entity.node.ProjectCp;
import com.yzc.bigdata.competitor.entity.relationship.Invite;
import com.yzc.bigdata.competitor.entity.relationship.Submit;
import com.yzc.bigdata.competitor.model.BCSimpleBulletinDto;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public interface ProjectCpRepository extends Neo4jRepository<ProjectCp, Long> {
    //创建

//    //创建项目
//    @Query("MERGE (n:ProjectCp{projectType: {projectType}, projectName: {projectName}, projectId: {projectId}, bulletinId: {bulletinId}, " +
//            "expStatus: {expStatus}, dealStatus: {dealStatus}, signUpTime: {signUpTime}, expTime: {expTime}, " +
//            "dealTime: {dealTime}, tendWay: {tendWay}}) " +
//            "SET n.signUpTime = {signUpTime} n.expTime = {expTime} n.dealTime = {dealTime} RETURN n")
//    ProjectCp addProjectCpNode(@Param("projectType") int projectType,
//                                  @Param("projectName") String projectName,
//                                  @Param("projectId") String projectId,
//                               @Param("bulletinId") String bulletinId,
//                               @Param("expStatus") Integer expStatus,
//                               @Param("dealStatus") Integer dealStatus,
//                               @Param("signUpTime") String signUpTime,
//                               @Param("expTime") String expTime,
//                               @Param("dealTime") String dealTime,
//                               @Param("tendWay") Integer tendWay);

//    创建项目
    @Query("MERGE (n:ProjectCp{projectId: {projectId}}) " +
            "SET n.projectType = coalesce(projectType}, n.projectType), " +
            "n.projectName = coalesce({projectName}, n.projectName), " +
            "n.bulletinId = coalesce({bulletinId}, n.bulletinId), " +

            "n.expStatus = coalesce({expStatus}, n.expStatus), " +
            "n.dealStatus = coalesce({dealStatus}, n.dealStatus), " +
            "n.signUpTime = coalesce({signUpTime}, n.signUpTime), " +
            "n.expTime = coalesce({expTime}, n.expTime), " +
            "n.dealTime = coalesce({dealTime}, n.dealTime), " +
            "n.tendWay = coalesce({tendWay}, n.tendWay), " +
            "n.dealMoney = coalesce({dealMoney}, n.dealMoney) " +
            "RETURN n")
    ProjectCp addProjectCpNode(@Param("projectId") String projectId,
                               @Param("projectType") Integer projectType,
                               @Param("projectName") String projectName,
                               @Param("bulletinId") String bulletinId,
                               @Param("expStatus") Integer expStatus,
                               @Param("dealStatus") Integer dealStatus,
                               @Param("signUpTime") String signUpTime,
                               @Param("expTime") String expTime,
                               @Param("dealTime") String dealTime,
                               @Param("tendWay") Integer tendWay,
                               @Param("dealMoney") String dealMoney);


    //创建发布项目关系
    @Query("MERGE (n:CompanyCp{companyNewId: {companyNewId}}) " +
            "MERGE (m:ProjectCp{projectId: {projectId}}) " +
            "MERGE (n)-[r:Invite]->(m) " +
            "RETURN r")
    Invite addInviteRelationship(@Param("companyNewId") String companyNewId, @Param("projectId") String projectId);



    //创建报名报价项目关系
    @Query("MERGE (n:CompanyCp{companyNewId: {companyNewId}}) " +
            "MERGE (m:ProjectCp{projectId: {projectId}}) " +
            "MERGE (n)-[r:Submit{dealStatus: {dealStatus}, dealMoney: {dealMoney}, dealTime: {dealTime}}]->(m) " +
            "RETURN r")

    Submit addSubmitRelationship(@Param("companyNewId") String companyNewId,
                                 @Param("projectId") String projectId,
                                 @Param("dealStatus") Integer dealStatus,
                                 @Param("dealMoney") String dealMoney,
                                 @Param("dealTime") String dealTime);

//    //站内一次性插入
//    @Query("MERGE (n:CompanyCp{companyNewId: {companyNewId}}) " +
//            "SET n.companyId = coalesce({companyId}, n.companyId), " +
//            "n.companyName = coalesce({companyName}, n.companyName) " +
//            "MERGE (m:CompanyCp{companyNewId: {bidderCompanyNewId}}) " +
//            "SET m.companyId = coalesce({bidderCompanyId}, m.companyId), " +
//            "m.companyName = coalesce({bidderCompanyName}, m.companyName) " +
//            "")

    //站内一次性插入
    @Query("MERGE (n:CompanyCp{companyNewId: {inviterCompanyNewId}}) " +
            "MERGE (p:ProjectCp{projectId: {projectId}}) " +
            "MERGE (m:CompanyCp{companyNewId: {submitterCompanyNewId}}) " +
            "WITH n, p, m " +
            "MERGE (n) -[r1:Invite]-> (p) " +
            "MERGE (m) -[r2:Submit]-> (p) " +
            "SET n.companyId = coalesce({inviterCompanyId}, n.companyId), " +
            "n.companyName = coalesce({inviterCompanyName}, n.companyName), " +
            "n.contacter = coalesce({inviterContacter}, n.contacter), " +
            "n.contactPhone = coalesce({inviterContactPhone}, n.contactPhone), " +
            "n.companyAddress = coalesce({inviterCompanyAddress}, n.companyAddress), " +
            "m.companyId = coalesce({submitterCompanyId}, m.companyId), " +
            "m.companyName = coalesce({submitterCompanyName}, m.companyName), " +
            "m.contacter = coalesce({submitterContacter}, m.contacter), " +
            "m.contactPhone = coalesce({submitterContactPhone}, m.contactPhone), " +
            "m.companyAddress = coalesce({submitterCompanyAddress}, m.companyAddress), " +
            "p.projectType = coalesce({projectType}, p.projectType), " +
            "p.projectName = coalesce({projectName}, p.projectName), " +
            "p.bulletinId = coalesce({bulletinId}, p.bulletinId), " +
            "p.bulletinName = coalesce({bulletinName}, p.bulletinName), " +
            "p.expStatus = coalesce({expStatus}, p.expStatus), " +
            "p.dealStatus = coalesce({dealStatus}, p.dealStatus), " +
            "p.signUpTime = coalesce({signUpTime}, p.signUpTime), " +
            "p.expTime = coalesce({expTime}, p.expTime), " +
            "p.dealTime = coalesce({dealTime}, p.dealTime), " +
            "p.tendWay = coalesce({tendWay}, p.tendWay), " +
            "p.dealMoney = coalesce({dealMoney}, p.dealMoney), " +
            "p.sourceType = coalesce({sourceType}, p.sourceType), " +
            "r2.dealStatus = coalesce({dealStatus}, r2.dealStatus), " +
            "r2.dealMoney = coalesce({dealMoney}, r2.dealMoney), " +
            "r2.dealTime = coalesce({dealTime}, r2.dealTime) " +
            "RETURN 1")
    int addCompanyAndProject(@Param("inviterCompanyNewId") String inviterCompanyNewId,
                             @Param("projectId") String projectId,
                             @Param("submitterCompanyNewId") String submitterCompanyNewId,
                             @Param("inviterCompanyId") String inviterCompanyId,
                             @Param("inviterCompanyName") String inviterCompanyName,
                             @Param("submitterCompanyId") String submitterCompanyId,
                             @Param("submitterCompanyName") String submitterCompanyName,
                             @Param("projectType") Integer projectType,
                             @Param("projectName") String projectName,
                             @Param("bulletinId") String bulletinId,
                             @Param("bulletinName") String bulletinName,
                             @Param("expStatus") Integer expStatus,
                             @Param("dealStatus") Integer dealStatus,
                             @Param("signUpTime") String signUpTime,
                             @Param("expTime") String expTime,
                             @Param("dealTime") String dealTime,
                             @Param("tendWay") Integer tendWay,
                             @Param("dealMoney") String dealMoney,
                             @Param("inviterContacter") String tenderContacter,
                             @Param("inviterContactPhone") String tenderContactPhone,
                             @Param("inviterCompanyAddress") String tenderCompanyAddress,
                             @Param("submitterContacter") String bidderContacter,
                             @Param("submitterContactPhone") String bidderContactPhone,
                             @Param("submitterCompanyAddress") String bidderCompanyAddress,
                             @Param("sourceType") Integer sourceType);


    //站外一次性插入 站外与站内的不同是在公司id上面，如果原来公司id为空，才把新的公司id添加到里面
    @Query("MERGE (n:CompanyCp{companyNewId: {inviterCompanyNewId}}) " +
            "MERGE (p:ProjectCp{projectId: {projectId}}) " +
            "MERGE (m:CompanyCp{companyNewId: {submitterCompanyNewId}}) " +
            "WITH n, p, m " +
            "MERGE (n) -[r1:Invite]-> (p) " +
            "MERGE (m) -[r2:Submit]-> (p) " +
            "SET n.companyId = coalesce(n.companyId, {inviterCompanyId}), " +
            "n.companyName = coalesce(n.companyName, {inviterCompanyName}), " +
            "n.contacter = coalesce({inviterContacter}, n.contacter), " +
            "n.contactPhone = coalesce({inviterContactPhone}, n.contactPhone), " +
            "n.companyAddress = coalesce({inviterCompanyAddress}, n.companyAddress), " +
            "m.companyId = coalesce(m.companyId, {submitterCompanyId}), " +
            "m.companyName = coalesce(m.companyName, {submitterCompanyName}), " +
            "m.contacter = coalesce({submitterContacter}, m.contacter), " +
            "m.contactPhone = coalesce({submitterContactPhone}, m.contactPhone), " +
            "m.companyAddress = coalesce({submitterCompanyAddress}, m.companyAddress), " +
            "p.projectType = coalesce({projectType}, p.projectType), " +
            "p.projectName = coalesce({projectName}, p.projectName), " +
            "p.bulletinId = coalesce({bulletinId}, p.bulletinId), " +
            "p.bulletinName = coalesce({bulletinName}, p.bulletinName), " +
            "p.expStatus = coalesce({expStatus}, p.expStatus), " +
            "p.dealStatus = coalesce({dealStatus}, p.dealStatus), " +
            "p.signUpTime = coalesce({signUpTime}, p.signUpTime), " +
            "p.expTime = coalesce({expTime}, p.expTime), " +
            "p.dealTime = coalesce({dealTime}, p.dealTime), " +
            "p.tendWay = coalesce({tendWay}, p.tendWay), " +
            "p.dealMoney = coalesce({dealMoney}, p.dealMoney), " +
            "p.sourceType = coalesce({sourceType}, p.sourceType), " +
            "r2.dealStatus = coalesce({dealStatus}, r2.dealStatus), " +
            "r2.dealMoney = coalesce({dealMoney}, r2.dealMoney), " +
            "r2.dealTime = coalesce({dealTime}, r2.dealTime) " +
            "RETURN 1")
    int addCompanyAndProjectOffSite(@Param("inviterCompanyNewId") String inviterCompanyNewId,
                             @Param("projectId") String projectId,
                             @Param("submitterCompanyNewId") String submitterCompanyNewId,
                             @Param("inviterCompanyId") String inviterCompanyId,
                             @Param("inviterCompanyName") String inviterCompanyName,
                             @Param("submitterCompanyId") String submitterCompanyId,
                             @Param("submitterCompanyName") String submitterCompanyName,
                             @Param("projectType") Integer projectType,
                             @Param("projectName") String projectName,
                             @Param("bulletinId") String bulletinId,
                             @Param("bulletinName") String bulletinName,
                             @Param("expStatus") Integer expStatus,
                             @Param("dealStatus") Integer dealStatus,
                             @Param("signUpTime") String signUpTime,
                             @Param("expTime") String expTime,
                             @Param("dealTime") String dealTime,
                             @Param("tendWay") Integer tendWay,
                             @Param("dealMoney") String dealMoney,
                             @Param("inviterContacter") String tenderContacter,
                             @Param("inviterContactPhone") String tenderContactPhone,
                             @Param("inviterCompanyAddress") String tenderCompanyAddress,
                             @Param("submitterContacter") String bidderContacter,
                             @Param("submitterContactPhone") String bidderContactPhone,
                             @Param("submitterCompanyAddress") String bidderCompanyAddress,
                             @Param("sourceType") Integer sourceType);




    //根据companyName, 查询企业招标询价项目
    @Query("MATCH (n:CompanyCp{companyName: {companyName}})-[r:Invite]->(m:ProjectCp) RETURN m")
    List<ProjectCp> getCompanyInviteProjectsByCompanyName(@Param("companyName") String companyName);

    //根据companyId, 查询企业招标询价项目
    @Query("MATCH (n:CompanyCp{companyId: {companyId}})-[r:Invite]->(m:ProjectCp) RETURN m")
    List<ProjectCp> getCompanyInviteProjectsByCompanyId(@Param("companyId") String companyId);

    //根据companyNewId, 查询企业招标询价项目
    @Query("MATCH (n:CompanyCp{companyNewId: {companyNewId}})-[r:Invite]->(m:ProjectCp) RETURN m")
    List<ProjectCp> getCompanyInviteProjectsByCompanyNewId(@Param("companyNewId") String companyNewId);


    //根据companyName, 查询企业报名报价项目
    @Query("MATCH (n:CompanyCp{companyName: {companyName}})-[r:Submit]->(m:ProjectCp) RETURN m")
    List<ProjectCp> getCompanySubmitProjectsByCompanyName(@Param("companyName") String companyName);

    //根据companyId, 查询企业报名报价项目
    @Query("MATCH (n:CompanyCp{companyId: {companyId}})-[r:Submit]->(m:ProjectCp) RETURN m")
    List<ProjectCp> getCompanySubmitProjectsByCompanyId(@Param("companyId") String companyId);

    //根据companyNewId, 查询企业报名报价项目
    @Query("MATCH (n:CompanyCp{companyNewId: {companyNewId}})-[r:Submit]->(m:ProjectCp) RETURN m")
    List<ProjectCp> getCompanySubmitProjectsByCompanyNewId(@Param("companyNewId") String companyNewId);


    //根据companyName, 查询企业中标项目
    @Query("MATCH (n:CompanyCp{companyName: {companyName}})-[r:Submit{dealStatus: true}]->(m:ProjectCp) RETURN m")
    List<ProjectCp> getCompanyWinProjectsByCompanyName(@Param("companyName") String companyName);

    //根据companyId, 查询企业中标项目
    @Query("MATCH (n:CompanyCp{companyId: {companyId}})-[r:Submit{dealStatus: 1}]->(m:ProjectCp) RETURN m")
    List<ProjectCp> getCompanyWinProjectsByCompanyId(@Param("companyId") String companyId);

    //根据companyNewId, 查询企业中标项目
    @Query("MATCH (n:CompanyCp{companyNewId: {companyNewId}})-[r:Submit{dealStatus: 1}]->(m:ProjectCp) RETURN m")
    List<ProjectCp> getCompanyWinProjectsByCompanyNewId(@Param("companyNewId") String companyNewId);


    //根据公司id, 查询发布的项目BCSimpleBulletinDto
    @Query("MATCH (n:CompanyCp{companyId: {companyId}})-[r:Invite]->(p:ProjectCp) RETURN " +
            "p.bulletinId AS bulletinId, " +
            "p.bulletinName AS bulletinName, " +
            "p.signUpTime AS publishTime, " +
            "p.dealStatus AS isWin, " +
            "p.dealMoney AS money, " +
            "n.companyId AS tenderId, " +
            "n.companyName AS tenderName, " +
            "n.contacter AS tenderContactUser, " +
            "n.contactPhone AS tenderContactWay, " +
            "n.companyAddress AS tenderAddress, " +
            "p.sourceType AS sourceType")
    List<Map<String, Object>> getCompanyInviteProjectsByCompanyId_BC(@Param("companyId") String companyId);

    //根据公司id, 查询中标的项目BCSimpleBulletinDto
    @Query("MATCH (n:CompanyCp{companyId: {companyId}})-[r1:Submit{dealStatus: 1}]->(p:ProjectCp)" +
            "<-[r2:Invite]-(m:CompanyCp) RETURN " +
            "p.bulletinId AS bulletinId, " +
            "p.bulletinName AS bulletinName, " +
            "p.signUpTime AS publishTime, " +
            "p.dealStatus AS isWin, " +
            "p.dealMoney AS money, " +
            "m.companyId AS tenderId, " +
            "m.companyName AS tenderName, " +
            "m.contacter AS tenderContactUser, " +
            "m.contactPhone AS tenderContactWay, " +
            "m.companyAddress AS tenderAddress, " +
            "p.sourceType AS sourceType")
    List<Map<String, Object>> getCompanyWinProjectsByCompanyId_BC(@Param("companyId") String companyId);

    //根据两公司id, 查询两公司的竞争记录BCCompeteRecordModel
    @Query("MATCH (n:CompanyCp{companyId: {companyId1}})-[r1:Submit]->(p:ProjectCp)" +
            "<-[r2:Submit]-(m:CompanyCp{companyId: {companyId2}}) WITH m, p " +
            "MATCH (t:CompanyCp)-[r3:Invite]->(p) RETURN " +
            "m.companyId AS comId, " +
            "m.companyName AS comName, " +
            "p.bulletinId AS bulletinId, " +
            "p.bulletinName AS bulletinName, " +
            "p.pubDate AS publishTime, " +
            "t.companyId AS tenderId, " +
            "t.companyName AS tenderName, " +
            "t.contacter AS tenderContactUser, " +
            "t.contactPhone AS tenderContactWay, " +
            "t.companyAddress AS tenderAddress")
    List<Map<String, Object>> getCompeteRecordByCompanyId_BC(@Param("companyId1") String companyId1,
                                                             @Param("companyId2") String companyId2);



}
