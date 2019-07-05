package com.yzc.bigdata.competitor.controller;

import com.yzc.bigdata.competitor.model.BCSimpleComInfoDto;
import com.yzc.bigdata.competitor.model.SimpleBulletinModel;
import com.yzc.bigdata.competitor.service.CompanyService;
import com.yzc.bigdata.competitor.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname BulletinController
 * @Author lizonghuan
 * @Description 公告相关接口
 * @Date-Time 2019/6/19-9:58
 * @Version 1.0
 */

@RestController
public class BulletinController {

    @Autowired
    CompanyService companyService;

    @Autowired
    ProjectService projectService;


    //通过id和name, 给出公司发布过的公告
    @RequestMapping(path = "/getInviteBulletinsByIdName.do", method = RequestMethod.POST)
    @ResponseBody
    public List<SimpleBulletinModel> getInviteBulletinsByIdName(@RequestBody BCSimpleComInfoDto inputCompany){
        List<SimpleBulletinModel> bulletins = null;
        if (inputCompany.getComId() != null){
            bulletins = projectService.getInviteBulletinModelByCompanyId(inputCompany.getComId());
        }
        if (bulletins == null || bulletins.size() == 0){
            if (inputCompany.getComName() != null){
                bulletins = projectService.getInviteBulletinModelByCompanyName(inputCompany.getComName());
            }
        }
        return bulletins;
    }

    //通过id和name, 给出公司报名过的公告
    @RequestMapping(path = "/getSubmitBulletinsByIdName.do", method = RequestMethod.POST)
    @ResponseBody
    public List<SimpleBulletinModel> getSubmitBulletinsByIdName(@RequestBody BCSimpleComInfoDto inputCompany){
        List<SimpleBulletinModel> bulletins = null;
        if (inputCompany.getComId() != null){
            bulletins = projectService.getSubmitBulletinModelByCompanyId(inputCompany.getComId());
        }
        if (bulletins == null || bulletins.size() == 0){
            if (inputCompany.getComName() != null){
                bulletins = projectService.getSubmitBulletinModelByCompanyName(inputCompany.getComName());
            }
        }
        return bulletins;
    }

    //通过id和name, 给出公司中标过的公告
    @RequestMapping(path = "/getWinBulletinsByIdName.do", method = RequestMethod.POST)
    @ResponseBody
    public List<SimpleBulletinModel> getWinBulletinsByIdName(@RequestBody BCSimpleComInfoDto inputCompany){
        List<SimpleBulletinModel> bulletins = null;
        if (inputCompany.getComId() != null){
            bulletins = projectService.getWinBulletinModelByCompanyId(inputCompany.getComId());
        }
        if (bulletins == null || bulletins.size() == 0){
            if (inputCompany.getComName() != null){
                bulletins = projectService.getWinBulletinModelByCompanyName(inputCompany.getComName());
            }
        }
        return bulletins;
    }


}
