package com.xinou.lawfrim.web.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.sso.entity.SYSUser;
import com.xinou.lawfrim.web.config.WebLoginToken;
import com.xinou.lawfrim.web.controller.QNUtilController;
import com.xinou.lawfrim.web.dto.BusAgreementDto;
import com.xinou.lawfrim.web.dto.BusLawyerDto;
import com.xinou.lawfrim.web.entity.BusLawyer;
import com.xinou.lawfrim.web.service.IBusAgreementService;
import com.xinou.lawfrim.web.service.IBusLawyerService;
import com.xinou.lawfrim.web.util.HeadersUtil;
import com.xinou.lawfrim.web.vo.agreement.AgreementVo;
import com.xinou.lawfrim.web.vo.custom.CustomNumVo;
import com.xinou.lawfrim.web.vo.lawyer.LawyerSimpleVo;
import com.xinou.lawfrim.web.vo.lawyer.LawyerVo;
import io.swagger.annotations.*;
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
@RequestMapping("/admin/lawyer")
@Api(tags = {"律师"})
public class BusLawyerController {

    @Autowired
    private IBusLawyerService busLawyerService;

    @Autowired
    private IBusAgreementService agreementService;

    @PostMapping("list")
//    @RequiresPermissions("/admin/lawyer/list")
    @ApiOperation(httpMethod = "POST", value = "律师列表")
    @ApiOperationSupport(includeParameters = {"lawyerDto.name"})
    APIResponse<LawyerVo> listLawyer(@RequestBody BusLawyerDto lawyerDto) {
        return busLawyerService.listLawyer(lawyerDto);
    }


    @PostMapping("add")
//    @RequiresPermissions("/admin/lawyer/add")
    @ApiOperation(httpMethod = "POST", value = "添加律师")
    @ApiOperationSupport(ignoreParameters = {"lawyerDto.id","lawyerDto.state","lawyerDto.pageNumber","lawyerDto.pageSize","lawyerDto.oldPassword","lawyerDto.sysUserId"})
    APIResponse lawyerAdd(@RequestBody BusLawyerDto lawyerDto) {
        return busLawyerService.addBusLawyer(lawyerDto);
    }


    @PostMapping("info")
//    @RequiresPermissions("/admin/lawyer/info")
    @ApiOperation(httpMethod = "POST", value = "获取律师信息")
    @ApiOperationSupport(includeParameters = {"lawyerDto.id"})
    APIResponse<LawyerVo> lawyerInfo(@RequestBody BusLawyerDto lawyerDto) {
        return busLawyerService.getBusLawyer(lawyerDto);
    }


    @PostMapping("updateState")
//    @RequiresPermissions("/admin/lawyer/updateState")
    @ApiOperation(httpMethod = "POST", value = "修改律师在线状态")
    @ApiOperationSupport(includeParameters = {"lawyerDto.state"})
    APIResponse lawyerUpdate(HttpServletRequest request,@RequestBody BusLawyerDto lawyerDto) {
        HttpSession session = request.getSession();
        int adminId = (Integer) session.getAttribute("sysUserId");
        BusLawyer lawyer = busLawyerService.getOne(new QueryWrapper<BusLawyer>().eq("sys_user_id",adminId));
        lawyerDto.setId(lawyer.getId());
        return busLawyerService.updateBusLawyer(lawyerDto);
    }


    @PostMapping("updatePassword")
//    @RequiresPermissions("/admin/lawyer/updatePassword")
    @ApiOperation(httpMethod = "POST", value = "修改律师、管理员登录密码")
    @ApiOperationSupport(includeParameters = {"lawyerDto.password","lawyerDto.oldPassword"})
    APIResponse lawyerUpdatePassword(HttpServletRequest request,@RequestBody BusLawyerDto lawyerDto) {
        HttpSession session = request.getSession();
        int adminId = (Integer) session.getAttribute("sysUserId");
        lawyerDto.setSysUserId(adminId);
        return busLawyerService.updateBusLawyerPassword(lawyerDto);
    }


    @PostMapping("get")
//    @RequiresPermissions("/admin/lawyer/get")
    @ApiOperation("获取律师姓名、职务、个人信息")
    APIResponse<LawyerVo> getLawyer(HttpServletRequest request) {
        BusLawyerDto lawyerDto = new BusLawyerDto();
        HttpSession session = request.getSession();
        int adminId = (Integer) session.getAttribute("sysUserId");
        BusLawyer lawyer = busLawyerService.getOne(new QueryWrapper<BusLawyer>().eq("sys_user_id",adminId));
        lawyerDto.setId(lawyer.getId());
        return busLawyerService.getBusLawyer(lawyerDto);
//        LawyerSimpleVo vo = new LawyerSimpleVo();
//        BeanUtil.copyProperties(busLawyerService.getBusLawyer(lawyerDto).getDataInfo(), vo);
//        return new APIResponse<>(vo);
    }


    @PostMapping("downloadAgreement")
    @ApiOperation(httpMethod = "POST", value = "下载合同")
    //    @RequiresPermissions("/admin/lawyer/downloadAgreement")
    @ApiOperationSupport(includeParameters = {"agreementDto.id"})
    APIResponse<AgreementVo> downloadAgreement(@RequestBody BusAgreementDto agreementDto) {
        return agreementService.downloadAgreement(agreementDto);
    }


}
