package com.xinou.lawfrim.web.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.web.config.WebLoginToken;
import com.xinou.lawfrim.web.dto.BusAgreementAuditDto;
import com.xinou.lawfrim.web.dto.BusAgreementDto;
import com.xinou.lawfrim.web.dto.BusCustomDto;
import com.xinou.lawfrim.web.dto.BusLawyerDto;
import com.xinou.lawfrim.web.entity.BusLawyer;
import com.xinou.lawfrim.web.service.IBusAgreementAuditService;
import com.xinou.lawfrim.web.service.IBusAgreementService;
import com.xinou.lawfrim.web.service.IBusLawyerService;
import com.xinou.lawfrim.web.util.HeadersUtil;
import com.xinou.lawfrim.web.vo.custom.CustomNumVo;
import com.xinou.lawfrim.web.vo.lawyer.LawyerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/admin/statistics")
@Api(tags = {"律师法务统计"})
public class StatisticsController {

    @Autowired
    private IBusLawyerService lawyerService;

    @Autowired
    private IBusAgreementAuditService agreementAuditService;

    @Autowired
    private IBusAgreementService agreementService;


    @PostMapping("allAgreementNum")
    @ApiOperation(httpMethod = "POST", value = "全部合同数统计（已处理、待处理、全部）")
        //    @RequiresPermissions("/admin/statistics/allAgreementNum")
    APIResponse<CustomNumVo> getAllAgreementCount() {
        return agreementService.getAllAgreementCount();
    }

    @PostMapping("lawyerAgreementNum")
    @ApiOperation(httpMethod = "POST", value = "律师合同数统计（已处理、待处理、全部）")
        //    @RequiresPermissions("/admin/statistics/lawyerAgreementNum")
    APIResponse<CustomNumVo> getLawyerAgreementCount(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer adminId = (Integer) request.getAttribute("sysUserId");
        BusAgreementAuditDto agreementAuditDto = new BusAgreementAuditDto();
        agreementAuditDto.setLawyerId(adminId);
        return agreementAuditService.getLawyerAgreementCount(agreementAuditDto);
    }
}
