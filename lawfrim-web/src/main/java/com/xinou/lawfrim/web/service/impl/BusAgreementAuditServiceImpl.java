package com.xinou.lawfrim.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.Config;
import com.xinou.lawfrim.web.dto.*;
import com.xinou.lawfrim.web.entity.BusAgreement;
import com.xinou.lawfrim.web.entity.BusAgreementAudit;
import com.xinou.lawfrim.web.entity.BusChangeRecord;
import com.xinou.lawfrim.web.entity.BusLawyer;
import com.xinou.lawfrim.web.mapper.BusAgreementAuditMapper;
import com.xinou.lawfrim.web.mapper.BusChangeRecordMapper;
import com.xinou.lawfrim.web.mapper.BusLawyerMapper;
import com.xinou.lawfrim.web.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinou.lawfrim.web.vo.agreementAudit.ScoreVo;
import com.xinou.lawfrim.web.vo.custom.CustomNumVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wangxin
 * @since 2020-10-16
 */
@Primary
@Service
public class BusAgreementAuditServiceImpl extends ServiceImpl<BusAgreementAuditMapper, BusAgreementAudit> implements IBusAgreementAuditService {

    @Autowired
    private BusAgreementAuditMapper agreementAuditMapper;

    @Autowired
    private IBusChangeRecordService busChangeRecordService;

    @Autowired
    private IBusAgreementService agreementService;

    @Autowired
    private BusLawyerMapper lawyerMapper;

