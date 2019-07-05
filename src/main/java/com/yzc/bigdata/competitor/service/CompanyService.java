package com.yzc.bigdata.competitor.service;

import com.yzc.bigdata.competitor.entity.node.CompanyCp;
import com.yzc.bigdata.competitor.model.BCComNameLikeModel;
import com.yzc.bigdata.competitor.model.BCCompanyContactModel;
import com.yzc.bigdata.competitor.model.BCSimpleComInfoDto;
import com.yzc.bigdata.competitor.model.BCSimpleComInfoDto;
import com.yzc.bigdata.competitor.repository.CompanyCpRepository;
import com.yzc.bigdata.competitor.repository.ProjectCpRepository;
import com.yzc.bigdata.competitor.util.ReflectClassUtil;
import com.yzc.bigdata.competitor.util.baseUtil.FrequencySortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Classname CompetitorService
 * @Author lizonghuan
 * @Description service
 * @Date-Time 2019/6/13-11:15
 * @Version 1.0
 */
@Service
public class CompanyService {

    @Autowired
    CompanyCpRepository companyCpRepository;

    @Autowired
    ProjectCpRepository projectCpRepository;

    private ReflectClassUtil<BCSimpleComInfoDto> bcSimpleComInfoDtoReflectClassUtil = new ReflectClassUtil<>();
    private ReflectClassUtil<BCCompanyContactModel> bcCompanyContactModelReflectClassUtil = new ReflectClassUtil<>();


