package com.xinou.lawfrim.web.service.impl;

import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.web.dto.BusAgreementAuditDto;
import com.xinou.lawfrim.web.entity.BusAgreementAudit;
import com.xinou.lawfrim.web.mapper.BusAgreementAuditMapper;
import com.xinou.lawfrim.web.service.IBusAgreementAuditService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinou.lawfrim.web.service.IBusAgreementService;
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
    @Override
    public APIResponse<CustomNumVo> getLawyerAgreementCount(BusAgreementAuditDto agreementAuditDto) {
        CustomNumVo customNumVo = agreementAuditMapper.getLawyerAgreementCount(agreementAuditDto);
        return new APIResponse<>(customNumVo);
    }
}
