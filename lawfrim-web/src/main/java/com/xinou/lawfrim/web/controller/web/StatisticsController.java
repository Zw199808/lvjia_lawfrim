package com.xinou.lawfrim.web.controller.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.web.dto.BusAgreementAuditDto;
import com.xinou.lawfrim.web.dto.BusAgreementDto;
import com.xinou.lawfrim.web.dto.BusLawyerDto;
import com.xinou.lawfrim.web.entity.BusLawyer;
import com.xinou.lawfrim.web.service.IBusAgreementAuditService;
import com.xinou.lawfrim.web.service.IBusAgreementService;
import com.xinou.lawfrim.web.service.IBusLawyerService;
import com.xinou.lawfrim.web.vo.agreement.AgreementTypeVo;
import com.xinou.lawfrim.web.vo.agreementAudit.ScoreVo;
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
 * @since 2020-10-23
 */
@RestController
@RequestMapping("/admin/LawyerStatistics")
@Api(tags = {"律师法务-统计"})
public class StatisticsController {

    @Autowired
    private IBusLawyerService lawyerService;

    @Autowired
    private IBusAgreementAuditService agreementAuditService;

    @Autowired
    private IBusAgreementService agreementService;

    @Autowired
    private IBusLawyerService busLawyerService;


    @PostMapping("lawyerAgreementNum")
    @ApiOperation(httpMethod = "POST", value = "律师合同数统计（已处理、待处理、全部）")
        //    @RequiresPermissions("/web/statistics/lawyerAgreementNum")
    APIResponse<CustomNumVo> getLawyerAgreementCount(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer adminId = (Integer) session.getAttribute("sysUserId");
        BusAgreementAuditDto agreementAuditDto = new BusAgreementAuditDto();
        agreementAuditDto.setSysUserId(adminId);
        return agreementAuditService.getLawyerAgreementCount(agreementAuditDto);
    }


    @PostMapping("statisticMyScore")
    //    @RequiresPermissions("/web/statistics/statisticMyScore")
    @ApiOperation(httpMethod = "POST", value = "我的评分统计")
    APIResponse<ScoreVo> getStatisticMyScore(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer adminId = (Integer) session.getAttribute("sysUserId");
        BusLawyerDto lawyerDto = new BusLawyerDto();
        BusLawyer lawyer = busLawyerService.getOne(new QueryWrapper<BusLawyer>().eq("sys_user_id",adminId));
        lawyerDto.setId(lawyer.getId());
        return agreementAuditService.getStatisticMyScore(lawyerDto);
    }

}
