package com.xinou.lawfrim.web.controller.admin;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.web.dto.BusAgreementAuditDto;
import com.xinou.lawfrim.web.dto.BusAgreementDto;
import com.xinou.lawfrim.web.service.IBusAgreementAuditService;
import com.xinou.lawfrim.web.service.IBusAgreementService;
import com.xinou.lawfrim.web.vo.agreement.AgreementListVo;
import com.xinou.lawfrim.web.vo.agreement.AgreementVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @since 2020-10-16
 */
@RestController
@RequestMapping("/admin/agreement")
@Api(tags = {"律师法务-合同"})
public class BusAgreementController {

    @Autowired
    private IBusAgreementService agreementService;

    @Autowired
    private IBusAgreementAuditService agreementAuditService;


    @PostMapping("list")
//    @RequiresPermissions("/admin/agreement/list")
    @ApiOperation(httpMethod = "POST", value = "未领取合同列表")
    APIResponse<AgreementListVo> listAgreement() {
        BusAgreementDto agreementDto = new BusAgreementDto();
        agreementDto.setState(1);//发布状态
        return agreementService.listAgreement(agreementDto);
    }

    @PostMapping("acceptAgreement")
//    @RequiresPermissions("/admin/agreement/acceptAgreement")
    @ApiOperation(httpMethod = "POST", value = "接受合同")
    @ApiOperationSupport(includeParameters = {"agreement.id"})
    APIResponse acceptAgreement(HttpServletRequest request, @RequestBody BusAgreementDto agreement) {
        HttpSession session = request.getSession();
        Integer adminId = (Integer)session.getAttribute("sysUserId");
        BusAgreementAuditDto agreementAudit = new BusAgreementAuditDto();
        agreementAudit.setLawyerId(adminId);
        agreementAudit.setAgreementId(agreement.getId());
        return agreementAuditService.acceptAgreement(agreementAudit);
    }


    @PostMapping("agreementInfo")
//    @RequiresPermissions("/admin/agreement/agreementInfo")
    @ApiOperation(httpMethod = "POST", value = "合同详情")
    @ApiOperationSupport(includeParameters = {"agreement.id"})
    APIResponse agreementInfo(@RequestBody BusAgreementDto agreement) {
        return agreementService.getAgreementInfo(agreement);
    }

    @PostMapping("downloadAgreement")
    @ApiOperation(httpMethod = "POST", value = "下载合同")
    //    @RequiresPermissions("/admin/lawyer/downloadAgreement")
    @ApiOperationSupport(includeParameters = {"agreementDto.id"})
    APIResponse<AgreementVo> downloadAgreement(@RequestBody BusAgreementDto agreementDto) {
        return agreementService.downloadAgreement(agreementDto);
    }
}
