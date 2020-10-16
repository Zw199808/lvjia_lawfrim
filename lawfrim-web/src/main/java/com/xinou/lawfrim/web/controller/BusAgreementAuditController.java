package com.xinou.lawfrim.web.controller;


import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.web.entity.BusAgreement;
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
@Api("cshi")
@RestController
@RequestMapping("//bus-agreement-audit")
public class BusAgreementAuditController {

    @PostMapping
    APIResponse<Void> test(@RequestBody BusAgreement busAgreement){
        return new APIResponse<>( );
    }
}
