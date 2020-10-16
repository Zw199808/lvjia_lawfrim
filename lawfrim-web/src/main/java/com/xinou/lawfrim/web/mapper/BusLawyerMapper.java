package com.xinou.lawfrim.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate;
import com.xinou.lawfrim.web.dto.BusLawyerDto;
import com.xinou.lawfrim.web.entity.BusLawyer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface BusLawyerMapper extends BaseMapper<BusLawyer> {

    /**
     * 列表-分页
     * @param page
     * @param busLawyerDto
     * @return
     */
    List<BusLawyer> getList(Page<BusLawyer> page, @Param("condition") BusLawyerDto busLawyerDto);

    /**
     * 总数
     * @param busLawyerDto
     * @return
     */
    Integer getTotal(@Param("condition") BusLawyerDto busLawyerDto);



}
