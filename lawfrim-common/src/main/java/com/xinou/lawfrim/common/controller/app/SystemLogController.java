package com.xinou.lawfrim.common.controller.app;

import cn.hutool.core.bean.BeanUtil;
import com.xinou.lawfrim.common.entity.LogDto;
import com.xinou.lawfrim.common.entity.LogEntity;
import com.xinou.lawfrim.common.service.SystemLogService;
import com.xinou.lawfrim.common.util.APIResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiao_XX
 * @date 2020/09/29.
 * Description:
 */
@RestController("AppSystemLogController")
@RequestMapping("/app/logs/")
public class SystemLogController {
    private final SystemLogService systemLogService;

    public SystemLogController(SystemLogService systemLogService) {
        this.systemLogService = systemLogService;
    }

    /**
     * @api {post} /app/logs/add 添加移动端异常日志
     * @apiDescription 添加移动端异常日志
     * @apiGroup APPLogs
     * @apiParam {String} module 操作模块
     * @apiParam {String} methods 操作方法
     * @apiParam {String} operationTimeStr 操作时间
     * @apiParam {String} responseTime 反应时间
     * @apiParam {String} description 描述
     * @apiParam {String} exceptionMessage 报错文件名
     * @apiParamExample {string} 请求参数格式:
     * {json}
     * @apiVersion 1.0.0
     * @apiErrorExample {json} 错误返回值:
     * {
     * "recode": 100,
     * "remsg": "error",
     * "body": {}
     * }
     * @apiSuccessExample {json} 正确返回值:
     * {
     * "recode": 200,
     * "remsg": "success",
     * "body": {}
     * }
     */
    @RequestMapping("add")
    APIResponse appServiceLog(@RequestBody LogDto logDto) {

        LogEntity logEntity = new LogEntity();
        BeanUtil.copyProperties(logDto, logEntity);
        systemLogService.addLogs(logEntity);
        return new APIResponse();
    }
}
