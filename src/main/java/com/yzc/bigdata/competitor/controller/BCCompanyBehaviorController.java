package com.yzc.bigdata.competitor.controller;

import com.yzc.bigdata.competitor.model.*;
import com.yzc.bigdata.competitor.service.CompanyService;
import com.yzc.bigdata.competitor.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Classname BCCompanyBehaviorController
 * @Author lizonghuan
 * @Description Controller
 * @Date-Time 2019/6/19-16:25
 * @Version 1.0
 */
@RestController
@RequestMapping(path = "/BCCompetitor")
public class BCCompanyBehaviorController {

    @Autowired
    CompanyService companyService;

    @Autowired
    ProjectService projectService;

    //去除C#的引号
    public String removeQuotation(String inputStr){
        if (inputStr != null){
            return inputStr.replaceAll("\"|'", "");
        }else{
            return null;
        }
    }

    //竞争对手的基本信息
    @RequestMapping(path = "/getCompete.do", method = RequestMethod.POST)
    public BaseReturnDto<BCSimpleComInfoDto> getCompete(@RequestBody String comId){
        BaseReturnDto<BCSimpleComInfoDto> baseReturnDto = new BaseReturnDto<>();
        if (comId == null || comId == "") {
            baseReturnDto.setStatus("failed");
            baseReturnDto.setDesc("comId为空");
            return baseReturnDto;
        }
        comId = removeQuotation(comId);
        try{
            List<BCSimpleComInfoDto> bcSimpleComInfoDtoList = companyService.getSortCompetitorsInfoByCompanyId(comId);
            baseReturnDto.setDataList(bcSimpleComInfoDtoList);
            baseReturnDto.setStatus("success");
            return baseReturnDto;
        }catch(Exception e){
            baseReturnDto.setStatus("failed");
            baseReturnDto.setDesc("服务器内部查询出错");
            return baseReturnDto;
        }
    }

    //企业上下游关系查询
    @RequestMapping(path = "/getBidRelation.do", method = RequestMethod.POST)
    public BaseReturnDto<BCSimpleComInfoDto> getBidRelation(@RequestBody BCQueryBidRelationDto dto){
        BaseReturnDto<BCSimpleComInfoDto> baseReturnDto = new BaseReturnDto<>();
        try{
            if (dto.getComId() != null && dto.getComId() != ""){
                if (dto.getType() == 1){
                    List<BCSimpleComInfoDto> bcSimpleComInfoDtoList = companyService.
                            getSortLowerLevelCompanysInfoByCompanyId(dto.getComId());
                    baseReturnDto.setDataList(bcSimpleComInfoDtoList);
                    baseReturnDto.setStatus("success");
                    baseReturnDto.setDesc("comId查询下层企业");
                    return baseReturnDto;
                }else if (dto.getType() == 2){
                    List<BCSimpleComInfoDto> bcSimpleComInfoDtoList = companyService.
                            getSortUpperLevelCompanysInfoByCompanyId(dto.getComId());
                    baseReturnDto.setDataList(bcSimpleComInfoDtoList);
                    baseReturnDto.setStatus("success");
                    baseReturnDto.setDesc("comId查询上层企业");
                    return baseReturnDto;
                }else{
                    baseReturnDto.setStatus("failed");
                    baseReturnDto.setDesc("type不为1和2");
                    return baseReturnDto;
                }
            }else if (dto.getCompanyName() != null && dto.getCompanyName() != ""){
                if (dto.getType() == 1){
                    List<BCSimpleComInfoDto> bcSimpleComInfoDtoList = companyService.
                            getSortLowerLevelCompanysInfoByCompanyName(dto.getCompanyName());
                    baseReturnDto.setDataList(bcSimpleComInfoDtoList);
                    baseReturnDto.setStatus("success");
                    baseReturnDto.setDesc("comName查询下层企业");
                    return baseReturnDto;
                }else if (dto.getType() == 2){
                    List<BCSimpleComInfoDto> bcSimpleComInfoDtoList = companyService.
                            getSortUpperLevelCompanysInfoByCompanyName(dto.getCompanyName());
                    baseReturnDto.setDataList(bcSimpleComInfoDtoList);
                    baseReturnDto.setStatus("success");
                    baseReturnDto.setDesc("comName查询上层企业");
                    return baseReturnDto;
                }else{
                    baseReturnDto.setStatus("failed");
                    baseReturnDto.setDesc("type不为1和2");
                    return baseReturnDto;
                }
            }
            baseReturnDto.setStatus("failed");
            baseReturnDto.setDesc("输入的公司id或公司名称均为空");
            return baseReturnDto;
        }catch(Exception e){
            baseReturnDto.setStatus("failed");
            baseReturnDto.setDesc("服务器内部查询出错");
            return baseReturnDto;
        }
    }

    //通过数据库和elasticsearch查询发布的公告
    @RequestMapping(path = "/getTendRecord.do", method = RequestMethod.POST)
    public BaseReturnDto<BCSimpleBulletinDto> getTendRecord(@RequestBody String comId){
        BaseReturnDto<BCSimpleBulletinDto> baseReturnDto = new BaseReturnDto<>();
        if (comId == null || comId == "") {
            baseReturnDto.setStatus("failed");
            baseReturnDto.setDesc("comId为空");
            return baseReturnDto;
        }
        comId = removeQuotation(comId);
        try{
            baseReturnDto.setDataList(projectService.getTendRecord(comId));
            System.out.println(projectService.getTendRecord(comId));
            baseReturnDto.setStatus("success");
            return baseReturnDto;
        } catch(Exception e){
            baseReturnDto.setStatus("failed");
            baseReturnDto.setDesc("服务器内部查询出错");
            return baseReturnDto;
        }
    }

