package com.xinou.lawfrim.web.service;

import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.web.dto.BusAgreementDto;
import com.xinou.lawfrim.web.dto.DownloadAgreementDto;
import com.xinou.lawfrim.web.entity.BusAgreement;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinou.lawfrim.web.vo.agreement.*;
import com.xinou.lawfrim.web.vo.custom.CustomNumVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
     * 合同类型分类统计
     * @param agreement
     * @return
     */
    APIResponse<AgreementTypeVo> getAgreementTypeStatistic(BusAgreementDto agreement);

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


    /**
     * 合同列表
     * @param
     * @return
     */
    APIResponse<AgreementListVo> listAgreement(BusAgreementDto agreementDto);

    /**
     * 合同列表
     * @param
     * @return
     */
    APIResponse<AgreementListVo> finishAgreement(BusAgreementDto agreementDto);

    /**
     * 合同详情
     * @param
     * @return
     */
    APIResponse<AgreementInfoVo> getAgreementInfo(BusAgreementDto agreementDto);

    /**
     * 合同详情
     * @param
     * @return
     */
    APIResponse<DownloadAgreementVo> downloadTwoAgreement(DownloadAgreementDto agreement);


    /**
     * 我的-合同列表
     * @param
     * @return
     */
    APIResponse<LawyerAgreementListVo> AllAgreementList(BusAgreementDto agreement);

    /**
     * 管理员-合同列表
     * @param
     * @return
     */
    APIResponse<LawyerAgreementListVo> getAllAgreementList(BusAgreementDto agreement);

    /**
     * 合同数统计
     * @param
     * @return
     */
    APIResponse<AgreementNumVo> getAgreementNumber();

    /**
     * 导出合同
     * @param agreement
     * @return
     */
    String LawyerExcelAgreement(BusAgreementDto agreement);

    /**
     * 管理员导出全部合同
     * @param agreement
     * @return
     */
    String AdminExcelAgreement(BusAgreementDto agreement);


    /**
     * 下载合同
     * @param
     * @return
     */
    APIResponse<AdminDownloadAgreementVo> adminDownloadAgreement(DownloadAgreementDto agreementDto);


}
