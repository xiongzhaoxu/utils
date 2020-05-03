package com.oozol.utils;

import org.slf4j.MDC;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * traceId工具类
 */
public class TraceIdUtil {
    private static final String TRACE_ID = "traceId";
    /**
     * 当traceId为空时，显示的traceId。随意。
     */
    private static final String DEFAULT_TRACE_ID = "0";

    /**
     * 设置traceId
     */
    public static void setTraceId(String traceId) {
        // 如果参数为空，则设置默认traceId
        traceId = StringUtils.isBlank(traceId) ? DEFAULT_TRACE_ID : traceId;
        MDC.put(TRACE_ID, traceId);
    }

    /**
     * 获取traceId
     */
    public static String getTraceId() {
        //获取
        String traceId = MDC.get(TRACE_ID);
        //如果traceId为空，则返回默认值
        return StringUtils.isBlank(traceId) ? DEFAULT_TRACE_ID : traceId;
    }

    /**
     * 判断traceId为默认值
     */
    public static Boolean defaultTraceId(String traceId) {
        return DEFAULT_TRACE_ID.equals(traceId);
    }

    /**
     * 生成traceId
     */
    public static String genTraceId() {
        return UUID.randomUUID().toString();
    }

    /**
     * 初始化traceId
     */
    public static void initTraceId(HttpServletRequest request) {
        // 尝试获取当前请求的traceId
        String traceId = request.getParameter("traceId");

        // 如果traceId为空或者为默认值，则生成新的traceId
        if (StringUtils.isBlank(traceId) || defaultTraceId(traceId)) {
            traceId = genTraceId();
        }

        // 设置traceId
        setTraceId(traceId);
    }

}
