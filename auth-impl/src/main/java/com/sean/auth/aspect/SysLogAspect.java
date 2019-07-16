package com.sean.auth.aspect;

import com.alibaba.fastjson.JSONObject;
import com.sean.auth.annotation.Operation;
import com.sean.auth.http.HttpUtils;
import com.sean.auth.security.SecurityUtils;
import com.sean.auth.service.LogService;
import com.sean.auth.utlis.IPUtils;
import com.sean.auth.utlis.JacksonUtil;
import com.sean.auth.vo.LogVO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 使用aop记录日志
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/3 17:07
 */
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private LogService logService;

    @Pointcut("execution(* com.sean.auth.service.*.*(..))")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        // 保存日志
        saveSysLog(point, time);
        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        String userName = SecurityUtils.getUsername();
        if(joinPoint.getTarget() instanceof LogService) {
            return ;
        }
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        LogVO sysLog = new LogVO();

        //获取切入点所在的方法
		Method method = signature.getMethod();
//		com.louis.merak.admin.annotation.SysLog syslogAnno = method.getAnnotation(com.louis.merak.admin.annotation.SysLog.class);
        //获取操作
        Operation operation = method.getAnnotation(Operation.class);
		if(operation != null){
//			//注解上的描述
			sysLog.setOperation(operation.value());//保存获取的操作
		}

        // 获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
		// 获取请求的方法名
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
//        System.out.println(sysLog.getMethod());
        // 请求的参数
        Object[] args = joinPoint.getArgs();
//        try{
//            String params = JSONObject.toJSONString(args[0]);
//            if(params.length() > 200) {
//                params = params.substring(0, 200) + "...";
//            }
//            sysLog.setParams(params);
//        } catch (Exception e){
//        }

        try {
            String params = JacksonUtil.obj2json(args);
            sysLog.setParams(params);
        } catch (Exception e) {

        }

        // 获取request
        HttpServletRequest request = HttpUtils.getHttpServletRequest();
        // 设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));

        // 用户名
        sysLog.setUserName(userName);
        sysLog.setCreateBy(userName);
        sysLog.setLastUpdateBy(userName);
        // 执行时长(毫秒)
        sysLog.setTime(time);

        // 保存系统日志
        logService.save(sysLog);
    }

}
