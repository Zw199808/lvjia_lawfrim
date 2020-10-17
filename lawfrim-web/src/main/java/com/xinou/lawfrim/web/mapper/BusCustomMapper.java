package com.xinou.lawfrim.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinou.lawfrim.web.dto.BusCustomDto;
import com.xinou.lawfrim.web.dto.BusLawyerDto;
import com.xinou.lawfrim.web.entity.BusCustom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinou.lawfrim.web.vo.LawyerVo;
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
public interface BusCustomMapper extends BaseMapper<BusCustom> {

    /**
     * 列表-分页
     * @param page
     * @param busLawyerDto
     * @return
     */
    List<BusCustom> getList(Page<BusCustomDto> page, @Param("condition") BusCustomDto busLawyerDto);

    /**
     * 总数
     * @param busLawyerDto
     * @return
     */
    Integer getTotal(@Param("condition") BusCustomDto busLawyerDto);

}
