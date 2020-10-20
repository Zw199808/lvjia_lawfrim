package com.xinou.lawfrim.web.service;

import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.web.dto.BusAgreementAuditDto;
import com.xinou.lawfrim.web.entity.BusAgreementAudit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinou.lawfrim.web.vo.custom.CustomNumVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Wangxin
 * @since 2020-10-16
 */
public interface IBusAgreementAuditService extends IService<BusAgreementAudit> {

    /**
     * 获取当前律师合同数
     * @param
     * @return
     */
    APIResponse<CustomNumVo> getLawyerAgreementCount(BusAgreementAuditDto agreementAuditDto);


}
