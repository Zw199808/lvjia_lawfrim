package com.xinou.lawfrim.sso.controller.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.TimeChange;
import com.xinou.lawfrim.sso.config.ConfigSSO;
import com.xinou.lawfrim.sso.entity.App;
import com.xinou.lawfrim.sso.service.AppSSOService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangbo on 2017/10/23.
 * 系统应用
 */
@RestController
@RequestMapping("/admin/app/")
public class AppSSOControllerImp {

    @Autowired
    AppSSOService appService;

    @RequestMapping("add")
    @RequiresPermissions("/admin/app/add")
    public APIResponse addApp(@RequestBody App app1) {

        App app = new App();
        app.setName(app1.getName());
        app.setCode(app1.getCode());
        app.setIsEnable(1);

        //应用名已存在
        App db_app = appService.getOne(new QueryWrapper<App>().eq("name", app1.getName()).eq("is_delete",0));
        if (db_app != null) {
            return new APIResponse(ConfigSSO.RE_CODE_APP_NAME_HAD, ConfigSSO.RE_MSG_APP_NAME_HAD);
        }

        //应用编码已存在
        App db_app1 = appService.getOne(new QueryWrapper<App>().eq("code", app1.getCode()).eq("is_delete",0));
        if (db_app1 != null) {
            return new APIResponse(ConfigSSO.RE_CODE_APP_CODE_HAD, ConfigSSO.RE_MSG_APP_CODE_HAD);
        }

        boolean result = appService.save(app);

        if (result) {
            return new APIResponse();
        }

        return new APIResponse(ConfigSSO.RE_CODE_ROLE_ERROR, ConfigSSO.RE_MSG_ROLE_ERROR);
    }

    @RequestMapping("del")
    @RequiresPermissions("/admin/app/del")
    public APIResponse delApp(@RequestBody App app1) {

        App app = appService.getById(app1.getId());
        app.setId(app1.getId());
        app.setIsDelete(1);

        boolean result = appService.updateById(app);

        if (result) {
            return new APIResponse();
        }
        return new APIResponse(ConfigSSO.RE_CODE_ROLE_ERROR, ConfigSSO.RE_MSG_ROLE_ERROR);
    }

    @RequestMapping("update")
    @RequiresPermissions("/admin/app/update")
    public APIResponse updateApp(@RequestBody App app1) {

        App app = appService.getById(app1.getId());
        app.setId(app1.getId());
        app.setName(app1.getName());
        app.setCode(app1.getCode());
        app.setIsEnable(app1.getIsEnable());
        app.setSort(app1.getSort());

        //应用名已存在
        List<App> db_app = appService.list(new QueryWrapper<App>().eq("name", app1.getName()).eq("is_delete",0));
        if (db_app != null) {
            for (App info : db_app) {
                int appId = info.getId();
                if (app1.getId() != appId) {
                    return new APIResponse(ConfigSSO.RE_CODE_APP_NAME_HAD, ConfigSSO.RE_MSG_APP_NAME_HAD);
                }
            }
        }

        //应用编码已存在
        List<App> db_app1 = appService.list(new QueryWrapper<App>().eq("code", app1.getCode()).eq("is_delete",0));
        if (db_app1 != null) {
            for (App info : db_app1) {
                int appId = info.getId();
                if (app1.getId() != appId) {
                    return new APIResponse(ConfigSSO.RE_CODE_APP_CODE_HAD, ConfigSSO.RE_MSG_APP_CODE_HAD);
                }
            }
        }


        boolean result = appService.updateById(app);

        if (result) {
            return new APIResponse();
        }

        return new APIResponse(ConfigSSO.RE_CODE_ROLE_ERROR, ConfigSSO.RE_MSG_ROLE_ERROR);
    }


    @RequestMapping("list")
    @RequiresPermissions("/admin/app/list")
    public APIResponse appList(HttpServletResponse response, @RequestBody App app1) {


        IPage myPage = new Page(app1.getPage(), app1.getPageSize());
        myPage = appService.page(myPage,
                new QueryWrapper<App>()
                        .eq("is_delete", 0)
                        .orderByDesc("sort!=0")
                        .orderByAsc("sort")
                        .orderByDesc("gmt_create")
        );

        List<App> list = myPage.getRecords();

        for (App info : list) {
            Timestamp tt = info.getGmtCreate();
            info.setGmtCreateStr(TimeChange.timeStampChangeString(tt));
        }

        long total = myPage.getTotal();

        Map<String, Object> data = new HashMap<>();
        data.put("dataList", list);
        data.put("total", total);


        return new APIResponse(data);
    }

    @RequestMapping("get")
//    @RequiresPermissions("/admin/app/get")
    public APIResponse getAppById(@RequestBody App app1) {

        App app = appService.getById(app1.getId());

        Map<String, Object> data = new HashMap<>();
        data.put("app", app);

        return new APIResponse(data);
    }
}
