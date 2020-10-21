package com.xinou.lawfrim.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinou.lawfrim.web.dto.BusAgreementDto;
import com.xinou.lawfrim.web.entity.BusAgreement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinou.lawfrim.web.vo.agreement.AgreementListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Wangxin
 * @since 2020-10-16
 */
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
}
