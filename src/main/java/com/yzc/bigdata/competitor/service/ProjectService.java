package com.yzc.bigdata.competitor.service;

import com.yzc.bigdata.competitor.entity.node.ProjectCp;
import com.yzc.bigdata.competitor.model.*;
import com.yzc.bigdata.competitor.repository.CompanyCpRepository;
import com.yzc.bigdata.competitor.repository.ProjectCpRepository;
import com.yzc.bigdata.competitor.util.JdbcUtil;
import com.yzc.bigdata.competitor.util.ReflectClassUtil;
import com.yzc.bigdata.competitor.util.baseUtil.FrequencySortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Classname ProjectService
 * @Author lizonghuan
 * @Description 项目查询服务层
 * @Date-Time 2019/6/13-14:59
 * @Version 1.0
 */

@Service
public class ProjectService {

    @Autowired
    ProjectCpRepository projectCpRepository;

    @Autowired
    CompanyCpRepository companyCpRepository;

    private ReflectClassUtil<BCSimpleBulletinDto> bcSimpleBulletinDtoReflectClassUtil = new ReflectClassUtil<>();
    private ReflectClassUtil<BCCompeteRecordModel> bcCompeteRecordModelReflectClassUtil = new ReflectClassUtil<>();





//    int status = projectCpRepository.addCompanyAndProject(companyInviter.getCompanyNewId(), project.getProjectId(),
//            companySubmitter.getCompanyNewId(), companyInviter.getCompanyId(), companyInviter.getCompanyName(),
//            companySubmitter.getCompanyId(), companySubmitter.getCompanyName(), project.getProjectType(),
//            project.getProjectName(), project.getBulletinId(), project.getExpStatus(), project.getDealStatus(),
//            project.getSignUpTime(), project.getExpTime(), project.getDealTime(), project.getTendWay(), project.getDealMoney());



//    public void insertProjectCp(InsertModel insertModel){
//        try{
//
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }



    //通过公司Id, 获得站内公司的联系人、电话、地址模型
    public BCCompanyContactModel getOnSiteCompanyContactor(String comId){
        BCCompanyContactModel bcCompanyContactModel = new BCCompanyContactModel();
        return bcCompanyContactModel;
    }


    //通过公告类型，获得公告来源（站内招标: 1; 站内采购: 2; 站外: 3）
    public Integer getSourceType(Integer projectType){
        Integer sourceType = null;
        switch(projectType){
            case 1:
                sourceType = 1;
                break;
            case 2:
                sourceType = 1;
                break;
            case 3:
                sourceType = 2;
                break;
            case 4:
                sourceType = 2;
                break;
            case 5:
                sourceType = 2;
                break;
            case 6:
                sourceType = 2;
                break;
            default:
                sourceType = 3;
                break;
        }
        return sourceType;
    }

    //通过公司id获得企业招标数据
    public List<BCSimpleBulletinDto> getTendRecord(String comId){
        List<Map<String, Object>> mapList = projectCpRepository.getCompanyInviteProjectsByCompanyId_BC(comId);
        List<BCSimpleBulletinDto> bcSimpleBulletinDtoList = new ArrayList<>();
        BCSimpleBulletinDto bcSimpleBulletinDto;
        String isWin, money, sourceType;
        for(Map<String, Object> map : mapList){
            bcSimpleBulletinDto = new BCSimpleBulletinDto();
            bcSimpleBulletinDto.setBulletinId(String.valueOf(map.get("bulletinId")));
            bcSimpleBulletinDto.setBulletinName(String.valueOf(map.get("bulletinName")));
            bcSimpleBulletinDto.setPublishTime(String.valueOf(map.get("publishTime")));
            isWin = String.valueOf(map.get("isWin"));
            bcSimpleBulletinDto.setIsWin(isWin == null || isWin == "null" ? 0 : Integer.parseInt(isWin));
            money = String.valueOf(map.get("money"));
            bcSimpleBulletinDto.setMoney(money == null || money == "null" ? new BigDecimal(0) : new BigDecimal(money));
            bcSimpleBulletinDto.setTenderId(String.valueOf(map.get("tenderId")));
            bcSimpleBulletinDto.setTenderName(String.valueOf(map.get("tenderName")));
            bcSimpleBulletinDto.setTenderContactUser(String.valueOf(map.get("tenderContactUser")));
            bcSimpleBulletinDto.setTenderContactWay(String.valueOf(map.get("tenderContactWay")));
            bcSimpleBulletinDto.setTenderAddress(String.valueOf(map.get("tenderAddress")));
            sourceType = String.valueOf(map.get("sourceType"));
            bcSimpleBulletinDto.setSourceType(sourceType == null || sourceType == "null" ? 3 : Integer.parseInt(sourceType));
            bcSimpleBulletinDtoList.add(bcSimpleBulletinDto);
        }
        return bcSimpleBulletinDtoReflectClassUtil.changeNullAttributeObjectList(bcSimpleBulletinDtoList);
    }

    //通过公司id获得企业中标数据
    public List<BCSimpleBulletinDto> getWinRecord(String comId){
        List<Map<String, Object>> mapList = projectCpRepository.getCompanyWinProjectsByCompanyId_BC(comId);
        List<BCSimpleBulletinDto> bcSimpleBulletinDtoList = new ArrayList<>();
        BCSimpleBulletinDto bcSimpleBulletinDto;
        String isWin, money, sourceType;
        for(Map<String, Object> map : mapList){
            bcSimpleBulletinDto = new BCSimpleBulletinDto();
            bcSimpleBulletinDto.setBulletinId(String.valueOf(map.get("bulletinId")));
            bcSimpleBulletinDto.setBulletinName(String.valueOf(map.get("bulletinName")));
            bcSimpleBulletinDto.setPublishTime(String.valueOf(map.get("publishTime")));
            isWin = String.valueOf(map.get("isWin"));
            bcSimpleBulletinDto.setIsWin(isWin == null || isWin == "null" ? 0 : Integer.parseInt(isWin));
            money = String.valueOf(map.get("money"));
            bcSimpleBulletinDto.setMoney(money == null || money == "null" ? new BigDecimal(0) : new BigDecimal(money));
            bcSimpleBulletinDto.setTenderId(String.valueOf(map.get("tenderId")));
            bcSimpleBulletinDto.setTenderName(String.valueOf(map.get("tenderName")));
            bcSimpleBulletinDto.setTenderContactUser(String.valueOf(map.get("tenderContactUser")));
            bcSimpleBulletinDto.setTenderContactWay(String.valueOf(map.get("tenderContactWay")));
            bcSimpleBulletinDto.setTenderAddress(String.valueOf(map.get("tenderAddress")));
            sourceType = String.valueOf(map.get("sourceType"));
            bcSimpleBulletinDto.setSourceType(sourceType == null || sourceType == "null" ? 3 : Integer.parseInt(sourceType));
            bcSimpleBulletinDtoList.add(bcSimpleBulletinDto);
        }
        return bcSimpleBulletinDtoReflectClassUtil.changeNullAttributeObjectList(bcSimpleBulletinDtoList);
    }

    //通过公司id获得企业竞争关系数据数据
    public List<BCCompeteRecordModel> getCompeteRecord(String comId1, String comId2){
        List<Map<String, Object>> mapList = projectCpRepository.getCompeteRecordByCompanyId_BC(comId1, comId2);
        List<BCCompeteRecordModel > bCCompeteRecordModelList = new ArrayList<>();
        BCCompeteRecordModel bCCompeteRecordModel;
        for(Map<String, Object> map : mapList){
            bCCompeteRecordModel = new BCCompeteRecordModel();
            bCCompeteRecordModel.setComId(String.valueOf(map.get("comId")));
            bCCompeteRecordModel.setComName(String.valueOf(map.get("comName")));
            bCCompeteRecordModel.setBulletinId(String.valueOf(map.get("bulletinId")));
            bCCompeteRecordModel.setBulletinName(String.valueOf(map.get("bulletinName")));
            bCCompeteRecordModel.setPublishTime(String.valueOf(map.get("publishTime")));
            bCCompeteRecordModel.setTenderId(String.valueOf(map.get("tenderId")));
            bCCompeteRecordModel.setTenderName(String.valueOf(map.get("tenderName")));
            bCCompeteRecordModel.setTenderContactUser(String.valueOf(map.get("tenderContactUser")));
            bCCompeteRecordModel.setTenderContactWay(String.valueOf(map.get("tenderContactWay")));
            bCCompeteRecordModel.setTenderAddress(String.valueOf(map.get("tenderAddress")));
            bCCompeteRecordModelList.add(bCCompeteRecordModel);
        }
        return bcCompeteRecordModelReflectClassUtil.changeNullAttributeObjectList(bCCompeteRecordModelList);
    }




    //通过companyId获得发布项目
    public List<SimpleBulletinModel> getInviteBulletinModelByCompanyId(String companyId){
        SimpleBulletinModel simpleBulletinModel;
        List<SimpleBulletinModel> simpleBulletinModels = new ArrayList<>();
        List<ProjectCp> projectCps = projectCpRepository.getCompanyInviteProjectsByCompanyId(companyId);
        for(ProjectCp projectCp : projectCps){
            try{
                simpleBulletinModel = new SimpleBulletinModel(projectCp.getBulletinId(),
                        projectCp.getBulletinName(),
                        projectCp.getSignUpTime());
                simpleBulletinModels.add(simpleBulletinModel);
            }catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }
        return simpleBulletinModels;
    }

    //通过companyName获得发布项目
    public List<SimpleBulletinModel> getInviteBulletinModelByCompanyName(String companyName){
        SimpleBulletinModel simpleBulletinModel;
        List<SimpleBulletinModel> simpleBulletinModels = new ArrayList<>();
        List<ProjectCp> projectCps = projectCpRepository.getCompanyInviteProjectsByCompanyName(companyName);
        for(ProjectCp projectCp : projectCps){
            try{
                simpleBulletinModel = new SimpleBulletinModel(projectCp.getBulletinId(),
                        projectCp.getBulletinName(),
                        projectCp.getSignUpTime());
                simpleBulletinModels.add(simpleBulletinModel);
            }catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }
        return simpleBulletinModels;
    }

    //通过companyId获得报名项目
    public List<SimpleBulletinModel> getSubmitBulletinModelByCompanyId(String companyId){
        SimpleBulletinModel simpleBulletinModel;
        List<SimpleBulletinModel> simpleBulletinModels = new ArrayList<>();
        List<ProjectCp> projectCps = projectCpRepository.getCompanySubmitProjectsByCompanyId(companyId);
        for(ProjectCp projectCp : projectCps){
            try{
                simpleBulletinModel = new SimpleBulletinModel(projectCp.getBulletinId(),
                        projectCp.getBulletinName(),
                        projectCp.getSignUpTime());
                simpleBulletinModels.add(simpleBulletinModel);
            }catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }
        return simpleBulletinModels;
    }

    //通过companyName获得报名项目
    public List<SimpleBulletinModel> getSubmitBulletinModelByCompanyName(String companyName){
        SimpleBulletinModel simpleBulletinModel;
        List<SimpleBulletinModel> simpleBulletinModels = new ArrayList<>();
        List<ProjectCp> projectCps = projectCpRepository.getCompanySubmitProjectsByCompanyName(companyName);
        for(ProjectCp projectCp : projectCps){
            try{
                simpleBulletinModel = new SimpleBulletinModel(projectCp.getBulletinId(),
                        projectCp.getBulletinName(),
                        projectCp.getSignUpTime());
                simpleBulletinModels.add(simpleBulletinModel);
            }catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }
        return simpleBulletinModels;
    }

    //通过companyId获得中标项目
    public List<SimpleBulletinModel> getWinBulletinModelByCompanyId(String companyId){
        SimpleBulletinModel simpleBulletinModel;
        List<SimpleBulletinModel> simpleBulletinModels = new ArrayList<>();
        List<ProjectCp> projectCps = projectCpRepository.getCompanyWinProjectsByCompanyId(companyId);
        for(ProjectCp projectCp : projectCps){
            try{
                simpleBulletinModel = new SimpleBulletinModel(projectCp.getBulletinId(),
                        projectCp.getBulletinName(),
                        projectCp.getSignUpTime());
                simpleBulletinModels.add(simpleBulletinModel);
            }catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }
        return simpleBulletinModels;
    }

    //通过companyName获得中标项目
    public List<SimpleBulletinModel> getWinBulletinModelByCompanyName(String companyName){
        SimpleBulletinModel simpleBulletinModel;
        List<SimpleBulletinModel> simpleBulletinModels = new ArrayList<>();
        List<ProjectCp> projectCps = projectCpRepository.getCompanyWinProjectsByCompanyName(companyName);
        for(ProjectCp projectCp : projectCps){
            try{
                simpleBulletinModel = new SimpleBulletinModel(projectCp.getBulletinId(),
                        projectCp.getBulletinName(),
                        projectCp.getSignUpTime());
                simpleBulletinModels.add(simpleBulletinModel);
            }catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }
        return simpleBulletinModels;
    }

}
