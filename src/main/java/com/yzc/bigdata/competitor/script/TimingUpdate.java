package com.yzc.bigdata.competitor.script;

import com.yzc.bigdata.competitor.CompetitorApplication;
import com.yzc.bigdata.competitor.config.QuartzConfig;
import com.yzc.bigdata.competitor.entity.node.CompanyCp;
import com.yzc.bigdata.competitor.entity.node.ProjectCp;
import com.yzc.bigdata.competitor.entity.relationship.Invite;
import com.yzc.bigdata.competitor.entity.relationship.Submit;
import com.yzc.bigdata.competitor.model.BCCompanyContactModel;
import com.yzc.bigdata.competitor.model.InsertModel;
import com.yzc.bigdata.competitor.repository.CompanyCpRepository;
import com.yzc.bigdata.competitor.repository.ProjectCpRepository;
import com.yzc.bigdata.competitor.service.CompanyService;
import com.yzc.bigdata.competitor.service.EsService;
import com.yzc.bigdata.competitor.service.ProjectService;
import com.yzc.bigdata.competitor.util.JdbcUtil;
import com.yzc.bigdata.competitor.util.TextUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.xml.soap.Text;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Classname TimingUpdate
 * @Author lizonghuan
 * @Description 定时更新
 * @Date-Time 2019/6/10-16:53
 * @Version 1.0
 */
@Component
public class TimingUpdate extends QuartzJobBean {

    @Autowired
    CompanyCpRepository companyCpRepository;

    @Autowired
    ProjectCpRepository projectCpRepository;

    @Autowired
    CompanyService companyService;

    @Autowired
    ProjectService projectService;

