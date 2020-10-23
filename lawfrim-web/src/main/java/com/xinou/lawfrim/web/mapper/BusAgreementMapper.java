package com.xinou.lawfrim.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinou.lawfrim.web.dto.BusAgreementDto;
import com.xinou.lawfrim.web.entity.BusAgreement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinou.lawfrim.web.vo.agreement.AgreementListVo;
import com.xinou.lawfrim.web.vo.agreement.AgreementNumVo;
import com.xinou.lawfrim.web.vo.agreement.AgreementTypeVo;
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
public interface BusAgreementMapper extends BaseMapper<BusAgreement> {

    /**
     * 列表-分页
     * @param page
     * @param agreementDto
     * @return
     */
    List<AgreementListVo> getList(Page<BusAgreementDto> page, @Param("condition") BusAgreementDto agreementDto);

    /**
     * 总数
     * @param agreementDto
     * @return
     */
    Integer getTotal(@Param("condition") BusAgreementDto agreementDto);

    /**
     * 总数
     * @param agreementDto
     * @return
     */
    List<AgreementTypeVo> getAgreementTypeStatistic(@Param("condition") BusAgreementDto agreementDto);

    /**
     * 合同数量统计
     * @param
     * @return
     */
    List<AgreementNumVo> getAgreementNumber();

    /**
     * 当前月份之前合同数量
     * @param
     * @return
     */
    Integer getAgreementNumByTime(@Param("condition") BusAgreementDto agreementDto);


}
