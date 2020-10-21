package com.xinou.lawfrim.web.service.impl;

import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.Config;
import com.xinou.lawfrim.web.dto.BusAgreementAuditDto;
import com.xinou.lawfrim.web.entity.BusAgreement;
import com.xinou.lawfrim.web.entity.BusAgreementAudit;
import com.xinou.lawfrim.web.entity.BusChangeRecord;
import com.xinou.lawfrim.web.mapper.BusAgreementAuditMapper;
import com.xinou.lawfrim.web.mapper.BusChangeRecordMapper;
import com.xinou.lawfrim.web.service.IBusAgreementAuditService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinou.lawfrim.web.service.IBusAgreementService;
import com.xinou.lawfrim.web.service.IBusChangeRecordService;
import com.xinou.lawfrim.web.service.IBusCustomService;
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

    @Override
    public APIResponse<CustomNumVo> getLawyerAgreementCount(BusAgreementAuditDto agreementAuditDto) {
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
}
