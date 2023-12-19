/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.aspect;

import brave.propagation.ThreadLocalCurrentTraceContext;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.themall.common.utils.ServletUtils;
import com.themall.model.constants.BusinessStatus;
import io.renren.annotation.SysLog;
import io.renren.modules.sys.entity.SysLogEntity;
import io.renren.modules.sys.service.SysLogService;
import io.renren.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Date;
import java.util.Map;


/**
 * 系统日志，切面处理类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Slf4j
@Aspect
@Component
public class SysLogAspect {

    /**
     * 排除敏感属性字段
     */
    public static final String[] EXCLUDE_PROPERTIES = {"password", "oldPassword", "newPassword", "confirmPassword"};
    /**
     * 计算操作消耗时间
     */
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new NamedThreadLocal<Long>("Cost Time");
    @Autowired
    private SysLogService iTabOpsOperLogService;

    /**
     * 处理请求前执行
     */
    @Before(value = "@annotation(sysLog)")
    public void boBefore(JoinPoint joinPoint, SysLog sysLog) {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(sysLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, SysLog sysLog, Object jsonResult) {
        handleLog(joinPoint, sysLog, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "@annotation(sysLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, SysLog sysLog, Exception e) {
        handleLog(joinPoint, sysLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, SysLog sysLog, final Exception e,
                             Object jsonResult) {
        try {
            // 获取当前的用户

            // *========数据库日志=========*//
            SysLogEntity operLog = new SysLogEntity();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址
            HttpServletRequest request = ServletUtils.getRequest();

            operLog.setTraceId(ThreadLocalCurrentTraceContext.create().get().traceIdString());
            operLog.setOperIp(IPUtils.getIpAddr(ServletUtils.getRequest()));
            operLog.setOperUrl(StringUtils.substring(request.getRequestURI(), 0, 255));

            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            operLog.setOperName(name);

            if (e != null) {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            //当前ip地址
            InetAddress addr = InetAddress.getLocalHost();

            String property = System.getProperty("csp.sentinel.heartbeat.client.ip");
            operLog.setOperLocation(StringUtils.isEmpty(property) ? addr.getHostAddress() : property);
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethodName(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(request.getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, sysLog, operLog, jsonResult);
            // 设置消耗时间
            operLog.setCostTime(System.currentTimeMillis() - TIME_THREADLOCAL.get());
            operLog.setOperTime(new Date(TIME_THREADLOCAL.get()));
            operLog.setCreateBy(name);
            operLog.setUpdateBy(name);
            Date createDate = new Date();
            operLog.setCreateDate(createDate);
            operLog.setUpdateDate(createDate);
            //  保存数据库
            iTabOpsOperLogService.save(operLog);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("异常信息: ", exp);
        } finally {
            TIME_THREADLOCAL.remove();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log          日志
     * @param sysLogEntity 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, SysLog log, SysLogEntity sysLogEntity,
                                               Object jsonResult) throws Exception {
        // 设置action动作
        sysLogEntity.setBusinessType(log.businessType().ordinal());
        // 设置标题
        sysLogEntity.setLogTitle(log.value());
        // 设置操作人类别
        sysLogEntity.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, sysLogEntity, log.excludeParamNames());
        }
        // 是否需要保存response，参数和值
        if (log.isSaveResponseData() && ObjectUtils.isNotEmpty(jsonResult)) {
            sysLogEntity.setJsonResult(
                    StringUtils.substring(JSON.toJSONString(jsonResult, JSONWriter.Feature.WriteNulls), 0, 2000));
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, SysLogEntity operLog, String[] excludeParamNames)
            throws Exception {
        Map<String, String[]> map = ServletUtils.getRequest().getParameterMap();
        if (ObjectUtils.isNotEmpty(map)) {
            String params = JSONObject.toJSONString(map, excludePropertyPreFilter(excludeParamNames));
            operLog.setOperParam(params);
        } else {
            Object args = joinPoint.getArgs();
            if (ObjectUtils.isNotEmpty(args)) {
                String params = argsArrayToString(joinPoint.getArgs(), excludeParamNames);
                operLog.setOperParam(params);
            }
        }
    }

    /**
     * 忽略敏感属性
     */
    public PropertyPreFilters.MySimplePropertyPreFilter excludePropertyPreFilter(String[] excludeParamNames) {
        return new PropertyPreFilters().addFilter()
                .addExcludes(ArrayUtils.addAll(EXCLUDE_PROPERTIES, excludeParamNames));
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray, String[] excludeParamNames) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (ObjectUtils.isNotEmpty(o) && !isFilterObject(o)) {
                    try {
                        String jsonStr = JSON.toJSONString(o);
                        params.append(jsonStr).append(" ");
                    } catch (Exception e) {
                        log.error("JSON序列化异常:", e);
                    }
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse || o instanceof BindingResult;
    }

}
