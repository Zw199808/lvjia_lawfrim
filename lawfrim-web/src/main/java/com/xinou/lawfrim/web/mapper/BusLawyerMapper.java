package com.xinou.lawfrim.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinou.lawfrim.web.dto.BusLawyerDto;
import com.xinou.lawfrim.web.dto.SortRuleDto;
import com.xinou.lawfrim.web.entity.BusLawyer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinou.lawfrim.web.vo.lawyer.AssignLawyerVo;
import com.xinou.lawfrim.web.vo.lawyer.LawyerVo;
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
public interface BusLawyerMapper extends BaseMapper<BusLawyer> {

    /**
     * 列表-分页
     * @param page
     * @param busLawyerDto
     * @return
     */
    List<LawyerVo> getList(Page<BusLawyerDto> page, @Param("condition") BusLawyerDto busLawyerDto);

    /**
     * 总数
     * @param busLawyerDto
     * @return
     */
    Integer getTotal(@Param("condition") BusLawyerDto busLawyerDto);


    /**
     * 分配律师列表
     * @param page
     * @param sortRuleDto
     * @return
     */
    List<AssignLawyerVo> getAssignLawyerList(Page<SortRuleDto> page, @Param("condition") SortRuleDto sortRuleDto);

    /**
     * 总数
     * @param sortRuleDto
     * @return
     */
    Integer getAssignLawyerTotal(@Param("condition") SortRuleDto sortRuleDto);


    /**
     * 导出列表
     * @param busLawyerDto
     * @return
     */
    List<LawyerVo> getExcelList(@Param("condition") BusLawyerDto busLawyerDto);

}