    @Override
    public APIResponse<CustomNumVo> getLawyerAgreementCount(BusAgreementAuditDto agreementAuditDto) {
        //根据adminId获取lawyerId
        BusLawyer lawyer = lawyerMapper.selectOne(new QueryWrapper<BusLawyer>().eq("is_delete",0)
                                                  .eq("sys_user_id",agreementAuditDto.getSysUserId()));
        if (lawyer == null){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        agreementAuditDto.setLawyerId(lawyer.getId());
        CustomNumVo customNumVo = agreementAuditMapper.getLawyerAgreementCount(agreementAuditDto);
        return new APIResponse<>(customNumVo);
    }

    @Override
    public APIResponse acceptAgreement(BusAgreementAuditDto agreementAuditDto) {
        BusAgreement busAgreement = agreementService.getById(agreementAuditDto.getAgreementId());
        if (busAgreement == null){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        busAgreement.setState(2);//将合同修改为初审状态
        boolean res = agreementService.updateById(busAgreement);
        if (!res){
            throw new RuntimeException("修改合同领取状态失败");
        }
        BusAgreementAudit busAgreementAudit = new BusAgreementAudit();
        busAgreementAudit.setAgreementId(agreementAuditDto.getAgreementId());
        busAgreementAudit.setLawyerId(agreementAuditDto.getLawyerId());
        res = save(busAgreementAudit);
        if (!res){
            throw new RuntimeException("领取合同失败");
        }
        BusChangeRecord changeRecord = new BusChangeRecord();
        changeRecord.setLawyerId(busAgreementAudit.getLawyerId());
        changeRecord.setAgreementAuditId(busAgreementAudit.getId());
        changeRecord.setType(1);
        res = busChangeRecordService.save(changeRecord);
        if (!res){
            throw new RuntimeException("领取合同失败");
        }
        return new APIResponse();
    }

    @Override
    public APIResponse answerAgreement(BusAgreementAuditDto agreementAuditDto) {
        BusAgreement agreement = agreementService.getById(agreementAuditDto.getAgreementId());
        if (agreement == null){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        agreement.setState(3);
        boolean res1 = agreementService.updateById(agreement);
        if (!res1){
            throw new RuntimeException("回复合同失败");
        }

        BusAgreementAudit agreementAudit = agreementAuditMapper.selectOne(new QueryWrapper<BusAgreementAudit>()
                                                                         .eq("agreement_id",agreementAuditDto.getAgreementId())
                                                                         .eq("is_delete",0));
        if (agreementAudit == null){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        agreementAudit.setFirstAgreementName(agreementAuditDto.getFirstAgreementName());//初审合同名
        agreementAudit.setSecondAgreementName(agreementAuditDto.getSecondAgreementName());//复审合同名
        agreementAudit.setType(agreementAuditDto.getAgreementType());
        agreementAudit.setEndLawyerId(agreementAuditDto.getLawyerId());
        agreementAudit.setFirstAuditTime(null);
        int res = agreementAuditMapper.updateById(agreementAudit);
        if (res<=0){
            throw new RuntimeException("回复合同失败");
        }
        return new APIResponse();
    }

    @Override
    public APIResponse changeAgreement(BusChangeRecordDto changeRecord) {
        //将审批表律师改为转移律师
//        BusAgreementAudit busAgreementAudit = agreementAuditMapper.selectById(changeRecord.getAgreementAuditId());
//        if (busAgreementAudit == null ){
//            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
//        }
//        busAgreementAudit.setLawyerId(changeRecord.getLawyerId());
//        int res1 = agreementAuditMapper.updateById(busAgreementAudit);
//        if (res1 <= 0){
//            throw new RuntimeException("修改审批表原律师失败");
//        }
        //将合同状态改为5：转移中
        BusAgreementAudit busAgreementAudit = agreementAuditMapper.selectById(changeRecord.getAgreementAuditId());
        if (busAgreementAudit == null ){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        BusAgreement agreement = agreementService.getById(busAgreementAudit.getAgreementId());
        if (busAgreementAudit == null ){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        agreement.setState(5);
        boolean res = agreementService.updateById(agreement);
        if (!res){
            throw  new RuntimeException("修改合同状态失败");
        }
        BusChangeRecord changeRecord1 = new BusChangeRecord();
        changeRecord1.setType(2);//类型为转移
        changeRecord1.setAgreementAuditId(changeRecord.getAgreementAuditId());//审批表id
        changeRecord1.setLawyerId(changeRecord.getLawyerId());//律师id
        changeRecord1.setOldOrAssignLawyerId(changeRecord.getAdminId());//原律师、分配律师
        changeRecord1.setState(1);
        res = busChangeRecordService.save(changeRecord1);
        if (!res){
            throw  new RuntimeException("转移合同失败");
        }
        //将审批表律师修改为转移律师
        busAgreementAudit.setLawyerId(changeRecord.getLawyerId());
        busAgreementAudit.setState(1);//将审批表转移状态改为未接收
        int res1 = agreementAuditMapper.updateById(busAgreementAudit);
        if (res1 <= 0 ){
            throw  new RuntimeException("转移合同-修改审批信息失败");
        }
        return new APIResponse();
    }

    @Override
    public APIResponse agreeChangeAgreement(BusChangeRecordDto changeRecord) {
        //修改合同审核表信息
        BusAgreementAudit agreementAudit = agreementAuditMapper.selectById(changeRecord.getAgreementAuditId());
        if (agreementAudit == null ){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        //根据adminId获取lawyerId
        BusLawyer lawyer = lawyerMapper.selectOne(new QueryWrapper<BusLawyer>().eq("sys_user_id",changeRecord.getAdminId())
                                                 .eq("is_delete",0));
        if (lawyer == null ){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
//        agreementAudit.setLawyerId(lawyer.getId());
        agreementAudit.setState(2);//将合同状态改为已接收
        int res = agreementAuditMapper.updateById(agreementAudit);
        if (res <= 0 ){
            throw new RuntimeException("修改合同转移信息失败");
        }
        //修改合同信息
        BusAgreement agreement = agreementService.getById(agreementAudit.getAgreementId());
        if (agreement == null ){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        agreement.setState(2);
        boolean res1 = agreementService.updateById(agreement);
        if (!res1){
            throw new RuntimeException("修改合同状态失败");
        }
        //修改转移纪律表信息
        BusChangeRecord changeRecord1 = busChangeRecordService.getOne(new QueryWrapper<BusChangeRecord>()
                                                                     .eq("is_delete",0)
                                                                     .eq("lawyer_id",lawyer.getId())
                                                                     .eq("agreement_audit_id",changeRecord.getAgreementAuditId())
                                                                     .eq("type",2));
        changeRecord1.setState(2);
        boolean res2 = busChangeRecordService.updateById(changeRecord1);
        if (!res2){
            throw new RuntimeException("修改转移分配表状态失败");
        }
        return new APIResponse();
    }

    @Override
    public APIResponse<ScoreVo> getStatisticMyScore(BusLawyerDto lawyerDto) {
        ScoreVo scoreVo = agreementAuditMapper.getStatisticScore(lawyerDto);
        return new APIResponse<>(scoreVo);
    }

    @Override
    public APIResponse endAuditAgreement(BusAgreementScoreDto agreementScore) {
        BusAgreementAudit agreementAudit = getById(agreementScore.getAgreementAuditId());
        if (agreementAudit == null){
            return new APIResponse(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        agreementAudit.setScore(agreementScore.getScore());
        int res = agreementAuditMapper.updateById(agreementAudit);
        if (res <= 0){
            throw new RuntimeException("评分失败");
        }
        return new APIResponse();
    }

    @Override
    public APIResponse assignAgreement(BusAgreementDto agreementDto) {
        BusAgreement busAgreement = agreementService.getById(agreementDto.getId());
        if (busAgreement == null){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        busAgreement.setState(2);//将合同修改为初审状态
        boolean res = agreementService.updateById(busAgreement);
        if (!res){
            throw new RuntimeException("修改合同领取状态失败");
        }
        BusAgreementAudit busAgreementAudit = new BusAgreementAudit();
        busAgreementAudit.setAgreementId(busAgreement.getId());
        busAgreementAudit.setLawyerId(agreementDto.getLawyerId());
        res = save(busAgreementAudit);
        if (!res){
            throw new RuntimeException("分配合同失败");
        }
        //根据adminId获取lawyerId
        BusLawyer lawyer1 = lawyerMapper.selectOne(new QueryWrapper<BusLawyer>().eq("sys_user_id",agreementDto.getAdminId())
                .eq("is_delete",0));
        if (lawyer1 == null ){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        BusChangeRecord changeRecord = new BusChangeRecord();
        changeRecord.setLawyerId(busAgreement.getId());
        changeRecord.setAgreementAuditId(busAgreementAudit.getId());
        changeRecord.setOldOrAssignLawyerId(lawyer1.getId());
        changeRecord.setType(3);
        res = busChangeRecordService.save(changeRecord);
        if (!res){
            throw new RuntimeException("分配合同失败");
        }
        return new APIResponse();
    }

}
