package com.xinou.lawfrim.web.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.web.config.WebLoginToken;
import com.xinou.lawfrim.web.dto.BusCustomDto;
import com.xinou.lawfrim.web.dto.BusLawyerDto;
import com.xinou.lawfrim.web.entity.BusLawyer;
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
@Api(tags = {"律师/法务-统计"})
public class StatisticsController {

    @Autowired
    private IBusLawyerService lawyerService;

    @Autowired
    private IBusAgreementService agreementService;

    @PostMapping("agreementNumByUserId")
    @ApiOperation(httpMethod = "POST", value = "法务合同数统计（已处理、处理中、全部）")
    @WebLoginToken
    APIResponse<CustomNumVo> getCustomAgreementCount(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer adminId = (Integer)session.getAttribute("sysUserId");
        return null;
    }

    @PostMapping("agreementNum")
    @ApiOperation(httpMethod = "POST", value = "全部合同数统计（已处理、待处理、全部）")
        //    @RequiresPermissions("/admin/statistics/agreementNum")
    APIResponse<CustomNumVo> getAllAgreementCount() {
        return agreementService.getAllAgreementCount();
    }

}
