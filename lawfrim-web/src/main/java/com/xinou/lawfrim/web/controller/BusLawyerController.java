package com.xinou.lawfrim.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.Config;
import com.xinou.lawfrim.web.dto.BusLawyerDto;
import com.xinou.lawfrim.web.entity.BusLawyer;
import com.xinou.lawfrim.web.service.IBusLawyerService;
import com.xinou.lawfrim.web.service.impl.BusLawyerServiceImpl;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Wangxin
 * @since 2020-10-16
 */
@RestController
@RequestMapping("/web/lawyer")
public class BusLawyerController {

    @Autowired
    private IBusLawyerService busLawyerService;

    @RequestMapping("list")
//    @RequiresPermissions("/web/lawyer/list")
    APIResponse listLawyer(@RequestBody BusLawyerDto lawyerDto) {
        return busLawyerService.listLawyer(lawyerDto);
    }


    @RequestMapping("add")
//    @RequiresPermissions("/web/lawyer/add")
    APIResponse lawyerAdd(@RequestBody BusLawyerDto lawyerDto) {
        boolean res = busLawyerService.addBusLawyer(lawyerDto);
        if (!res) {
            return new APIResponse(Config.RE_ADD_CODE, Config.RE_ADD_MSG);
        }

        return new APIResponse();
    }

}