    //查询中标记录
    @RequestMapping(path = "/getWinRecord.do", method = RequestMethod.POST)
    public BaseReturnDto<BCSimpleBulletinDto> getWinRecord(@RequestBody String comId){
        BaseReturnDto<BCSimpleBulletinDto> baseReturnDto = new BaseReturnDto<>();
        if (comId == null || comId == ""){
            baseReturnDto.setStatus("failed");
            baseReturnDto.setDesc("comId为空");
            return baseReturnDto;
        }
        comId = removeQuotation(comId);
        try{
            baseReturnDto.setDataList(projectService.getWinRecord(comId));
            baseReturnDto.setStatus("success");
            return baseReturnDto;
        }catch(Exception e){
            baseReturnDto.setStatus("failed");
            baseReturnDto.setDesc("服务器内部查询出错");
            return baseReturnDto;
        }
    }

    //查询两个企业的竞争次数
    @RequestMapping(path = "/getCompeteRecord.do", method = RequestMethod.POST)
    public BaseReturnDto<BCCompeteRecordModel> getCompeteRecord(@RequestBody ComCompeteModel comCompeteModel){
        BaseReturnDto<BCCompeteRecordModel> baseReturnDto = new BaseReturnDto<>();
        String comId1 = removeQuotation(comCompeteModel.getComId1()),
                comId2 = removeQuotation(comCompeteModel.getComId2());
        if (comId1 == null || comId1 == "" || comId2 == null || comId2 == ""){
            baseReturnDto.setStatus("failed");
            baseReturnDto.setDesc("comId1或comId2为空");
            return baseReturnDto;
        }
        try{
            baseReturnDto.setDataList(projectService.getCompeteRecord(comId1, comId2));
            baseReturnDto.setStatus("success");
            return baseReturnDto;
        }catch(Exception e){
            baseReturnDto.setStatus("failed");
            baseReturnDto.setDesc("服务器内部查询出错");
            return baseReturnDto;
        }
    }

    @RequestMapping(path = "/getContactsByComId.do", method = RequestMethod.POST)
    public BaseReturnDto<BCCompanyContactModel> getContactsByComId(@RequestBody String comId){
        BaseReturnDto<BCCompanyContactModel> baseReturnDto = new BaseReturnDto<>();
        if (comId == null || comId == "") {
            baseReturnDto.setStatus("failed");
            baseReturnDto.setDesc("comId为空");
            return baseReturnDto;
        }
        comId = removeQuotation(comId);
        try{
            BCCompanyContactModel bcCompanyContactModel = companyService.getContactsByComId(comId);
            baseReturnDto.setData(bcCompanyContactModel);
            baseReturnDto.setStatus("success");
            return baseReturnDto;
        }catch(Exception e){
            baseReturnDto.setStatus("failed");
            baseReturnDto.setDesc("服务器内部查询出错");
            return baseReturnDto;
        }
    }

    //模糊查询得到投标人或者业主列表
    @RequestMapping(path = "/getCompanyInfo.do", method = RequestMethod.POST)
    public BaseReturnDto<BCSimpleComInfoDto> getCompanyInfo(@RequestBody BCComNameLikeModel comNameLikeModel){
        BaseReturnDto<BCSimpleComInfoDto> baseReturnDto = new BaseReturnDto<>();
        if (comNameLikeModel.getComNameLike() == null || comNameLikeModel.getComNameLike() == ""){
            baseReturnDto.setStatus("failed");
            baseReturnDto.setDesc("输入comNameLike为空");
            return baseReturnDto;
        }
        if (comNameLikeModel.getType() != 1 && comNameLikeModel.getType() != 2){
            baseReturnDto.setStatus("failed");
            baseReturnDto.setDesc("输入type应为1或2");
            return baseReturnDto;
        }
        baseReturnDto.setDataList(companyService.getCompanyInfoByCompanyNameLike(comNameLikeModel));
        baseReturnDto.setStatus("success");
        return baseReturnDto;
    }

    //根据companyId查询企业业绩， 查询失败为-1
    @RequestMapping(path = "/getBCCompanyPerformanceById.do", method = RequestMethod.POST)
    public int getBCCompanyPerformanceById(@RequestBody String comId){
        if (comId == null || comId == "") {
            return -1;
        }
        comId = removeQuotation(comId);
        try{
             return companyService.getBCCompanyPerformanceById(comId);
        }catch(Exception e){
            return -1;
        }
    }

//    @RequestMapping(path = "/getBCCompanyPerformanceById.do", method = RequestMethod.POST)

//    //分页查询企业竞争记录
//    @RequestMapping(path = "/getCompeteRecordByPage.do", method = RequestMethod.POST)
//    public Map<String, String> getCompeteRecordByPage(BCQueryCompeteRecordDto bcQueryCompeteRecordDto){
//
//    }
//
//    @RequestMapping(path = "/getB", method = RequestMethod.POST)
//    public Map<String, String> get


    //分页查询企业上下游

    //分页查询招标记录

    //分页查询中标记录


}
