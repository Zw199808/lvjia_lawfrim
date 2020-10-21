package com.xinou.lawfrim.web.controller.web;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.web.config.WebLoginToken;
import com.xinou.lawfrim.web.dto.BusAgreementAuditDto;
import com.xinou.lawfrim.web.dto.BusAgreementDto;
import com.xinou.lawfrim.web.service.IBusAgreementAuditService;
import com.xinou.lawfrim.web.service.IBusAgreementService;
import com.xinou.lawfrim.web.service.IBusCustomService;
import com.xinou.lawfrim.web.util.HeadersUtil;
import com.xinou.lawfrim.web.vo.agreement.AgreementListVo;
import com.xinou.lawfrim.web.vo.custom.CustomNumVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Wangxin
 * @since 2020-10-16
 */
@RestController
@RequestMapping("/web/agreement")
@Api(tags = {"客户-合同"})
public class AgreementController {

    @Autowired
    private IBusAgreementService agreementService;

    @Autowired
    private IBusCustomService customService;


    @PostMapping("progressList")
    @WebLoginToken
    @ApiOperation(httpMethod = "POST", value = "进行中合同列表")
    APIResponse<AgreementListVo> progressListAgreement(HttpServletRequest request) {
        BusAgreementDto agreementDto = new BusAgreementDto();
        List<Integer> state = new ArrayList<>();
        state.add(1);
        state.add(2);
        state.add(3);
        agreementDto.setStateList(state);//发布状态
        agreementDto.setCustomId(Integer.parseInt(HeadersUtil.getUserId(request).toString()));
        return agreementService.listAgreement(agreementDto);
    }

    @PostMapping("finishListAgreement")
    @WebLoginToken
    @ApiOperation(httpMethod = "POST", value = "已完成合同列表")
    APIResponse<AgreementListVo> finishListAgreement(HttpServletRequest request) {
        BusAgreementDto agreementDto = new BusAgreementDto();
        agreementDto.setState(4);
        agreementDto.setCustomId(Integer.parseInt(HeadersUtil.getUserId(request).toString()));
        return agreementService.finishAgreement(agreementDto);
    }

    @PostMapping("addAgreement")
    @ApiOperation(httpMethod = "POST", value = "上传合同")
    @WebLoginToken
    @ApiOperationSupport(includeParameters = {"agreementDto.audit","agreementDto.endTime","agreementDto.remark","agreementDto.name"})
    APIResponse<CustomNumVo> addAgreement(HttpServletRequest request, @RequestBody BusAgreementDto agreementDto) {
        agreementDto.setCustomId(Integer.parseInt(HeadersUtil.getUserId(request).toString()));
        return customService.addAgreement(agreementDto);
    }

}
