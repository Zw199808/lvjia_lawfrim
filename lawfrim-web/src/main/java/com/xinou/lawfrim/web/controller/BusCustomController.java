package com.xinou.lawfrim.web.controller;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.web.dto.BusCustomDto;
import com.xinou.lawfrim.web.service.IBusCustomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/web/custom")
@Api(tags = {"客户"})
public class BusCustomController {

    @Autowired
    private IBusCustomService customService;

    @PostMapping("list")
//    @RequiresPermissions("/web/custom/list")
    APIResponse listCustom(@RequestBody BusCustomDto customDto) {
        return customService.listCustom(customDto);
    }


    @PostMapping("add")
//    @RequiresPermissions("/web/custom/add")
    @ApiOperation(httpMethod = "POST", value = "添加客户")
    @ApiOperationSupport(ignoreParameters = {"customDto.id"})
    APIResponse customAdd(@RequestBody BusCustomDto customDto) {
        return customService.addCustom(customDto);
    }


    @PostMapping("info")
//    @RequiresPermissions("/web/custom/info")
    @ApiOperation(httpMethod = "POST", value = "客户信息")
    @ApiOperationSupport(includeParameters = {"customDto.id"})
    APIResponse customInfo(@RequestBody BusCustomDto customDto) {
        return customService.getCustom(customDto);
    }


    @PostMapping("update")
//    @RequiresPermissions("/web/custom/update")
    @ApiOperation(httpMethod = "POST", value = "修改客户信息")
    @ApiOperationSupport(ignoreParameters = {"custom.account"})
    APIResponse customUpdate(@RequestBody BusCustomDto customDto) {
        return customService.updateCustom(customDto);
    }

}
