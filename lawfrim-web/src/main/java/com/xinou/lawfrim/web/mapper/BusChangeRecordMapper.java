package com.xinou.lawfrim.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinou.lawfrim.web.dto.BusAgreementAuditDto;
import com.xinou.lawfrim.web.dto.BusAgreementDto;
import com.xinou.lawfrim.web.entity.BusChangeRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinou.lawfrim.web.vo.agreement.AgreementListVo;
import com.xinou.lawfrim.web.vo.agreement.LawyerAgreementListVo;
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
public interface BusChangeRecordMapper extends BaseMapper<BusChangeRecord> {

    /**
     * 列表-分页
     * @param page
     * @param agreementDto
     * @return
     */
    List<LawyerAgreementListVo> getList(Page<BusAgreementDto> page, @Param("condition") BusAgreementDto agreementDto);

    /**
     * 总数
     * @param agreementDto
     * @return
     */
    Integer getTotal(@Param("condition") BusAgreementDto agreementDto);

    /**
     * 管理员-列表
     * @param page
     * @param agreementDto
     * @return
     */
    List<LawyerAgreementListVo> getAllList(Page<BusAgreementDto> page, @Param("condition") BusAgreementDto agreementDto);


    /**
     * 管理员-总数
     * @param agreementDto
     * @return
     */
    Integer getAllTotal(@Param("condition") BusAgreementDto agreementDto);
}
