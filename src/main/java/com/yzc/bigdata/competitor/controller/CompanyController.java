package com.yzc.bigdata.competitor.controller;

import com.yzc.bigdata.competitor.model.BCSimpleComInfoDto;
import com.yzc.bigdata.competitor.model.BCSimpleComInfoDto;
import com.yzc.bigdata.competitor.service.CompanyService;
import com.yzc.bigdata.competitor.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Classname CompanyController
 * @Author lizonghuan
 * @Description
 * @Date-Time 2019/6/13-15:01
 * @Version 1.0
 */
@RestController
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @Autowired
    ProjectService projectService;

    @RequestMapping(path = "/testSuccess", method = RequestMethod.POST)
    @ResponseBody
    public String testSuccess(@RequestBody String companyName){
        return "Success";
    }

    @RequestMapping(path = "/getCompetitorsByCompanyName.do", method = RequestMethod.POST)
    @ResponseBody
    public List<BCSimpleComInfoDto> getCompetitorsByCompanyName(@RequestBody String companyName){
        List<BCSimpleComInfoDto> companys = null;
        List<BCSimpleComInfoDto> top20Companys;
        int count;
        if (companyName != null){
            companys = companyService.getSortCompetitorsInfoByCompanyName(companyName);
        }
        if (companys == null || companys.size() == 0){
            return null;
        }else{
            top20Companys = new ArrayList<>();
            count = 0;
            for(BCSimpleComInfoDto bCSimpleComInfoDto : companys){
                if (20 < ++count){
                    break;
                }
                top20Companys.add(bCSimpleComInfoDto);
            }
        }
        return top20Companys;
    }



    @RequestMapping(path = "/getCompetitorsByIdName.do", method = RequestMethod.POST)
    @ResponseBody
    public List<BCSimpleComInfoDto> getCompetitorsByIdName(@RequestBody BCSimpleComInfoDto inputCompany){
        List<BCSimpleComInfoDto> companys = null;
        List<BCSimpleComInfoDto> top20Companys;
        int count;
        if (inputCompany.getComId() != null){
            companys = companyService.getSortCompetitorsInfoByCompanyId(inputCompany.getComId());
        }
        if (companys == null || companys.size() == 0){
            if (inputCompany.getComName() != null){
                companys = companyService.getSortCompetitorsInfoByCompanyName(inputCompany.getComName());
            }
        }
        if (companys == null || companys.size() == 0){
            return null;
        }else{
            top20Companys = new ArrayList<>();
            count = 0;
            for(BCSimpleComInfoDto bCSimpleComInfoDto : companys){
                if (20 < ++count){
                    break;
                }
                top20Companys.add(bCSimpleComInfoDto);
            }
        }
        return top20Companys;
    }

    //根据输入公司id和公司名, 获得下层节点公司
    @RequestMapping(path = "/getUpperLevelCompanysByIdName.do", method = RequestMethod.POST)
    @ResponseBody
    public List<BCSimpleComInfoDto> getUpperLevelCompanysByIdName(@RequestBody BCSimpleComInfoDto inputCompany){
        List<BCSimpleComInfoDto> companys = null;
        List<BCSimpleComInfoDto> top20Companys;
        int count;
        if (inputCompany.getComId() != null){
            companys = companyService.getSortUpperLevelCompanysInfoByCompanyId(inputCompany.getComId());
        }
        if (companys == null || companys.size() == 0){
            if (inputCompany.getComName() != null){
                companys = companyService.getSortUpperLevelCompanysInfoByCompanyName(inputCompany.getComName());
            }
        }
        if (companys == null || companys.size() == 0){
            return null;
        }else{
            top20Companys = new ArrayList<>();
            count = 0;
            for(BCSimpleComInfoDto bCSimpleComInfoDto : companys){
                if (20 < ++count){
                    break;
                }
                top20Companys.add(bCSimpleComInfoDto);
            }
        }
        return top20Companys;
    }

    //根据输入公司id和公司名, 获得下层节点公司
    @RequestMapping(path = "/getLowerLevelCompanysByIdName.do", method = RequestMethod.POST)
    @ResponseBody
    public List<BCSimpleComInfoDto> getLowerLevelCompanysByIdName(@RequestBody BCSimpleComInfoDto inputCompany){
        List<BCSimpleComInfoDto> companys = null;
        List<BCSimpleComInfoDto> top20Companys;
        int count;
        if (inputCompany.getComId() != null){
            companys = companyService.getSortLowerLevelCompanysInfoByCompanyId(inputCompany.getComId());
        }
        if (companys == null || companys.size() == 0){
            if (inputCompany.getComName() != null){
                companys = companyService.getSortLowerLevelCompanysInfoByCompanyName(inputCompany.getComName());
            }
        }
        if (companys == null || companys.size() == 0){
            return null;
        }else{
            top20Companys = new ArrayList<>();
            count = 0;
            for(BCSimpleComInfoDto BCSimpleComInfoDto : companys){
                if (20 < ++count){
                    break;
                }
                top20Companys.add(BCSimpleComInfoDto);
            }
        }
        return top20Companys;
    }
}
