package com.xinou.lawfrim.web.controller.web;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.web.config.WebLoginToken;
import com.xinou.lawfrim.web.dto.BusCustomDto;
import com.xinou.lawfrim.web.service.IBusCustomService;
import com.xinou.lawfrim.web.util.HeadersUtil;
import com.xinou.lawfrim.web.vo.CustomVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wangxin
 * @since 2020-10-19
 */
@RestController
@RequestMapping("/web/custom")
@Api(tags = {"客户"})
public class CustomController {

    @Autowired
    private IBusCustomService customService;

    @PostMapping("login")
    @ApiOperation(httpMethod = "POST", value = "客户登录")
    @ApiOperationSupport(includeParameters = {"customDto.password","customDto.account"})
    APIResponse listCustom(@RequestBody BusCustomDto customDto) {
        return customService.customLogin(customDto);
    }


    @PostMapping("info")
    @ApiOperation(httpMethod = "POST", value = "客户简介、个人信息")
    @WebLoginToken
    APIResponse<CustomVo> listCustom(HttpServletRequest request) {
        BusCustomDto busCustomDto = new BusCustomDto();
        busCustomDto.setId(Integer.parseInt(HeadersUtil.getUserId(request).toString()));
        return customService.getCustomInfo(busCustomDto);
    }
}