    @Autowired
    EsService esService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String currentTime = sdf.format(QuartzConfig.startDate);
        String startTwoDaysLatter = TextUtil.delayDateDays(currentTime, -8);
        if (sdf.format(new Date()).compareTo(startTwoDaysLatter) < 0){
            System.out.println("当前时间在启动时间的两天之内，暂不执行同步");
        }else{
            //        updateImport();
            System.out.println(new Date().toString());
        }
    }

    public void updateImport() {
        Date date;
        //将当前时间写入文件，之后每次都是读取这个文件的最后一条作为更新截断时间
        String currentTime, onSiteLastUpdateTime, offSiteLastUpdateTime;

        //判断neo4j是否包含公司分类相关数据，即要不要从头开始写入数据
        boolean neo4jHasData = true;

        //判断neo4j是否已经启动
        boolean neo4jStart = companyService.neo4jStart();

        Connection conn = JdbcUtil.getConectionYouZC_DW();
        try {
            //如果neo4j启动
            if (neo4jStart) {
                System.out.println("neo4j启动");
                neo4jHasData = companyService.neo4jHasData();
                if (neo4jHasData) {
                    System.out.println("neo4j有数据");
                    //站内公告更新数据
                    //获得上次站内公告更新的时间onSiteLastUpdateTime
                    onSiteLastUpdateTime = getLastUpdateTime("onSite", conn);
                    //获得当前时间
                    date = new Date();
                    currentTime = sdf.format(date);
                    //在onSiteLastUpdateTime之后的站内公告数据更新到neo4j
                    onSiteUpdateImportInsertModel(onSiteLastUpdateTime, conn);
                    //站内公告完成之后, 将当前时间存下来
                    insertLastUpdateTime(currentTime, "onSite", conn);

                    //站外公告更新到neo4j
                    //获得上次站外公告更新的时间offSiteLastUpdateTime
                    offSiteLastUpdateTime = getLastUpdateTime("offSite", conn);
                    //获得当前时间
                    date = new Date();
                    currentTime = sdf.format(date);
                    //在offSiteLastUpdateTime之后的站外公告数据更新到neo4j
                    offSiteUpdateImport(offSiteLastUpdateTime);
                    //站外公告完成之后, 将当前时间存下来
                    insertLastUpdateTime(currentTime, "offSite", conn);
                } else {
                    System.out.println("neo4j无数据");
                    //获得当前时间
                    date = new Date();
                    currentTime = sdf.format(date);
                    //从头更新站内数据
                    onSiteUpdateImportInsertModel("2000", conn);
                    //站内公告完成之后, 将当前时间存下来
                    insertLastUpdateTime(currentTime, "onSite", conn);

                    //获得当前时间
                    date = new Date();
                    currentTime = sdf.format(date);
                    //从头更新站外数据
                    offSiteUpdateImport("2000");
                    //站外公告完成之后, 将当前时间存下来
                    insertLastUpdateTime(currentTime, "offSite", conn);
                }
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //站内公告单条一并导入，改版为InsertModel模式插入
    public void onSiteUpdateImportInsertModel(String lastUpdateTime, Connection conn) {
        if (lastUpdateTime == null) {
            return;
        }
        //该连接是为了获得bulletinTitle
        Connection conn1 = JdbcUtil.getConectionYouZC_DW();
        String selectSQL = "select t.bidderId, t.projectType, t.projectName, t.projectId, t.bulletinId, t.expStatus, t.dealStatus, \n" +
                "t.signUpTime, t.expTime, t.dealTime, t.dealMoney, t.tendWay, t.companyName, t.companyId,\n" +
                "t.maxUpdateTime, u1.subName, t.contactUserName as bidderContacter, t.contactUserPhone as bidderContactPhone, \n" +
                "u1.companyAddress as bidderCompanyAddress, u2.contactUser as tenderContacter, \n" +
                "u2.ContactUserPhone as tenderContactPhone, u2.companyAddress as tenderCompanyAddress from\n" +
                "(select bidderId, projectType, projectName, projectId, bulletinId, expStatus, dealStatus, \n" +
                "signUpTime, expTime, dealTime, dealMoney, tendWay, companyName, companyId, contactUserName, contactUserPhone,\n" +
                " ( SELECT    MAX(LastUpdateDate)\n" +
                "          FROM      ( VALUES ( signUpTime), ( expTime),\n" +
                "                    ( dealTime) ) AS lastUpdateTime ( LastUpdateDate )\n" +
                "          ) as maxUpdateTime \n" +
                "from T_TaskBidderRegRecord) t \n" +
                "left join User_BiddingSubInfo u1 on u1.BiddingSubInfoId = t.bidderId\n" +
                "left join User_BiddingSubInfo u2 on u2.BiddingSubInfoId = t.companyId \n" +
                "where t.maxupdatetime > '" + lastUpdateTime + "'";
        List<InsertModel> insertModelList = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(selectSQL);
            System.out.println("selectSQL " + selectSQL);
            ResultSet rs = stmt.executeQuery();
            InsertModel insertModelSelect;
            while (rs.next()) {
                try {
                    System.out.println(rs.getString("projectName"));
                    insertModelSelect = new InsertModel(rs.getString("projectId"),
                            rs.getString("companyId"),
                            rs.getString("companyName"),
                            rs.getString("bidderId"),
                            rs.getString("subName"),
                            rs.getInt("projectType"),
                            rs.getString("projectName"),
                            rs.getString("bulletinId"),
                            getBulletinName(rs.getString("bulletinId"),
                                    rs.getInt("projectType"), conn1),
                            rs.getInt("expStatus") == 0 ? null : rs.getInt("expStatus"), //0和null都是null, 为了防止neo4j使用coalesce更新时覆盖数值
                            rs.getInt("dealStatus") == 0 ? null : rs.getInt("dealStatus"),
                            rs.getString("signUpTime"),
                            rs.getString("expTime"),
                            rs.getString("dealTime"),
                            rs.getInt("tendWay"),
                            rs.getString("dealMoney"),
                            projectService.getSourceType(rs.getInt("projectType")),
                            rs.getString("tenderContacter"),
                            rs.getString("tenderContactPhone"),
                            rs.getString("tenderCompanyAddress"),
                            rs.getString("bidderContacter"),
                            rs.getString("bidderContactPhone"),
                            rs.getString("bidderCompanyAddress")
                    );
                    insertModelList.add(insertModelSelect);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
            conn1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (InsertModel insertModel : insertModelList) {
            try {
                //插入neo4j
                int status = projectCpRepository.addCompanyAndProject(insertModel.getInviterNewId(), insertModel.getProjectId(),
                        insertModel.getSubmitterNewId(), insertModel.getInviterId(), insertModel.getInviterName(),
                        insertModel.getSubmitterId(), insertModel.getSubmitterName(), insertModel.getProjectType(),
                        insertModel.getProjectName(), insertModel.getBulletinId(), insertModel.getBulletinName(),
                        insertModel.getExpStatus(), insertModel.getDealStatus(), insertModel.getSignUpTime(),
                        insertModel.getExpTime(), insertModel.getDealTime(), insertModel.getTendWay(),
                        insertModel.getDealMoney(), insertModel.getTenderContacter(), insertModel.getTenderContactPhone(),
                        insertModel.getTenderCompanyAddress(), insertModel.getBidderContacter(), insertModel.getBidderContactPhone(),
                        insertModel.getBidderCompanyAddress(), insertModel.getSourceType());
                System.out.println("插入状态: " + status);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    //站外公告一次性插入
    public void offSiteUpdateImport(String lastUpdateTime) {
        List<InsertModel> insertModelList = esService.selectProject(TextUtil.delayDateDays(lastUpdateTime, 3));
        int i = 0;
        for (InsertModel insertModel : insertModelList) {
            try {
                System.out.println("插入计数: " + i++);
                int status = projectCpRepository.addCompanyAndProjectOffSite(insertModel.getInviterNewId(), insertModel.getProjectId(),
                        insertModel.getSubmitterNewId(), insertModel.getInviterId(), insertModel.getInviterName(), insertModel.getSubmitterId(),
                        insertModel.getSubmitterName(), insertModel.getProjectType(), insertModel.getProjectName(), insertModel.getBulletinId(),
                        insertModel.getBulletinName(), insertModel.getExpStatus(), insertModel.getDealStatus(), insertModel.getSignUpTime(),
                        insertModel.getExpTime(), insertModel.getDealTime(), insertModel.getTendWay(), insertModel.getDealMoney(),
                        insertModel.getTenderContacter(), insertModel.getTenderContactPhone(), insertModel.getTenderCompanyAddress(),
                        insertModel.getBidderContacter(), insertModel.getBidderContactPhone(), insertModel.getBidderCompanyAddress(),
                        insertModel.getSourceType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //站外公告分批量
    public void offSiteUpdateImportPatch(String lastUpdateTime) {
        try {
            int count = esService.patchSelectImportProject(lastUpdateTime);
            System.out.println("站外公告共分批导入数量为: " + count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //通过公告id和公告类型，获得公告名称
    public String getBulletinName(String bulletinId, Integer projectType, Connection conn) throws Exception {
        String selectSQL, tableName = null, fieldName = "bulletinTitle";
        switch (projectType) {
            case 1:
                tableName = "Pro_LB_Bulletin";
                fieldName = "bulletinName";
                break;
            case 2:
                tableName = "Pro_CB_Bulletin";
                break;
            case 3:
                tableName = "Pro_NT_Bulletin";
                break;
            case 4:
                tableName = "Pro_NT_Bulletin";
                break;
            case 5:
                tableName = "Pro_SB_Bulletin";
                break;
            case 6:
                return null;
            default:
                break;
        }
        if (tableName != null) {
            selectSQL = "SELECT top 1 " + fieldName + " as bulletinTitle FROM " + tableName + " WHERE bulletinId = '" + bulletinId + "'";
            PreparedStatement stmt = conn.prepareStatement(selectSQL);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                String bulletinTitle = resultSet.getString("bulletinTitle");
                if (bulletinTitle != null && bulletinTitle != "") {
                    return bulletinTitle;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    //插入运行的时间作为最后更新时间,数据源分为站内(onSite)和站外(offSite)
    public int insertLastUpdateTime(String lastUpdateTime, String dataSource, Connection conn) {
        String insertSQL = "INSERT INTO T_CompetitorUpdateTime (lastUpdateTime, dataSource) " +
                "VALUES ('" + lastUpdateTime + "', '" + dataSource + "')";
        try {
            Statement stmt = conn.createStatement();
            System.out.println(insertSQL);
            stmt.executeUpdate(insertSQL);
            conn.commit();
            System.out.println("插入更新时间成功");
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //获得最后更新时间,数据源分为站内(onSite)和站外(offSite)
    public String getLastUpdateTime(String dataSource, Connection conn) {
        String selectSQL = "SELECT top 1 lastUpdateTime FROM T_CompetitorUpdateTime where dataSource = '" + dataSource + "' order by lastUpdateTime desc";
        System.out.println("getLastUpdateTime selectSql: " + selectSQL);
        try {
            PreparedStatement stmt = conn.prepareStatement(selectSQL);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                String lastUpdateTime = resultSet.getString("lastUpdateTime");
                if (lastUpdateTime != null && lastUpdateTime != "") {
                    return lastUpdateTime;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //根据招标人id获得招标人地址
    public BCCompanyContactModel getContactInfo(String companyId, Connection conn) {
        BCCompanyContactModel bcCompanyContactModel;
        String selectSQL = "SELECT top 1 contactUser, contactUserPhone, companyAddress FROM User_BiddingSubInfo " +
                "WHERE biddingSubInfoId = '" + companyId + "'";
        try {
            PreparedStatement stmt = conn.prepareStatement(selectSQL);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                bcCompanyContactModel = new BCCompanyContactModel();
                bcCompanyContactModel.setContactUser(resultSet.getString("contactUser"));
                bcCompanyContactModel.setContactWay(resultSet.getString("contactUserPhone"));
                bcCompanyContactModel.setAddress(resultSet.getString("companyAddress"));
                return bcCompanyContactModel;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //站内公告单条一并导入，改版
//    public void onSiteUpdateImport(String lastUpdateTime, Connection conn){
//        if (lastUpdateTime == null){
//            return;
//        }
//        String selectSQL = "select t.bidderId, u.subName, t.projectType, t.projectName, t.projectId, t.bulletinId, " +
//                "t.expStatus, t.dealStatus, \n" +
//                "t.signUpTime, t.expTime, t.dealTime, t.dealMoney, t.tendWay, t.companyName, t.companyId,\n" +
//                "t.maxUpdateTime from\n" +
//                "(select bidderId, projectType, projectName, projectId, bulletinId, expStatus, dealStatus, \n" +
//                "signUpTime, expTime, dealTime, dealMoney, tendWay, companyName, companyId, \n" +
//                " ( SELECT    MAX(LastUpdateDate)\n" +
//                "          FROM      ( VALUES ( signUpTime), ( expTime),\n" +
//                "                    ( dealTime) ) AS lastUpdateTime ( LastUpdateDate )\n" +
//                "          ) as maxUpdateTime \n" +
//                "from T_TaskBidderRegRecord) t \n" +
//                "left join User_BiddingSubInfo u on u.BiddingSubInfoId = t.bidderId\n " +
//                "where t.maxUpdateTime > '" + lastUpdateTime + "'";
//        try{
//            PreparedStatement stmt = conn.prepareStatement(selectSQL);
//            System.out.println("selectSQL " + selectSQL);
//            ResultSet rs = stmt.executeQuery();
//            CompanyCp companyInviter, companyInviterResult, companySubmitter, companySubmitterResult;
//            ProjectCp project, projectResult;
//            Invite invite;
//            Submit submit;
//            while(rs.next()){
//                try{
//                    System.out.println(rs.getString("projectName"));
//                    companyInviter = new CompanyCp(rs.getString("companyName"),
//                            rs.getString("companyId"),
//                            rs.getString(""),
//                            rs.getString(""),
//                            rs.getString(""));
//                    companySubmitter = new CompanyCp(rs.getString("subName"), rs.getString("bidderId"));
//                    project = new ProjectCp(rs.getString("projectId"),
//                            rs.getInt("projectType"),
//                            rs.getString("projectName"),
//                            rs.getString("bulletinId"),
//                            rs.getInt("expStatus"),
//                            rs.getInt("dealStatus"),
//                            rs.getString("signUpTime"),
//                            rs.getString("expTime"),
//                            rs.getString("dealTime"),
//                            rs.getInt("tendWay"),
//                            rs.getString("dealMoney"),
//                            projectService.getSourceType(rs.getInt("projectType"))
//                    );
//                    System.out.println(project.getProjectName());
//                    System.out.println("projectId为: " + project.getProjectId());
//                    System.out.println("招标人newId为: " + companyInviter.getCompanyNewId());
//                    System.out.println("投标人newId为: " + companySubmitter.getCompanyNewId());
//                    int status = projectCpRepository.addCompanyAndProject(companyInviter.getCompanyNewId(), project.getProjectId(),
//                            companySubmitter.getCompanyNewId(), companyInviter.getCompanyId(), companyInviter.getCompanyName(),
//                            companySubmitter.getCompanyId(), companySubmitter.getCompanyName(), project.getProjectType(),
//                            project.getProjectName(), project.getBulletinId(), project.getExpStatus(), project.getDealStatus(),
//                            project.getSignUpTime(), project.getExpTime(), project.getDealTime(), project.getTendWay(), project.getDealMoney());
//                    System.out.println("插入状态: "+ status);
//                }catch(Exception e){
//                    e.printStackTrace();
//                    continue;
//                }
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }

    //站内公告单条分布导入, 弃用
//    public void onSiteUpdateImportSteps(String lastUpdateTime){
//        if (lastUpdateTime == null){
//            return;
//        }
//        String selectSQL = "select t.bidderId, u.subName, t.projectType, t.projectName, t.projectId, t.bulletinId, " +
//                "t.expStatus, t.dealStatus, \n" +
//                "t.signUpTime, t.expTime, t.dealTime, t.dealMoney, t.tendWay, t.companyName, t.companyId,\n" +
//                "t.maxUpdateTime from\n" +
//                "(select bidderId, projectType, projectName, projectId, bulletinId, expStatus, dealStatus, \n" +
//                "signUpTime, expTime, dealTime, dealMoney, tendWay, companyName, companyId, \n" +
//                " ( SELECT    MAX(LastUpdateDate)\n" +
//                "          FROM      ( VALUES ( signUpTime), ( expTime),\n" +
//                "                    ( dealTime) ) AS lastUpdateTime ( LastUpdateDate )\n" +
//                "          ) as maxUpdateTime \n" +
//                "from T_TaskBidderRegRecord) t \n" +
//                "left join User_BiddingSubInfo u on u.BiddingSubInfoId = t.bidderId \n" +
//                "where t.maxUpdateTime > '" + lastUpdateTime + "'";
//        try{
//            Connection conn = JdbcUtil.getConectionYouZC_DW();
//            PreparedStatement stmt = conn.prepareStatement(selectSQL);
//            System.out.println("selectSQL " + selectSQL);
//            ResultSet rs = stmt.executeQuery();
//            CompanyCp companyInviter, companyInviterResult, companySubmitter, companySubmitterResult;
//            ProjectCp project, projectResult;
//            Invite invite;
//            Submit submit;
//            while(rs.next()){
//                companyInviter = new CompanyCp(rs.getString("companyName"), rs.getString("companyId"));
//                companySubmitter = new CompanyCp(rs.getString("subName"), rs.getString("bidderId"));
//                project = new ProjectCp(rs.getString("projectId"),
//                        rs.getInt("projectType"),
//                        rs.getString("projectName"),
//                        rs.getString("bulletinId"),
//                        rs.getInt("expStatus"),
//                        rs.getInt("dealStatus"),
//                        rs.getString("signUpTime"),
//                        rs.getString("expTime"),
//                        rs.getString("dealTime"),
//                        rs.getInt("tendWay"),
//                        rs.getString("dealMoney"),
//                        projectService.getSourceType(rs.getInt("projectType"))
//                );
//                companyInviterResult = companyCpRepository.addCompanyCpNode(companyInviter.getCompanyNewId(),
//                        companyInviter.getCompanyId(), companyInviter.getCompanyName());
//                companySubmitterResult = companyCpRepository.addCompanyCpNode(companySubmitter.getCompanyNewId(),
//                        companySubmitter.getCompanyId(), companySubmitter.getCompanyName());
//                projectResult = projectCpRepository.addProjectCpNode(project.getProjectId(), project.getProjectType(),
//                        project.getProjectName(), project.getBulletinId(), project.getExpStatus(),
//                        project.getDealStatus(), project.getSignUpTime(), project.getExpTime(),
//                        project.getDealTime(), project.getTendWay(), project.getDealMoney());
//                invite = projectCpRepository.addInviteRelationship(companyInviterResult.getCompanyNewId(), projectResult.getProjectId());
//                submit = projectCpRepository.addSubmitRelationship(companySubmitterResult.getCompanyNewId(), projectResult.getProjectId(),
//                        projectResult.getDealStatus(),
//                        projectResult.getDealMoney(),
//                        projectResult.getDealTime());
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }


}
