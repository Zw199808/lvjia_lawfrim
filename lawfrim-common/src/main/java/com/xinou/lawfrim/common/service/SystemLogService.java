package com.xinou.lawfrim.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinou.lawfrim.common.entity.LogDto;
import com.xinou.lawfrim.common.entity.LogEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * All rights Reserved, Designed By 信鸥科技
 *
 * @author xiao_XX
 * @date 2019/10/22
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description:
 */
public interface SystemLogService extends IService<LogEntity> {
    /**
     * 日志列表
     *
     * @param logDto
     * @return
     */
    List<LogEntity> logList(LogDto logDto);

    /**
     * 日志总数
     *
     * @param logDto
     * @return
     */
    Integer logCount(LogDto logDto);

    /**
     * 导出日志Excel
     *
     * @param logDto
     * @param request
     * @return
     */
    String excelLog(LogDto logDto, HttpServletRequest request);

    Boolean addLogs(LogEntity logEntity);

}
