package com.xinou.lawfrim.web.controller.web;


import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.web.config.WebLoginToken;
import com.xinou.lawfrim.web.dto.BusAgreementAuditDto;
import com.xinou.lawfrim.web.dto.BusAgreementDto;
import com.xinou.lawfrim.web.service.IBusAgreementAuditService;
import com.xinou.lawfrim.web.service.IBusAgreementService;
import com.xinou.lawfrim.web.service.IBusLawyerService;
import com.xinou.lawfrim.web.util.HeadersUtil;
import com.xinou.lawfrim.web.vo.agreement.AgreementTypeVo;
import com.xinou.lawfrim.web.vo.custom.CustomNumVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Wangxin
 * @since 2020-10-20
 */
@RestController
@RequestMapping("/web/statistics")
@Api(tags = {"客户-统计"})
public class CustomStatisticsController {

    @Autowired
    private IBusAgreementService agreementService;

    @PostMapping("StatisticsAgreementNum")
    @ApiOperation(httpMethod = "POST", value = "客户合同数（全部、处理中、已处理）")
    @WebLoginToken
    APIResponse<CustomNumVo> getCustomAgreementCount(HttpServletRequest request) {
        BusAgreementDto agreementDto = new BusAgreementDto();
        agreementDto.setCustomId(Integer.parseInt(HeadersUtil.getUserId(request).toString()));
        return agreementService.getCustomAgreementCount(agreementDto);
    }


    @PostMapping("statisticAgreementType")
    @ApiOperation(httpMethod = "POST", value = "合同类型分类统计")
    @WebLoginToken
    APIResponse<AgreementTypeVo> getAgreementTypeStatistic(HttpServletRequest request) {
        BusAgreementDto agreementDto = new BusAgreementDto();
        agreementDto.setCustomId(Integer.parseInt(HeadersUtil.getUserId(request).toString()));
        return agreementService.getAgreementTypeStatistic(agreementDto);
    }

}
