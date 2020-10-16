package com.xinou.lawfrim.common.controller.admin;

import com.xinou.lawfrim.common.aspect.LogTypeEnum;
import com.xinou.lawfrim.common.aspect.SystemLog;
import com.xinou.lawfrim.common.entity.LogDto;
import com.xinou.lawfrim.common.entity.LogEntity;
import com.xinou.lawfrim.common.service.SystemLogService;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.Config;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiao_XX
 * @date 2020/09/29.
 * Description:
 */
@RestController("AdminSystemLogController")
@RequestMapping("/admin/logs/")
@ApiIgnore
public class AdminSystemLogController {
    private final SystemLogService systemLogService;

    public AdminSystemLogController(SystemLogService systemLogService) {
        this.systemLogService = systemLogService;
    }

    /**
     * @api {post} /admin/logs/list 登录日志列表
     * @apiDescription 登录日志列表
     * @apiGroup AdminLogs
     * @apiParam {string} [realName] 姓名搜索
     * @apiParam {string} [startTimeStr] 开始时间
     * @apiParam {string} [endTimeStr] 结束时间
     * @apiParam {Number} pageNumber 当前页
     * @apiParam {Number} pageSize 每页数量
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
     * "body": {
     * "dataList":[{
     * "module": "功能",
     * "methods": "功能模块",
     * "type": "1-登录管理系统日志 2- 登录移动端日志 3 -系统日志 4- 服务器通信异常日志 5- 移动服务日志 6- 移动异常日志",
     * "description": "执行结果",
     * "account": "账户",
     * "realName": "操作者",
     * "operationTimeStr": "操作时间"
     * }],
     * "total":"数据总数"
     * }
     * }
     */
    @RequestMapping("list")
    @RequiresPermissions("/admin/logs/login")
    APIResponse loginLogsList(@RequestBody LogDto logDto) {
        Integer[] typeList = {1, 2};
        logDto.setTypeList(Arrays.asList(typeList));
        List<LogEntity> logEntities = systemLogService.logList(logDto);
        Integer total = systemLogService.logCount(logDto);
        Map<String, Object> data = new HashMap<>(2);
        data.put(Config.DATA_LIST, logEntities);
        data.put(Config.TOTAL, total);
        return new APIResponse(data);
    }

    /**
     * @api {post} /admin/logs/system 系统日志列表
     * @apiDescription 系统日志列表
     * @apiGroup AdminLogs
     * @apiParam {string} [realName] 姓名搜索
     * @apiParam {string} [startTimeStr] 开始时间
     * @apiParam {string} [endTimeStr] 结束时间
     * @apiParam {Number} pageNumber 当前页
     * @apiParam {Number} pageSize 每页数量
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
     * "body": {
     * "dataList":[{
     * "module": "功能",
     * "methods": "功能模块",
     * "type": "1-登录管理系统日志 2- 登录移动端日志 3 -系统日志 4- 服务器通信异常日志 5- 移动服务日志 6- 移动异常日志",
     * "description": "执行结果",
     * "account": "账户",
     * "realName": "操作者",
     * "operationTimeStr": "操作时间"
     * }],
     * "total":"数据总数"
     * }
     * }
     */
    @RequestMapping("system")
    @RequiresPermissions("/admin/logs/system")
    APIResponse systemLogsList(@RequestBody LogDto logDto) {
        logDto.setType(3);
        List<LogEntity> logEntities = systemLogService.logList(logDto);
        Integer total = systemLogService.logCount(logDto);
        Map<String, Object> data = new HashMap<>(2);
        data.put(Config.DATA_LIST, logEntities);
        data.put(Config.TOTAL, total);
        return new APIResponse(data);
    }


    /**
     * @api {post} /admin/logs/exception 异常日志列表
     * @apiDescription 异常日志列表
     * @apiGroup AdminLogs
     * @apiParam {string} [realName] 姓名搜索
     * @apiParam {string} [startTimeStr] 开始时间
     * @apiParam {string} [endTimeStr] 结束时间
     * @apiParam {Number} pageNumber 当前页
     * @apiParam {Number} pageSize 每页数量
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
     * "body": {
     * "dataList":[{
     * "type": "4--服务异常  6--移动端异常",
     * "module": "功能（有可能为空）",
     * "methods": "功能模块",
     * "description": "操作失败",
     * "exceptionMessage": "type==4异常信息,type==6异常文件（要下载）",
     * "exceptionLine": "异常所在行数",
     * "exceptionClass": "异常类",
     * "exceptionMethod": "异常方法名",
     * "account": "账户",
     * "realName": "操作者",
     * "operationTimeStr": "操作时间"
     * }],
     * "total":"数据总数"
     * }
     * }
     */
    @RequestMapping("exception")
    @RequiresPermissions("/admin/logs/exception")
    APIResponse logsList(@RequestBody LogDto logDto) {
        Integer[] typeList = {4, 6};
        logDto.setTypeList(Arrays.asList(typeList));
        List<LogEntity> logEntities = systemLogService.logList(logDto);
        Integer total = systemLogService.logCount(logDto);
        Map<String, Object> data = new HashMap<>(2);
        data.put(Config.DATA_LIST, logEntities);
        data.put(Config.TOTAL, total);
        return new APIResponse(data);
    }

