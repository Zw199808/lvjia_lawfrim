package com.xinou.lawfrim.web.mapper;

import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.web.dto.BusAgreementAuditDto;
import com.xinou.lawfrim.web.entity.BusAgreementAudit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinou.lawfrim.web.vo.custom.CustomNumVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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

}
