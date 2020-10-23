package com.xinou.lawfrim.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.web.dto.BusAgreementAuditDto;
import com.xinou.lawfrim.web.dto.BusAgreementDto;
import com.xinou.lawfrim.web.dto.BusLawyerDto;
import com.xinou.lawfrim.web.entity.BusAgreementAudit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinou.lawfrim.web.vo.agreement.AgreementListVo;
import com.xinou.lawfrim.web.vo.agreementAudit.ScoreVo;
import com.xinou.lawfrim.web.vo.custom.CustomNumVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Wangxin
 * @since 2020-10-16
 */
@Repository
public interface BusAgreementAuditMapper extends BaseMapper<BusAgreementAudit> {

    /**
     * 获取当前律师合同数
     * @param
     * @return
     */
    CustomNumVo getLawyerAgreementCount(@Param("condition")BusAgreementAuditDto agreementAuditDto);

    /**
     * 获取当前律师合同数
     * @param
     * @return
     */
    ScoreVo getStatisticScore(@Param("condition") BusLawyerDto lawyerDto);

}
