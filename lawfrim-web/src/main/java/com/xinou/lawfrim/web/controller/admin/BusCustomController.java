package com.xinou.lawfrim.web.controller.admin;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.Config;
import com.xinou.lawfrim.web.config.WebLoginToken;
import com.xinou.lawfrim.web.dto.BusAgreementDto;
import com.xinou.lawfrim.web.dto.BusCustomDto;
import com.xinou.lawfrim.web.dto.BusLawyerDto;
import com.xinou.lawfrim.web.entity.BusCustom;
import com.xinou.lawfrim.web.service.IBusAgreementService;
import com.xinou.lawfrim.web.service.IBusCustomService;
import com.xinou.lawfrim.web.util.HeadersUtil;
import com.xinou.lawfrim.web.vo.agreement.AgreementTypeVo;
import com.xinou.lawfrim.web.vo.custom.CustomVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
    @RequiresPermissions("/admin/custom/list")
    @ApiOperation(httpMethod = "POST", value = "客户列表")
    @ApiOperationSupport(includeParameters = {"customDto.name"})
    APIResponse<CustomVo> listCustom(@RequestBody BusCustomDto customDto) {
        return customService.listCustom(customDto);
    }


    @PostMapping("add")
    @RequiresPermissions("/admin/custom/add")
    @ApiOperation(httpMethod = "POST", value = "添加客户")
    @ApiOperationSupport(ignoreParameters = {"customDto.id","customDto.oldPassword"})
    APIResponse customAdd(@RequestBody BusCustomDto customDto) {
        return customService.addCustom(customDto);
    }


    @PostMapping("info")
    @RequiresPermissions("/admin/custom/info")
    @ApiOperation(httpMethod = "POST", value = "客户信息")
    @ApiOperationSupport(includeParameters = {"customDto.id"})
    APIResponse<CustomVo> customInfo(@RequestBody BusCustomDto customDto) {
        return customService.getCustom(customDto);
    }


    @PostMapping("update")
    @RequiresPermissions("/admin/custom/update")
    @ApiOperation(httpMethod = "POST", value = "修改客户密码")
    @ApiOperationSupport(includeParameters = {"customDto.id","customDto.password"})
    APIResponse customUpdate(@RequestBody BusCustomDto customDto) {
        return customService.AdminUpdateCustom(customDto);
    }

    @PostMapping("updateName")
    @RequiresPermissions("/admin/custom/updateName")
    @ApiOperation(httpMethod = "POST", value = "修改客户姓名")
    @ApiOperationSupport(includeParameters = {"customDto.id","customDto.name"})
    APIResponse customUpdateName(@RequestBody BusCustomDto customDto) {
        return customService.AdminUpdateCustomName(customDto);
    }


    @PostMapping("AdminExcelCustom")
    @RequiresPermissions("/admin/custom/AdminExcelCustom")
    @ApiOperation(httpMethod = "POST", value = "管理员-导出-客户列表")
    @ApiOperationSupport(includeParameters = {"customDto.name"})
    public APIResponse AdminAllExcelAgreement(@RequestBody BusCustomDto customDto)  {
        String fileName = customService.AdminExcelCustom(customDto);
        return new APIResponse(Config.BASE_URL+fileName);
    }
}
