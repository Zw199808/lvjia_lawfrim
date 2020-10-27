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
import com.xinou.lawfrim.web.vo.UserNumberVo;
import com.xinou.lawfrim.web.vo.agreement.AgreementNumVo;
import com.xinou.lawfrim.web.vo.agreement.AgreementTypeVo;
import com.xinou.lawfrim.web.vo.agreementAudit.ScoreVo;
import com.xinou.lawfrim.web.vo.custom.CustomNumVo;
import com.xinou.lawfrim.web.vo.lawyer.LawyerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@Api(tags = {"管理员-统计"})
public class BusStatisticsController {

    @Autowired
    private IBusLawyerService lawyerService;

    @Autowired
    private IBusAgreementAuditService agreementAuditService;

    @Autowired
    private IBusAgreementService agreementService;


    @PostMapping("allAgreementNum")
    @ApiOperation(httpMethod = "POST", value = "全部合同数统计（已处理、待处理、全部）")
    @RequiresPermissions("/admin/statistics/allAgreementNum")
    APIResponse<CustomNumVo> getAllAgreementCount() {
        return agreementService.getAllAgreementCount();
    }


    @PostMapping("statisticAgreementType")
    @RequiresPermissions("/admin/statistics/statisticAgreementType")
    @ApiOperation(httpMethod = "POST", value = "合同类型分类统计")
    APIResponse<AgreementTypeVo> getAgreementTypeStatistic(HttpServletRequest request) {
        BusAgreementDto agreementDto = new BusAgreementDto();
        return agreementService.getAgreementTypeStatistic(agreementDto);
    }

    @PostMapping("statisticMyScore")
    @RequiresPermissions("/admin/statistics/statisticMyScore")
    @ApiOperation(httpMethod = "POST", value = "全部评分统计")
    APIResponse<ScoreVo> getStatisticMyScore(HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        Integer adminId = (Integer) session.getAttribute("sysUerId");
        BusLawyerDto lawyerDto = new BusLawyerDto();
//        lawyerDto.setId(adminId);
        return agreementAuditService.getStatisticMyScore(lawyerDto);
    }

    @PostMapping("userNumber")
    @RequiresPermissions("/admin/statistics/userNumber")
    @ApiOperation(httpMethod = "POST", value = "用户数量统计")
    APIResponse<UserNumberVo> getUserNumber() {
        return lawyerService.statisticUserNumber();
    }

    @PostMapping("agreementNumber")
    @RequiresPermissions("/admin/statistics/agreementNumber")
    @ApiOperation(httpMethod = "POST", value = "合同数量统计")
    APIResponse<AgreementNumVo> getAgreementNumber() {
        return agreementService.getAgreementNumber();
    }

}
