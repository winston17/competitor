package com.yzc.bigdata.competitor.controller;

import com.yzc.bigdata.competitor.model.BaseReturnDto;
import com.yzc.bigdata.competitor.model.CACompany;
import com.yzc.bigdata.competitor.model.MDQualifications;
import com.yzc.bigdata.competitor.model.PageQueryModel;
import com.yzc.bigdata.competitor.service.CACompanyService;
import com.yzc.bigdata.competitor.service.MDQualificationsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2019/7/1.
 */
@Controller
@RequestMapping("/getQualification")
public class QualificationsController {
    @Resource
    MDQualificationsService mdQualificationsService;
    @Resource
    CACompanyService caCompanyService;

    /**
     * 根据公司名称 获取资质信息
     * @param companyName
     * @return
     */
    @ResponseBody
    @PostMapping("/getAllMDQualifications.do")
    public BaseReturnDto<MDQualifications> getMDQualifications(@RequestBody String companyName){
        MDQualifications model = mdQualificationsService.getAllMDQualifications(companyName);
        BaseReturnDto<MDQualifications> baseReturnDto=new BaseReturnDto<>();
        baseReturnDto.setData(model);
        return baseReturnDto;
    }

    @ResponseBody
    @PostMapping("/getCompanyDetial.do")
    public BaseReturnDto<CACompany> getCompanyDetial(@RequestBody String companyName){
        if(companyName==null||companyName.isEmpty()){
            BaseReturnDto<CACompany> baseReturnDto=new BaseReturnDto<>();
            baseReturnDto.setStatus("error");
            baseReturnDto.setDesc("公司名称不能为空！");
            return baseReturnDto;
        }
        companyName=companyName.replaceAll("\"","");
        CACompany detial = caCompanyService.getDetial(companyName);
        BaseReturnDto<CACompany> baseReturnDto=new BaseReturnDto<>();
        baseReturnDto.setData(detial);
        baseReturnDto.setStatus("success");
        baseReturnDto.setDesc("查询成功！");
        return baseReturnDto;
    }
}
