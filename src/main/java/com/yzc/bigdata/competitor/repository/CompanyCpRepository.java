package com.yzc.bigdata.competitor.repository;

import com.yzc.bigdata.competitor.entity.node.CompanyCp;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Component
public interface CompanyCpRepository extends Neo4jRepository<CompanyCp, Long> {

    //判断neo4j是否启动
    @Query("MATCH (n) RETURN count(n)")
    int getNodeCount();


    //判断neo4j是否有CompanyCp的节点
    @Query("MATCH (n:CompanyCp) RETURN n LIMIT 1")
    CompanyCp getACompanyCp();

    //建立公司节点, 公司可以是招标人、采购人、供应商、投标人
    @Query("MERGE (n:CompanyCp{companyNewId: {companyNewId}}) " +
            "SET n.companyId = coalesce({companyId}, n.companyId), " +
            "n.companyName = coalesce({companyName}, n.companyName) " +
            "RETURN n LIMIT 1")
    CompanyCp addCompanyCpNode(@Param("companyNewId") String companyNewId,
                           @Param("companyId") String companyId,
                           @Param("companyName") String companyName);


    //寻找公司的上层节点企业名称
    @Query("MATCH (n:CompanyCp{companyName: {companyName}})-[r1:Submit]->" +
            "(p:ProjectCp)<-[r2:Invite]-(m:CompanyCp) " +
            "RETURN m.companyName")
    List<String> getUpperLevelCompanysByCompanyName(@Param("companyName") String companyName);

    //寻找公司的上层节点企业Map信息
    @Query("MATCH (n:CompanyCp{companyName: {companyName}})-[r1:Submit]->" +
            "(p:ProjectCp)<-[r2:Invite]-(m:CompanyCp) " +
            "RETURN r1.dealStatus AS dealStatus, " +
            "r1.dealMoney AS dealMoney, " +
            "p.projectId AS projectId, " +
            "p.projectName AS projectName, " +
            "m.companyName AS companyName")
    List<Map<String, Object>> getUpperLevelMapCompanyName(@Param("companyName") String companyName);



    //查询公司的下层节点企业名称
    @Query("MATCH (n:CompanyCp{companyName: {companyName}})-[r1:Invite]-> " +
            "(p:ProjectCp)<-[r2:Submit]-(m:CompanyCp) " +
            "RETURN m.companyName")
    List<String> getLowerLevelCompanysByCompanyName(@Param("companyName") String companyName);

    //寻找公司的下层节点企业Map信息
    @Query("MATCH (n:CompanyCp{companyName: {companyName}})-[r1:Invite]->" +
            "(p:ProjectCp)<-[r2:Submit]-(m:CompanyCp) " +
            "RETURN r1.dealStatus AS dealStatus, " +
            "r1.dealMoney AS dealMoney, " +
            "p.projectId AS projectId, " +
            "p.projectName AS projectName, " +
            "m.companyName AS companyName")
    List<Map<String, Object>> getLowerLevelMapCompanyName(@Param("companyName") String companyName);


    //查询公司的竞争对手的企业名称
    @Query("MATCH (n:CompanyCp{companyName: {companyName}})-[r1:Submit]-> " +
            "(p:ProjectCp)<-[r2:Submit]-(m:CompanyCp) " +
            "RETURN m.companyName")
    List<String> getCompetitorsByCompanyName(@Param("companyName") String companyName);

    //寻找公司的竞争对手企业Map信息
    @Query("MATCH (n:CompanyCp{companyName: {companyName}})-[r1:Submit]->" +
            "(p:ProjectCp)<-[r2:Submit]-(m:CompanyCp) " +
            "RETURN r1.dealStatus AS dealStatus, " +
            "r1.dealMoney AS dealMoney, " +
            "p.projectId AS projectId, " +
            "p.projectName AS projectName, " +
            "m.companyName AS companyName")
    List<Map<String, Object>> getCompetitorMapCompanyName(@Param("companyName") String companyName);

    //----------------------以下返回SimpleComInfoModel信息-------------------------------