    /**
     * @api {post} /admin/logs/mobile 移动日志列表
     * @apiDescription 移动日志列表
     * @apiGroup AdminLogs
     * @apiParam {string} [realName] 姓名搜索
     * @apiParam {string} [startTimeStr] 开始时间
     * @apiParam {string} [endTimeStr] 结束时间
     * @apiParam {Number} pageNumber 当前页
     * @apiParam {Number} pageSize 每页数量
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
     * "body": {
     * "dataList":[{
     * "module": "功能",
     * "methods": "功能模块",
     * "type": "1-登录管理系统日志 2- 登录移动端日志 3 -系统日志 4- 服务器通信异常日志 5- 移动服务日志 6- 移动异常日志",
     * "description": "执行结果",
     * "account": "账户",
     * "realName": "操作者",
     * "operationTimeStr": "操作时间"
     * }],
     * "total":"数据总数"
     * }
     * }
     */
    @RequestMapping("mobile")
    @RequiresPermissions("/admin/logs/mobile")
    APIResponse mobileList(@RequestBody LogDto logDto) {
        logDto.setType(5);
        List<LogEntity> logEntities = systemLogService.logList(logDto);
        Integer total = systemLogService.logCount(logDto);
        Map<String, Object> data = new HashMap<>(2);
        data.put(Config.DATA_LIST, logEntities);
        data.put(Config.TOTAL, total);
        return new APIResponse(data);
    }

    /**
     * @api {post} /admin/logs/export/login 登录日志导出Excel
     * @apiDescription 登录日志导出Excel
     * @apiGroup AdminLogs
     * @apiParam {string} [realName] 姓名搜索
     * @apiParam {string} [startTimeStr] 开始时间
     * @apiParam {string} [endTimeStr] 结束时间
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
     * "body": {
     * "filePath":"文件下载路径"
     * }
     * }
     */
    @RequestMapping("export/login")
    @RequiresPermissions("/admin/logs/export/login")
    @SystemLog(module = LogTypeEnum.LOG_MODULE, methods = LogTypeEnum.LOG_MODULE_EXCEL_LOGIN, type = "3")
    APIResponse exportExcel(@RequestBody LogDto logDto, HttpServletRequest request) {
        Integer[] typeList = {1, 2};
        logDto.setTypeList(Arrays.asList(typeList));
        String fileName = systemLogService.excelLog(logDto, request);
        Map<String, Object> data = new HashMap<>(1);
        data.put("filePath", fileName);
        return new APIResponse(data);
    }

    /**
     * @api {post} /admin/logs/export/system 系统日志导出
     * @apiDescription 系统日志导出
     * @apiGroup AdminLogs
     * @apiParam {string} [realName] 姓名搜索
     * @apiParam {string} [startTimeStr] 开始时间
     * @apiParam {string} [endTimeStr] 结束时间
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
     * "body": {
     * "filePath":"文件下载路径"
     * }
     * }
     */
    @RequestMapping("export/system")
    @RequiresPermissions("/admin/logs/export/system")
    @SystemLog(module = LogTypeEnum.LOG_MODULE, methods = LogTypeEnum.LOG_MODULE_EXCEL_SYSTEM, type = "3")
    APIResponse exportSystemExcel(@RequestBody LogDto logDto, HttpServletRequest request) {
        logDto.setType(3);
        String fileName = systemLogService.excelLog(logDto, request);
        Map<String, Object> data = new HashMap<>(1);
        data.put("filePath", fileName);
        return new APIResponse(data);
    }

    /**
     * @api {post} /admin/logs/export/exception 异常日志导出
     * @apiDescription 异常日志导出
     * @apiGroup AdminLogs
     * @apiParam {string} [realName] 姓名搜索
     * @apiParam {string} [startTimeStr] 开始时间
     * @apiParam {string} [endTimeStr] 结束时间
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
     * "body": {
     * "filePath":"文件下载路径"
     * }
     * }
     */
    @RequestMapping("export/exception")
    @RequiresPermissions("/admin/logs/export/exception")
    @SystemLog(module = LogTypeEnum.LOG_MODULE, methods = LogTypeEnum.LOG_MODULE_EXCEL_EXCEPTION, type = "3")
    APIResponse exportExceptionExcel(@RequestBody LogDto logDto, HttpServletRequest request) {
        logDto.setType(3);
        String fileName = systemLogService.excelLog(logDto, request);
        Map<String, Object> data = new HashMap<>(1);
        data.put("filePath", fileName);
        return new APIResponse(data);
    }

    /**
     * @api {post} /admin/logs/export/mobile 移动日志导出
     * @apiDescription 移动日志导出
     * @apiGroup AdminLogs
     * @apiParam {string} [realName] 姓名搜索
     * @apiParam {string} [startTimeStr] 开始时间
     * @apiParam {string} [endTimeStr] 结束时间
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
     * "body": {
     * "filePath":"文件下载路径"
     * }
     * }
     */
    @RequestMapping("export/mobile")
    @RequiresPermissions("/admin/logs/export/mobile")
    @SystemLog(module = LogTypeEnum.LOG_MODULE, methods = LogTypeEnum.LOG_MODULE_EXCEL_EXCEPTION, type = "3")
    APIResponse exportMobileExcel(@RequestBody LogDto logDto, HttpServletRequest request) {
        logDto.setType(5);
        String fileName = systemLogService.excelLog(logDto, request);
        Map<String, Object> data = new HashMap<>(1);
        data.put("filePath", fileName);
        return new APIResponse(data);
    }
}
