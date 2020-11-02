package com.xinou.lawfrim.web.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.Config;
import com.xinou.lawfrim.web.config.WebLoginToken;
import com.xinou.lawfrim.web.dto.*;
import com.xinou.lawfrim.web.entity.BusAgreement;
import com.xinou.lawfrim.web.entity.BusLawyer;
import com.xinou.lawfrim.web.service.IBusAgreementAuditService;
import com.xinou.lawfrim.web.service.IBusAgreementService;
import com.xinou.lawfrim.web.service.IBusLawyerService;
import com.xinou.lawfrim.web.vo.agreement.*;
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
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private IBusLawyerService lawyerService;



    @PostMapping("list")
    @RequiresPermissions("/admin/agreement/list")
    @ApiOperation(httpMethod = "POST", value = "未领取合同列表")
    APIResponse<AgreementListVo> listAgreement() {
        BusAgreementDto agreementDto = new BusAgreementDto();
        agreementDto.setState(1);//发布状态
        return agreementService.listAgreement(agreementDto);
    }

    @PostMapping("AllAgreementList")
    @RequiresPermissions("/admin/agreement/AllAgreementList")
    @ApiOperation(httpMethod = "POST", value = "我的-合同列表")
    @ApiOperationSupport(includeParameters = {"agreementDto.state","agreementDto.name"})
    APIResponse<LawyerAgreementListVo> AllAgreementList(HttpServletRequest request, @RequestBody BusAgreementDto agreementDto) {
        HttpSession session = request.getSession();
        Integer adminId  = (Integer) session.getAttribute("sysUserId");
        //根据adminId获取lawyerId
        BusLawyer lawyer = lawyerService.getOne(new QueryWrapper<BusLawyer>().eq("sys_user_id",adminId)
                .eq("is_delete",0));
        if (lawyer == null ){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        agreementDto.setLawyerId(lawyer.getId());
        return agreementService.AllAgreementList(agreementDto);
    }

    @PostMapping("acceptAgreement")
    @RequiresPermissions("/admin/agreement/acceptAgreement")
    @ApiOperation(httpMethod = "POST", value = "接受未领取合同")
    @ApiOperationSupport(includeParameters = {"agreement.id"})
    APIResponse acceptAgreement(HttpServletRequest request, @RequestBody BusAgreementDto agreement) {
        HttpSession session = request.getSession();
        Integer adminId = (Integer)session.getAttribute("sysUserId");
        BusAgreementAuditDto agreementAudit = new BusAgreementAuditDto();
        //根据adminId获取lawyerId
        BusLawyer lawyer = lawyerService.getOne(new QueryWrapper<BusLawyer>().eq("sys_user_id",adminId)
                .eq("is_delete",0));
        if (lawyer == null ){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        agreementAudit.setLawyerId(lawyer.getId());
        agreementAudit.setAgreementId(agreement.getId());
        return agreementAuditService.acceptAgreement(agreementAudit);
    }


    @PostMapping("agreementInfo")
    @RequiresPermissions("/admin/agreement/agreementInfo")
    @ApiOperation(httpMethod = "POST", value = "合同详情")
    @ApiOperationSupport(includeParameters = {"agreement.id"})
    APIResponse<AgreementInfoVo> agreementInfo(@RequestBody BusAgreementDto agreement) {
        return agreementService.getAgreementInfo(agreement);
    }

    @PostMapping("downloadAgreement")
    @ApiOperation(httpMethod = "POST", value = "下载合同")
    @RequiresPermissions("/admin/agreement/downloadAgreement")
    @ApiOperationSupport(includeParameters = {"agreementDto.id"})
    APIResponse<AgreementVo> downloadAgreement(@RequestBody BusAgreementDto agreementDto) {
        return agreementService.downloadAgreement(agreementDto);
    }

    @PostMapping("endLawyerList")
    @ApiOperation(httpMethod = "POST", value = "复审律师列表")
        @RequiresPermissions("/admin/agreement/endLawyerList")
    APIResponse endLawyerList() {
        return lawyerService.endListLawyer();
    }

    @PostMapping("answerAgreement")
    @ApiOperation(httpMethod = "POST", value = "回复合同")
    @RequiresPermissions("/admin/agreement/answerAgreement")
    @ApiOperationSupport(includeParameters = {"agreementAudit.agreementId","agreementAudit.lawyerId","agreementAudit.agreementType","agreementAudit.firstAgreementName","agreementAudit.secondAgreementName"})
    APIResponse agreementInfo(@RequestBody BusAgreementAuditDto agreementAudit) {
        return agreementAuditService.answerAgreement(agreementAudit);
    }

    @PostMapping("changeAgreement")
    @ApiOperation(httpMethod = "POST", value = "申请合同转移")
    @RequiresPermissions("/admin/agreement/changeAgreement")
    @ApiOperationSupport(includeParameters = {"changeRecord.lawyerId","changeRecord.agreementAuditId"})
    APIResponse changeAgreement(HttpServletRequest request,@RequestBody BusChangeRecordDto changeRecord) {
        HttpSession session = request.getSession();
        Integer adminId = (Integer) session.getAttribute("sysUserId");
        changeRecord.setAdminId(adminId);
        return agreementAuditService.changeAgreement(changeRecord);
    }

    @PostMapping("agreeChangeAgreement")
    @ApiOperation(httpMethod = "POST", value = "接受转移合同")
        @RequiresPermissions("/admin/agreement/agreeChangeAgreement")
    @ApiOperationSupport(includeParameters = {"changeRecord.agreementAuditId"})
    APIResponse agreeChangeAgreement(HttpServletRequest request,@RequestBody BusChangeRecordDto changeRecord) {
        HttpSession session = request.getSession();
        Integer adminId = (Integer) session.getAttribute("sysUserId");
        changeRecord.setAdminId(adminId);
        return agreementAuditService.agreeChangeAgreement(changeRecord);
    }

    @PostMapping("endAuditAgreement")
    @ApiOperation(httpMethod = "POST", value = "复审评分")
        @RequiresPermissions("/admin/agreement/endAuditAgreement")
    @ApiOperationSupport(includeParameters = {"agreementScore.agreementAuditId","agreementScore.score"})
    APIResponse endAuditAgreement(HttpServletRequest request,@RequestBody BusAgreementScoreDto agreementScore) {
        HttpSession session = request.getSession();
        Integer adminId = (Integer) session.getAttribute("sysUserId");
        BusLawyer lawyer = lawyerService.getOne(new QueryWrapper<BusLawyer>().eq("sys_user_id",adminId)
                .eq("is_delete",0));
        if (lawyer == null ){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        agreementScore.setLawyerId(lawyer.getId());
        return agreementAuditService.endAuditAgreement(agreementScore);
    }

    @PostMapping("AdminAgreementList")
    @RequiresPermissions("/admin/agreement/AdminAgreementList")
    @ApiOperation(httpMethod = "POST", value = "管理员-合同列表")
    @ApiOperationSupport(includeParameters = {"agreementDto.state","agreementDto.name"})
    APIResponse<LawyerAgreementListVo> AdminAgreementList(HttpServletRequest request, @RequestBody BusAgreementDto agreementDto) {
        return agreementService.getAllAgreementList(agreementDto);
    }

    @PostMapping("assignAgreement")
    @ApiOperation(httpMethod = "POST", value = "管理员-分配合同")
        @RequiresPermissions("/admin/agreement/assignAgreement")
    @ApiOperationSupport(includeParameters = {"agreementDto.lawyerId","agreementDto.id"})
    APIResponse assignAgreement(HttpServletRequest request,@RequestBody BusAgreementDto agreementDto) {
        HttpSession session = request.getSession();
        Integer adminId = (Integer) session.getAttribute("sysUserId");
        agreementDto.setAdminId(adminId);
        return agreementAuditService.assignAgreement(agreementDto);
    }

    @PostMapping("LawyerNewExcelAgreement")
    @RequiresPermissions("/admin/agreement/LawyerNewExcelAgreement")
    @ApiOperation(httpMethod = "POST", value = "律师法务-导出-今日新合同")
    public APIResponse LawyerNewExcelAgreement(HttpServletRequest request)  {
        BusAgreementDto agreementDto = new BusAgreementDto();
        HttpSession session = request.getSession();
        Integer adminId  = (Integer) session.getAttribute("sysUserId");
        //根据adminId获取lawyerId
        BusLawyer lawyer = lawyerService.getOne(new QueryWrapper<BusLawyer>().eq("sys_user_id",adminId)
                .eq("is_delete",0));
        if (lawyer == null ){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        agreementDto.setLawyerId(lawyer.getId());
        agreementDto.setTag(2);
        String fileName = agreementService.LawyerExcelAgreement(agreementDto);
        return new APIResponse(Config.BASE_URL+fileName);
    }

    @PostMapping("LawyerExcelAgreement")
    @RequiresPermissions("/admin/agreement/LawyerExcelAgreement")
    @ApiOperation(httpMethod = "POST", value = "律师法务-导出-未回复")
    public APIResponse LawyerExcelAgreement(HttpServletRequest request)  {
        BusAgreementDto agreementDto = new BusAgreementDto();
        HttpSession session = request.getSession();
        Integer adminId  = (Integer) session.getAttribute("sysUserId");
        //根据adminId获取lawyerId
        BusLawyer lawyer = lawyerService.getOne(new QueryWrapper<BusLawyer>().eq("sys_user_id",adminId)
                .eq("is_delete",0));
        if (lawyer == null ){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        agreementDto.setLawyerId(lawyer.getId());
        agreementDto.setState(2);
        agreementDto.setTag(1);
        String fileName = agreementService.LawyerExcelAgreement(agreementDto);
        return new APIResponse(Config.BASE_URL+fileName);
    }

    @PostMapping("AdminExcelAgreement")
    @RequiresPermissions("/admin/agreement/AdminExcelAgreement")
    @ApiOperation(httpMethod = "POST", value = "管理员-导出-未回复")
    public APIResponse AdminExcelAgreement()  {
        BusAgreementDto agreementDto = new BusAgreementDto();
        agreementDto.setState(2);
        agreementDto.setTag(1);
        String fileName = agreementService.LawyerExcelAgreement(agreementDto);
        return new APIResponse(Config.BASE_URL+fileName);
    }

    @PostMapping("AdminNewExcelAgreement")
    @RequiresPermissions("/admin/agreement/AdminNewExcelAgreement")
    @ApiOperation(httpMethod = "POST", value = "管理员-导出-今日新合同")
    public APIResponse AdminNewExcelAgreement()  {
        BusAgreementDto agreementDto = new BusAgreementDto();
        agreementDto.setTag(2);
        String fileName = agreementService.LawyerExcelAgreement(agreementDto);
        return new APIResponse(Config.BASE_URL+fileName);
    }

    @PostMapping("AdminAllExcelAgreement")
    @RequiresPermissions("/admin/agreement/AdminAllExcelAgreement")
    @ApiOperation(httpMethod = "POST", value = "管理员-导出-所有合同")
    public APIResponse AdminAllExcelAgreement()  {
        BusAgreementDto agreementDto = new BusAgreementDto();
        String fileName = agreementService.AdminExcelAgreement(agreementDto);
        return new APIResponse(Config.BASE_URL+fileName);
    }

    @PostMapping("adminDownloadAgreement")
    @ApiOperation(httpMethod = "POST", value = "管理员-下载回复合同")
    @RequiresPermissions("/admin/agreement/adminDownloadAgreement")
    @ApiOperationSupport(includeParameters = {"agreement.agreeId"})
    APIResponse<AdminDownloadAgreementVo> downloadAgreement(@RequestBody DownloadAgreementDto agreement) {
        return agreementService.adminDownloadAgreement(agreement);
    }
}
