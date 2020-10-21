package com.xinou.lawfrim.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.Config;
import com.xinou.lawfrim.common.util.TimeChange;
import com.xinou.lawfrim.web.dto.BusAgreementDto;
import com.xinou.lawfrim.web.entity.*;
import com.xinou.lawfrim.web.mapper.*;
import com.xinou.lawfrim.web.service.IBusAgreementAuditService;
import com.xinou.lawfrim.web.service.IBusAgreementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinou.lawfrim.web.util.upLoadFile;
import com.xinou.lawfrim.web.vo.agreement.AgreementInfoVo;
import com.xinou.lawfrim.web.vo.agreement.AgreementListVo;
import com.xinou.lawfrim.web.vo.agreement.AgreementVo;
import com.xinou.lawfrim.web.vo.custom.CustomNumVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class BusAgreementServiceImpl extends ServiceImpl<BusAgreementMapper, BusAgreement> implements IBusAgreementService {

    @Autowired
    private BusAgreementMapper agreementMapper;

    @Autowired
    private BusAgreementAuditMapper agreementAuditMapper;

    @Autowired
    private BusCustomMapper customMapper;

    @Autowired
    private BusChangeRecordMapper changeRecordMapper;

    @Autowired
    private BusLawyerMapper lawyerMapper;

    @Override
    public APIResponse<CustomNumVo> getCustomAgreementCount(BusAgreementDto agreement) {
        //根据id查询该对象上传的合同总数
        Integer agreementCount = count(new QueryWrapper<BusAgreement>()
                .eq("is_delete",0)
                .eq("custom_id",agreement.getCustomId()));
        Integer auditCount = count(new QueryWrapper<BusAgreement>()
                .eq("is_delete",0)
                .eq("custom_id",agreement.getCustomId())
                .eq("state",4));
        Integer notAuditCount = count(new QueryWrapper<BusAgreement>()
                .eq("is_delete",0)
                .eq("custom_id",agreement.getCustomId())
                .in("state",2,3));
        CustomNumVo customNumVo = new CustomNumVo();
        customNumVo.setAgreeNum(agreementCount);
        customNumVo.setAuditAgreement(auditCount);
        customNumVo.setNotAuditAgreement(notAuditCount);

        return new APIResponse<>(customNumVo);
    }

    @Override
    public APIResponse<CustomNumVo> getAllAgreementCount() {
        //根据id查询该对象上传的合同总数
        Integer agreementCount = count(new QueryWrapper<BusAgreement>()
                .eq("is_delete",0));
        Integer auditCount = count(new QueryWrapper<BusAgreement>()
                .eq("is_delete",0)
                .eq("state",4));
        Integer notAuditCount = count(new QueryWrapper<BusAgreement>()
                .eq("is_delete",0)
                .in("state",2,3));
        CustomNumVo customNumVo = new CustomNumVo();
        customNumVo.setAgreeNum(agreementCount);
        customNumVo.setAuditAgreement(auditCount);
        customNumVo.setNotAuditAgreement(notAuditCount);

        return new APIResponse<>(customNumVo);
    }

    @Override
    public APIResponse<AgreementVo> downloadAgreement(BusAgreementDto agreementDto) {
        BusAgreement agreement = getById(agreementDto.getId());
        if (agreement == null){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        String url = upLoadFile.resourcesCode(agreement.getName());
        AgreementVo agreementVo = new AgreementVo();
        agreementVo.setUrl(url);
        return new APIResponse<>(agreementVo);
    }

    @Override
    public APIResponse<AgreementListVo> listAgreement(BusAgreementDto agreementDto) {
        Page<BusAgreementDto> page = new Page<>(agreementDto.getPageNumber(), agreementDto.getPageSize());
        List<AgreementListVo> list = agreementMapper.getList(page, agreementDto);
        Integer total = agreementMapper.getTotal(agreementDto);
        Map<String, Object> map = new HashMap<>(2);
        if (list.size() == 0) {
            map.put("dataList", new ArrayList<>());
            map.put("total", 0);
            return new APIResponse(map);
        }
        map.put("dataList", list);
        map.put("total", total);
        return new APIResponse(map);
    }

    @Override
    public APIResponse<AgreementListVo> finishAgreement(BusAgreementDto agreementDto) {
        Page<BusAgreementDto> page = new Page<>(agreementDto.getPageNumber(), agreementDto.getPageSize());
        List<AgreementListVo> list = agreementMapper.getList(page, agreementDto);
        Integer total = agreementMapper.getTotal(agreementDto);
        for (AgreementListVo agreementListVo :list){
              BusAgreementAudit agreementAudit = agreementAuditMapper.selectOne(new QueryWrapper<BusAgreementAudit>()
                                                                            .eq("agreement_id",agreementListVo.getAgreeId())
                                                                            .eq("is_delete",0));
              if (agreementAudit == null){
                  return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
              }
              agreementListVo.setFirstAgreeName(agreementAudit.getFirstAgreementName());
              agreementListVo.setEndAgreeName(agreementAudit.getFirstAgreementName());
        }
        Map<String, Object> map = new HashMap<>(2);
        if (list.size() == 0) {
            map.put("dataList", new ArrayList<>());
            map.put("total", 0);
            return new APIResponse(map);
        }
        map.put("dataList", list);
        map.put("total", total);
        return new APIResponse(map);
    }

    @Override
    public APIResponse<AgreementInfoVo> getAgreementInfo(BusAgreementDto agreementDto) {
        BusAgreement agreement = getById(agreementDto.getId());
        if (agreement == null){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        //查询合同上传人姓名
        BusCustom busCustom = customMapper.selectById(agreement.getCustomId());
        if (agreement == null){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }

        AgreementInfoVo agreementInfoVo = new AgreementInfoVo();
        agreementInfoVo.setAgreeName(agreement.getName());
        agreementInfoVo.setCustomName(busCustom.getName());
        agreementInfoVo.setAgreeAudit(agreement.getAudit());
        agreementInfoVo.setApplyTime(TimeChange.timeChangeString(agreement.getGmtCreate()));//上传时间
        agreementInfoVo.setEndTime(TimeChange.timeChangeString(agreement.getEndTime()));//截止时间
        agreementInfoVo.setState(agreement.getState());//合同状态
        if (agreement.getState() != 1){
            //查询合同初审律师   初审接受时间
            BusAgreementAudit agreementAudit = agreementAuditMapper.selectOne(new QueryWrapper<BusAgreementAudit>()
                                                                             .eq("is_delete",0)
                                                                              .eq("agreement_id",agreement.getId()));
            if (agreementAudit == null){
                return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
            }
            BusChangeRecord changeRecord = changeRecordMapper.selectOne(new QueryWrapper<BusChangeRecord>()
                                                                       .eq("is_delete",0)
                                                                       .eq("agreement_audit_id",agreementAudit.getId()));
            //获取初审律师姓名
            if (agreement.getState() != 2){
                BusLawyer lawyer = lawyerMapper.selectById(agreementAudit.getLawyerId());
                agreementInfoVo.setFirstLawyerName(lawyer.getName());//初审律师姓名
            }
            agreementInfoVo.setAgreeType(agreementAudit.getType());//合同类型---回复后才会有
            //初审接受时间
            agreementInfoVo.setGmtCreate(TimeChange.timeChangeString(agreementAudit.getGmtCreate()));
            //初审回复时间
            agreementInfoVo.setFirstAuditTime(TimeChange.timeChangeString(agreementAudit.getFirstAuditTime()));
            //等待复审 或完成
            if (agreement.getState() == 3 || agreement.getState() == 4 ){
                BusLawyer lawyer1 = lawyerMapper.selectById(agreementAudit.getEndLawyerId());
                agreementInfoVo.setFirstLawyerName(lawyer1.getName());//复审律师姓名
            }
            //复审回复时间
            if (agreement.getState() == 4){
                agreementInfoVo.setGmtModified(TimeChange.timeChangeString(agreementAudit.getGmtModified()));//审核完成--修改时间为复审时间
            }else{
                agreementInfoVo.setGmtModified("");//未审核完成复审时间为空
            }
            if (changeRecord.getType() == 2){//转移，查询转移律师
                BusLawyer lawyer2 = lawyerMapper.selectById(changeRecord.getOldOrAssignLawyerId());
                agreementInfoVo.setChangeLawyerName(lawyer2.getName());
                agreementInfoVo.setChangeTime(TimeChange.timeChangeString(changeRecord.getGmtModified()));
            }
        }
        return new APIResponse<>(agreementInfoVo);
    }

}