    //通过查询总结点数判断neo4j是否启动
    public boolean neo4jStart(){
        try{
            int nodeCount = companyCpRepository.getNodeCount();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


    //通过查询有没有一个公司判断neo4j有没有关于竞争对手的数据，没有得话就要从头更新
    public boolean neo4jHasData(){
        CompanyCp companyCp = companyCpRepository.getACompanyCp();
        if (companyCp == null){
            return false;
        }else{
            return true;
        }
    }

    //通过companyName, 获得依据合作次数排序的上层节点公司
    public List<BCSimpleComInfoDto> getSortUpperLevelCompanysInfoByCompanyName(String companyName){
        List<BCSimpleComInfoDto> companys = new ArrayList<>();
        List<Map<String, String>> comMapList = companyCpRepository.getUpperLevelCompanysInfoCompanyName(companyName);
        if (comMapList != null){
            for(Map<String, String> comMap : comMapList){
                companys.add(new BCSimpleComInfoDto(comMap.get("comId"), comMap.get("comName")));
            }
        }
        companys = FrequencySortUtil.sortList(companys, "count");
        return bcSimpleComInfoDtoReflectClassUtil.changeNullAttributeObjectList(companys);
    }


    //通过companyId, 获得依据合作次数排序的上层节点公司
    public List<BCSimpleComInfoDto> getSortUpperLevelCompanysInfoByCompanyId(String companyId){
        List<BCSimpleComInfoDto> companys = new ArrayList<>();
        List<Map<String, String>> comMapList = companyCpRepository.getUpperLevelCompanysInfoCompanyId(companyId);
        if (comMapList != null){
            for(Map<String, String> comMap : comMapList){
                companys.add(new BCSimpleComInfoDto(comMap.get("comId"), comMap.get("comName")));
            }
        }
        companys = FrequencySortUtil.sortList(companys, "count");
        return bcSimpleComInfoDtoReflectClassUtil.changeNullAttributeObjectList(companys);
    }


    //根据companyName, 获得依据合作次数排序的下层节点公司
    public List<BCSimpleComInfoDto> getSortLowerLevelCompanysInfoByCompanyName(String companyName){
        List<BCSimpleComInfoDto> companys = new ArrayList<>();
        List<Map<String, String>> comMapList = companyCpRepository.getLowerLevelCompanysInfoCompanyName(companyName);
        if (comMapList != null){
            for(Map<String, String> comMap : comMapList){
                companys.add(new BCSimpleComInfoDto(comMap.get("comId"), comMap.get("comName")));
            }
        }
        companys = FrequencySortUtil.sortList(companys, "count");
        return bcSimpleComInfoDtoReflectClassUtil.changeNullAttributeObjectList(companys);
    }

    //通过companyId, 获得依据合作次数排序的下层节点公司
    public List<BCSimpleComInfoDto> getSortLowerLevelCompanysInfoByCompanyId(String companyId){
        List<BCSimpleComInfoDto> companys = new ArrayList<>();
        List<Map<String, String>> comMapList = companyCpRepository.getLowerLevelCompanysInfoCompanyId(companyId);
        if (comMapList != null){
            for(Map<String, String> comMap : comMapList){
                companys.add(new BCSimpleComInfoDto(comMap.get("comId"), comMap.get("comName")));
            }
        }
        companys = FrequencySortUtil.sortList(companys, "count");
        return bcSimpleComInfoDtoReflectClassUtil.changeNullAttributeObjectList(companys);
    }


    //根据companyName, 获得依据竞争次数排序的竞争对手公司
    public List<BCSimpleComInfoDto> getSortCompetitorsInfoByCompanyName(String companyName){
        List<BCSimpleComInfoDto> companys = new ArrayList<>();
        List<Map<String, String>> comMapList = companyCpRepository.getCompetitorsInfoCompanyName(companyName);
        if (comMapList != null){
            for(Map<String, String> comMap : comMapList){
                companys.add(new BCSimpleComInfoDto(comMap.get("comId"), comMap.get("comName")));
            }
        }
        companys = FrequencySortUtil.sortList(companys, "count");
        return bcSimpleComInfoDtoReflectClassUtil.changeNullAttributeObjectList(companys);
    }


    //根据companyId, 获得依据竞争次数排序的竞争对手公司
    public List<BCSimpleComInfoDto> getSortCompetitorsInfoByCompanyId(String companyId){
        List<BCSimpleComInfoDto> companys = new ArrayList<>();
        List<Map<String, String>> comMapList = companyCpRepository.getCompetitorsInfoCompanyId(companyId);
        if (comMapList != null){
            for(Map<String, String> comMap : comMapList){
                companys.add(new BCSimpleComInfoDto(comMap.get("comId"), comMap.get("comName")));
            }
        }
        companys = FrequencySortUtil.sortList(companys, "count");
        return bcSimpleComInfoDtoReflectClassUtil.changeNullAttributeObjectList(companys);
    }

    //根据companyNameLike, 查询业主、代理公司(type = 2)或者投标人、供应商(type = 1)
    public List<BCSimpleComInfoDto> getCompanyInfoByCompanyNameLike(BCComNameLikeModel bcComNameLikeModel){
        List<BCSimpleComInfoDto> bcSimpleComInfoDtoList = new ArrayList<>();
        List<Map<String, String>> mapList;
        if (bcComNameLikeModel.getType() == 1){
            mapList = companyCpRepository.getSubmitterByCompanyNameLike(".*" + bcComNameLikeModel.getComNameLike() + ".*");
            for(Map<String, String> map : mapList){
                bcSimpleComInfoDtoList.add(new BCSimpleComInfoDto(map.get("comId"), map.get("comName")));
            }
        }else if (bcComNameLikeModel.getType() == 2){
            mapList = companyCpRepository.getInviterByCompanyNameLike(".*" + bcComNameLikeModel.getComNameLike() + ".*");
            for(Map<String, String> map : mapList){
                bcSimpleComInfoDtoList.add(new BCSimpleComInfoDto(map.get("comId"), map.get("comName")));
            }
        }
        return bcSimpleComInfoDtoReflectClassUtil.changeNullAttributeObjectList(bcSimpleComInfoDtoList);
    }

    //通过comId, 查询公司联系方式model
    public BCCompanyContactModel getContactsByComId(String companyId){
        List<Map<String, String>> mapList = companyCpRepository.getContactsByComId(companyId);
        if (mapList != null && mapList.size() > 0){
            for(Map<String, String> map : mapList){
                return bcCompanyContactModelReflectClassUtil.changeNullAttributeObject(
                        new BCCompanyContactModel(map.get("contactUser"),
                                map.get("contactWay"), map.get("address"))
                );
            }
            return bcCompanyContactModelReflectClassUtil.changeNullAttributeObject(new BCCompanyContactModel());
        }else{
            return bcCompanyContactModelReflectClassUtil.changeNullAttributeObject(new BCCompanyContactModel());
        }
    }

    //通过comId, 查询公司业绩总数,失败为-1
    public int getBCCompanyPerformanceById(String companyId){
        return companyCpRepository.getBCCompanyPerformanceById(companyId);
    }

}
