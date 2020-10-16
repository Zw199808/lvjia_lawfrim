package com.xinou.lawfrim.common.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinou.lawfrim.common.entity.ExcelEntity;
import com.xinou.lawfrim.common.entity.LogDto;
import com.xinou.lawfrim.common.entity.LogEntity;
import com.xinou.lawfrim.common.mapper.SystemLogMapper;
import com.xinou.lawfrim.common.service.SystemLogService;
import com.xinou.lawfrim.common.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * All rights Reserved, Designed By 信鸥科技
 *
 * @author xiao_XX
 * @date 2019/10/22
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description:
 */
@Service("SystemLogService")
@Primary
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper, LogEntity> implements SystemLogService {
    private final SystemLogMapper systemLogMapper;

    public SystemLogServiceImpl(SystemLogMapper systemLogMapper) {
        this.systemLogMapper = systemLogMapper;
    }

    @Override
    public List<LogEntity> logList(LogDto logDto) {

        Page<LogEntity> page = new Page<>(logDto.getPageNumber(), logDto.getPageSize());
        List<LogEntity> logEntities = systemLogMapper.logList(page, logDto);

        if (logEntities.size() != 0) {
            for (LogEntity info : logEntities) {
                String time = TimeChange.timeStampChangStringDateTime(info.getOperationTime());
                info.setOperationTimeStr(time);
                if (info.getType() == 6 && !StringUtil.isNullString(info.getExceptionMessage())) {
                    info.setExceptionMessage(Config.SERVICE_IMG_URL + info.getExceptionMessage());
                }
            }
        }
        return logEntities;
    }

    @Override
    public Integer logCount(LogDto logDto) {
        return systemLogMapper.logCount(logDto);
    }

    @Override
    public String excelLog(LogDto logDto, HttpServletRequest request) {

        List<LogEntity> logEntities = systemLogMapper.logListAll(logDto);

        List<ExcelEntity> excelEntities = new ArrayList<>();

        int count = 0;

        String[] headers = {};
        Integer[] strArr = new Integer[]{1, 2, 3, 5};
        List<Integer> list = Arrays.asList(strArr);
        if (logEntities.size() != 0) {
            if (list.contains(logDto.getType())) {
                for (LogEntity log : logEntities) {
                    ExcelEntity info = new ExcelEntity();
                    info.setParam1(++count + "");
                    info.setParam2(log.getAccount());
                    info.setParam3(log.getRealName());
                    info.setParam4(log.getModule());
                    info.setParam5(log.getMethods());

                    String time = TimeChange.timeStampChangStringDateTime(log.getOperationTime());
                    info.setParam6(time);
                    info.setParam7(log.getDescription());
                    excelEntities.add(info);
                }
                headers = new String[]{"序号", "操作账户", "真实姓名", "操作模块", "操作功能", "操作时间", "操作结果"};
            } else {
                for (LogEntity log : logEntities) {
                    ExcelEntity info = new ExcelEntity();
                    info.setParam1(++count + "");
                    info.setParam2(log.getAccount());
                    info.setParam3(log.getRealName());
                    info.setParam4(StringUtil.isNullString(log.getModule()) ? "-" : log.getModule());
                    info.setParam5(StringUtil.isNullString(log.getMethods()) ? "-" : log.getMethods());

                    String time = TimeChange.timeStampChangStringDateTime(log.getOperationTime());
                    info.setParam6(time);
                    info.setParam7(log.getDescription());
                    info.setParam8(log.getExceptionMessage());
                    info.setParam9(log.getExceptionLine());
                    info.setParam10(log.getExceptionClass());
                    info.setParam11(log.getExceptionMethod());
                    excelEntities.add(info);
                }
                headers = new String[]{"序号", "操作账户", "真实姓名", "操作模块", "操作功能", "操作时间",
                        "操作结果", "异常信息", "异常行", "异常类", "异常方法"};
            }
        }

        String exclName = System.currentTimeMillis() + "操作日志.xls";
        //导出excel地址
        String path = Config.EXCEL_PATH + exclName;

//        ExcelUtil<ExcelEntity> ex = new ExcelUtil<ExcelEntity>();
        try {
            OutputStream out = new FileOutputStream(path);
//            ex.exportExcel(headers, excelEntities, out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Config.SERVICE_IMG_URL + exclName;
    }

    @Override
    public Boolean addLogs(LogEntity logEntity) {
        Subject s = SecurityUtils.getSubject();
        int userId = (int) s.getSession().getAttribute("memberId");
        logEntity.setUserId(userId);
        logEntity.setType(6);
        logEntity.setOperationTime(TimeChange.stringChangeTime(logEntity.getOperationTimeStr()));
        boolean res = save(logEntity);
        if (!res) {
            throw new BusException(Config.RE_ADD_CODE, Config.RE_ADD_MSG);
        }
        return res;
    }
}
