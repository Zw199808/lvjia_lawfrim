package com.xinou.lawfrim.sso.controller;



import com.xinou.lawfrim.common.util.APIResponse;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhangbo on 2017/10/23.
 * 后台系统管理
 */
public interface AppSSOController {

    /**
     * 添加后台系统
     *
     * @param name 系统名称
     * @param code 系统编码
     */
    APIResponse addApp(String name, String code);


    /**
     * 删除系统
     *
     * @param id 系统id
     */
    APIResponse delApp(int id);

    /**
     * 修改系统
     *
     * @param id       id
     * @param name     名称
     * @param code     编码
     * @param isEnable 是否可用  0:可用  1:不可用
     * @param sort     排序
     */
    APIResponse updateApp(int id, String name, String code, int isEnable, int sort);


    /**
     * 系统列表
     *
     * @param page     页数
     * @param pageSize 显示个数   如果是下拉菜单   pageSize传1000
     */
    APIResponse appList(HttpServletResponse response,
                        @RequestParam(required = false, defaultValue = "1") int page,
                        @RequestParam(required = false, defaultValue = "10") int pageSize);


    /**
     * 根据id获取详情
     * @param id id
     */
    APIResponse getAppById(int id);

}
