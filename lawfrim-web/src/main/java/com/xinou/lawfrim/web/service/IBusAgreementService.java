package com.xinou.lawfrim.web.service;

import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.web.dto.BusAgreementDto;
import com.xinou.lawfrim.web.dto.BusCustomDto;
import com.xinou.lawfrim.web.entity.BusAgreement;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinou.lawfrim.web.vo.agreement.AgreementVo;
import com.xinou.lawfrim.web.vo.custom.CustomNumVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Wangxin
 * @since 2020-10-16
 */
public interface IBusAgreementService extends IService<BusAgreement> {

    /**
     * 获取客户合同数
     * @param agreement
     * @return
     */
    APIResponse<CustomNumVo> getCustomAgreementCount(BusAgreementDto agreement);

    /**
     * 获取全部合同数
     * @param
     * @return
     */
    APIResponse<CustomNumVo> getAllAgreementCount();

    /**
     * 下载合同
     * @param
     * @return
     */
    APIResponse<AgreementVo> downloadAgreement(BusAgreementDto agreementDto);



}
