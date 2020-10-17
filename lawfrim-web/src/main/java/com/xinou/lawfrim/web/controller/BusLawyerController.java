package com.xinou.lawfrim.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.Config;
import com.xinou.lawfrim.web.dto.BusLawyerDto;
import com.xinou.lawfrim.web.entity.BusLawyer;
import com.xinou.lawfrim.web.service.IBusLawyerService;
import com.xinou.lawfrim.web.service.impl.BusLawyerServiceImpl;
import com.xinou.lawfrim.web.vo.LawyerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
@Api(tags = {"律师"})
public class BusLawyerController {

    @Autowired
    private IBusLawyerService busLawyerService;

    @PostMapping("list")
//    @RequiresPermissions("/web/lawyer/list")
    @ApiOperation(httpMethod = "POST", value = "律师列表")
    @ApiOperationSupport(includeParameters = {"lawyerDto.name"})
    APIResponse<LawyerVo> listLawyer(@RequestBody BusLawyerDto lawyerDto) {
        return busLawyerService.listLawyer(lawyerDto);
    }


    @PostMapping("add")
//    @RequiresPermissions("/web/lawyer/add")
    @ApiOperation(httpMethod = "POST", value = "添加律师")
    @ApiOperationSupport(ignoreParameters = {"lawyerDto.id","lawyerDto.state","lawyerDto.pageNumber","lawyerDto.pageSize"})
    APIResponse lawyerAdd(@RequestBody BusLawyerDto lawyerDto) {
        return busLawyerService.addBusLawyer(lawyerDto);
    }


    @PostMapping("info")
//    @RequiresPermissions("/web/lawyer/info")
    @ApiOperation(httpMethod = "POST", value = "获取律师信息")
    @ApiOperationSupport(includeParameters = {"lawyerDto.id"})
    APIResponse<LawyerVo> lawyerInfo(@RequestBody BusLawyerDto lawyerDto) {
        return busLawyerService.getBusLawyer(lawyerDto);
    }


    @PostMapping("update")
//    @RequiresPermissions("/web/lawyer/update")
    @ApiOperation(httpMethod = "POST", value = "修改律师信息")
    @ApiOperationSupport(ignoreParameters = {"lawyerDto.account","lawyerDto.pageNumber","lawyerDto.pageSize"})
    APIResponse lawyerUpdate(@RequestBody BusLawyerDto lawyerDto) {
        return busLawyerService.updateBusLawyer(lawyerDto);
    }

}
