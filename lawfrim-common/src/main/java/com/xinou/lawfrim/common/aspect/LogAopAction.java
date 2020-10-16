//package com.xinou.lawfrim.common.aspect;
//
//import com.xinou.lawfrim.common.entity.LogEntity;
//import com.xinou.lawfrim.common.service.SystemLogService;
//import com.xinou.lawfrim.common.util.APIResponse;
//import com.xinou.lawfrim.common.util.StringUtil;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.subject.Subject;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.lang.reflect.Method;
//import java.sql.Timestamp;
//import java.util.Arrays;
//
///**
// * All rights Reserved, Designed By 信鸥科技
// * Created by xiao_XX on 2019/10/22.
// * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
// * Description:
// */
//
//@Aspect
//@Component
//public class LogAopAction {
//
//    @Autowired
//    private SystemLogService systemLogService;
//
//    private static LogAopAction logAopAction;
//
//    @PostConstruct
//    public void init() {
//        logAopAction = this;
//        logAopAction.systemLogService = this.systemLogService;
//    }
//
//    //配置接入点,第一段表示返回值类型 * 表示所有类型
//    @Pointcut("execution(* com..*.controller..*(..))")
//    private void controllerAspect() {
//    }//定义一个切入点
//
//    @AfterReturning(value = "controllerAspect()", returning = "apiResponse")
//    public Object around(JoinPoint pjp, Object apiResponse) throws Throwable {
//        //常见日志实体对象
//        LogEntity log = new LogEntity();
//
//        //获取系统时间
//        long time = System.currentTimeMillis();
//        Timestamp operationTime = new Timestamp(time);
//        log.setOperationTime(operationTime);
//
//        //方法通知前获取时间,为什么要记录这个时间呢？当然是用来计算模块执行时间的
//        long start = System.currentTimeMillis();
//        // 拦截的实体类，就是当前正在执行的controller
//        Object target = pjp.getTarget();
//        // 拦截的方法名称。当前正在执行的方法
//        String methodName = pjp.getSignature().getName();
//        // 拦截的方法参数
//        Object[] args = pjp.getArgs();
//        // 拦截的放参数类型
//        Signature sig = pjp.getSignature();
//        MethodSignature msig = null;
//        if (!(sig instanceof MethodSignature)) {
//            throw new IllegalArgumentException("该注解只能用于方法");
//        }
//        msig = (MethodSignature) sig;
//        Class[] parameterTypes = msig.getMethod().getParameterTypes();
//
//        Object object = null;
//        Method method = msig.getMethod();
//
//        if (null != method) {
//            // 判断是否包含自定义的注解，说明一下这里的SystemLog就是我自己自定义的注解
//            if (method.isAnnotationPresent(SystemLog.class)) {
//                SystemLog systemlog = method.getAnnotation(SystemLog.class);
//                log.setMethods(systemlog.methods());
//                log.setType(Integer.parseInt(systemlog.type()));
//                log.setModule(systemlog.module());
//                if (!StringUtil.isNullString(systemlog.loginType())) {
//                    Object type = AnnotationResolver.newInstance().resolver(pjp, systemlog.loginType());
//                    if (Integer.parseInt(type.toString()) == 3) {
//                        log.setMethods(LogTypeEnum.LOGIN_MODULE_APP);
//                        log.setType(2);
//                    }
//                }
//                if (StringUtil.isNullString(systemlog.name())) {
//                    log.setMethods(log.getMethods());
//                } else {
//                    Object name = AnnotationResolver.newInstance().resolver(pjp, systemlog.name());
//                    log.setMethods(log.getMethods() + "-" + name);
//                }
//                log.setResponseTime("-");
//                Subject s = SecurityUtils.getSubject();
//                if (s.getSession().getAttribute("memberId") != null) {
//                    int userId = (int) s.getSession().getAttribute("memberId");
//                    log.setUserId(userId);
//                }
//                // 获取返回值
//                APIResponse result = (APIResponse) apiResponse;
//                log.setDescription("执行成功");
//
//                if (!"200".equals(result.getReCode())) {
//                    log.setDescription("执行失败");
//                }
//                systemLogService.save(log);
//            }
//
//        } else { //不需要拦截直接执行
////            object = pjp.proceed();
//        }
//        return object;
//    }
//
//
//    @AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
//    public void handleThrowing(JoinPoint joinPoint, RuntimeException e) {//controller类抛出的异常在这边捕获
//        MethodSignature sig = (MethodSignature) joinPoint.getSignature();
//        LogEntity logEntity = new LogEntity();
//        if (sig != null && sig.getMethod().isAnnotationPresent(SystemLog.class)) {
//            SystemLog systemlog = sig.getMethod().getAnnotation(SystemLog.class);
//            logEntity.setModule(systemlog.module());
//            if (StringUtil.isNullString(systemlog.name())) {
//                logEntity.setMethods(systemlog.methods());
//            } else {
//                Object name = AnnotationResolver.newInstance().resolver(joinPoint, systemlog.name());
//                logEntity.setMethods(systemlog.methods() + "-" + name);
//            }
//        }
//        Object[] args = joinPoint.getArgs();
//        logEntity.setExceptionMessage(e.getMessage());
//        logEntity.setExceptionClass(e.getStackTrace()[0].getClassName());
//        logEntity.setExceptionLine(String.valueOf(e.getStackTrace()[0].getLineNumber()));
//        logEntity.setExceptionMethod(e.getStackTrace()[0].getMethodName());
//        logEntity.setExceptionArgs(Arrays.toString(args));
//        logEntity.setResponseTime("-");
//        logEntity.setType(4);
//        logEntity.setDescription("操作失败");
//        Subject s = SecurityUtils.getSubject();
//        if (s.getSession().getAttribute("memberId") != null) {
//            int userId = (int) s.getSession().getAttribute("memberId");
//            logEntity.setUserId(userId);
//        }
//        systemLogService.save(logEntity);
//    }
//
//
//}
//
