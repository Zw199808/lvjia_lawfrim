package com.xinou.lawfrim.web.controller;


import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.web.po.BusAgreementPO;
import io.swagger.annotations.Api;
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
@Api(tags = {"测试Swagger"})
@RestController
@RequestMapping("/agreement-audit")
public class BusAgreementAuditController {

    @PostMapping("test")
    APIResponse<Void> test(@RequestBody BusAgreementPO busAgreementPO){
        return new APIResponse<>( );
    }
}
