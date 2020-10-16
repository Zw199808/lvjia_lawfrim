package com.xinou.lawfrim.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinou.lawfrim.common.entity.LogDto;
import com.xinou.lawfrim.common.entity.LogEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * All rights Reserved, Designed By 信鸥科技
 * Created by xiao_XX on 2019/10/22.
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description:
 */
public interface SystemLogMapper extends BaseMapper<LogEntity> {

    /**
     * 日志列表
     *
     * @param page
     * @param logDto
     * @return
     */
    List<LogEntity> logList(Page<LogEntity> page, @Param("condition") LogDto logDto);

    /**
     * 导出excel总列表
     *
     * @param logDto
     * @return
     */
    List<LogEntity> logListAll(@Param("condition") LogDto logDto);

    /**
     * 日志总数
     *
     * @param logDto
     * @return
     */
    Integer logCount(@Param("condition")LogDto logDto);
}
