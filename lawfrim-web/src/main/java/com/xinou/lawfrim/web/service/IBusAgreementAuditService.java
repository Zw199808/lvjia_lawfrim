package com.xinou.lawfrim.web.service;

import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.web.dto.BusAgreementAuditDto;
import com.xinou.lawfrim.web.dto.BusAgreementScoreDto;
import com.xinou.lawfrim.web.dto.BusChangeRecordDto;
import com.xinou.lawfrim.web.dto.BusLawyerDto;
import com.xinou.lawfrim.web.entity.BusAgreementAudit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinou.lawfrim.web.entity.BusChangeRecord;
import com.xinou.lawfrim.web.vo.agreementAudit.ScoreVo;
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

    /**
     * 接受合同
     * @param
     * @return
     */
    APIResponse acceptAgreement(BusAgreementAuditDto agreementAuditDto);

    /**
     * 回复合同
     * @param
     * @return
     */
    APIResponse answerAgreement(BusAgreementAuditDto agreementAuditDto);

    /**
     * 转移合同
     * @param
     * @return
     */
    APIResponse changeAgreement(BusChangeRecordDto changeRecord);

    /**
     * 接受转移合同
     * @param
     * @return
     */
    APIResponse agreeChangeAgreement(BusChangeRecordDto changeRecord);

    /**
     * 接受转移合同
     * @param
     * @return
     */
    APIResponse<ScoreVo> getStatisticMyScore(BusLawyerDto lawyerDto);

    /**
     * 复审合同
     * @param
     * @return
     */
    APIResponse endAuditAgreement(BusAgreementScoreDto agreementScore);
}
