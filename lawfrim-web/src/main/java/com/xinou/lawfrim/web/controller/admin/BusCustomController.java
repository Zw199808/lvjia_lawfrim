package com.xinou.lawfrim.web.controller.admin;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.web.dto.BusCustomDto;
import com.xinou.lawfrim.web.service.IBusCustomService;
import com.xinou.lawfrim.web.vo.custom.CustomVo;
import io.swagger.annotations.Api;
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
@RequestMapping("/admin/custom")
@Api(tags = {"管理员-管理客户"})
public class BusCustomController {

    @Autowired
    private IBusCustomService customService;

    @PostMapping("list")
//    @RequiresPermissions("/admin/custom/list")
    @ApiOperation(httpMethod = "POST", value = "客户列表")
    @ApiOperationSupport(includeParameters = {"customDto.name"})
    APIResponse<CustomVo> listCustom(@RequestBody BusCustomDto customDto) {
        return customService.listCustom(customDto);
    }


    @PostMapping("add")
//    @RequiresPermissions("/web/custom/add")
    @ApiOperation(httpMethod = "POST", value = "添加客户")
    @ApiOperationSupport(ignoreParameters = {"customDto.id","customDto.oldPassword"})
    APIResponse customAdd(@RequestBody BusCustomDto customDto) {
        return customService.addCustom(customDto);
    }


    @PostMapping("info")
//    @RequiresPermissions("/web/custom/info")
    @ApiOperation(httpMethod = "POST", value = "客户信息")
    @ApiOperationSupport(includeParameters = {"customDto.id"})
    APIResponse<CustomVo> customInfo(@RequestBody BusCustomDto customDto) {
        return customService.getCustom(customDto);
    }


    @PostMapping("update")
//    @RequiresPermissions("/web/custom/update")
    @ApiOperation(httpMethod = "POST", value = "修改客户信息")
    @ApiOperationSupport(includeParameters = {"customDto.password","customDto.id"})
    APIResponse customUpdate(@RequestBody BusCustomDto customDto) {
        return customService.updateCustom(customDto);
    }

}