    //根据companyId获得公司竞争对手SimpleComInfoModel信息
    @Query("MATCH (n:CompanyCp{companyId: {companyId}})-[r1:Submit]->" +
            "(p:ProjectCp)<-[r2:Submit]-(m:CompanyCp) " +
            "RETURN m.companyId AS comId, " +
            "m.companyName AS comName")
    List<Map<String, String>> getCompetitorsInfoCompanyId(@Param("companyId") String companyId);

    //根据companyName获得公司竞争对手SimpleComInfoModel信息
    @Query("MATCH (n:CompanyCp{companyName: {companyName}})-[r1:Submit]->" +
            "(p:ProjectCp)<-[r2:Submit]-(m:CompanyCp) " +
            "RETURN m.companyId AS comId, " +
            "m.companyName AS comName")
    List<Map<String, String>> getCompetitorsInfoCompanyName(@Param("companyName") String companyName);


    //根据companyId获得公司下层节点SimpleComInfoModel信息
    @Query("MATCH (n:CompanyCp{companyId: {companyId}})-[r1:Invite]->" +
            "(p:ProjectCp)<-[r2:Submit]-(m:CompanyCp) " +
            "RETURN m.companyId AS comId, " +
            "m.companyName AS comName")
    List<Map<String, String>> getLowerLevelCompanysInfoCompanyId(@Param("companyId") String companyId);

    //根据companyName获得公司下层节点SimpleComInfoModel信息
    @Query("MATCH (n:CompanyCp{companyName: {companyName}})-[r1:Invite]->" +
            "(p:ProjectCp)<-[r2:Submit]-(m:CompanyCp) " +
            "RETURN m.companyId AS comId, " +
            "m.companyName AS comName")
    List<Map<String, String>> getLowerLevelCompanysInfoCompanyName(@Param("companyName") String companyName);


    //根据companyId获得公司上层节点SimpleComInfoModel信息
    @Query("MATCH (n:CompanyCp{companyId: {companyId}})-[r1:Submit]->" +
            "(p:ProjectCp)<-[r2:Invite]-(m:CompanyCp) " +
            "RETURN m.companyId AS comId, " +
            "m.companyName AS comName")
    List<Map<String, String>> getUpperLevelCompanysInfoCompanyId(@Param("companyId") String companyId);

    //根据companyName获得公司上层节点SimpleComInfoModel信息
    @Query("MATCH (n:CompanyCp{companyName: {companyName}})-[r1:Submit]->" +
            "(p:ProjectCp)<-[r2:Invite]-(m:CompanyCp) " +
            "RETURN m.companyId AS comId, " +
            "m.companyName AS comName")
    List<Map<String, String>> getUpperLevelCompanysInfoCompanyName(@Param("companyName") String companyName);

    //模糊查询业主、代理公司
    @Query("MATCH (n:CompanyCp)-[r:Invite]->(p:ProjectCp) WHERE n.companyName =~ {companyNamLike} " +
            "RETURN n.companyId AS comId, n.companyName AS comName LIMIT 50")
    List<Map<String, String>> getInviterByCompanyNameLike(@Param("companyNamLike") String companyNameLike);

    //模糊查询投标人、供应商
    @Query("MATCH (n:CompanyCp)-[r:Submit]->(p:ProjectCp) WHERE n.companyName =~ {companyNamLike} " +
            "RETURN n.companyId AS comId, n.companyName AS comName LIMIT 50")
    List<Map<String, String>> getSubmitterByCompanyNameLike(@Param("companyNamLike") String companyNameLike);

    //通过companyId查询公司的联系方式
    @Query("MATCH (n:CompanyCp{companyId: {companyId}}) RETURN " +
            "n.contacter AS contactUser, " +
            "n.contactPhone AS contactWay, " +
            "n.companyAddress AS address LIMIT 1")
    List<Map<String, String>> getContactsByComId(@Param("companyId") String companyId);

    //通过companyId查询公司的业绩总数
    @Query("MATCH (n:CompanyCp{companyId: {companyId}})-[r:Submit{dealStatus: 1}]-(p:ProjectCp) " +
            "RETURN count(p)")
    int getBCCompanyPerformanceById(@Param("companyId") String companyId);

}
