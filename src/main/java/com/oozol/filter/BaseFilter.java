package com.oozol.filter;

import com.oozol.support.Global;
import com.oozol.utils.IpUtil;
import com.oozol.utils.StringUtils;
import com.oozol.utils.TraceIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*", filterName = "BaseFilter")
public class BaseFilter extends GenericFilterBean {

    @Value("${spring.profiles.active}")
    private String env;
    @Value("${spring.application.name}")
    private String appKey;
    @Value("${server.port}")
    private String port;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 初始化traceId
        initTraceId((HttpServletRequest) servletRequest);
        // 初始化日志字段
        setMDCFields((HttpServletRequest) servletRequest);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void initTraceId(HttpServletRequest request) {
        // 尝试获取当前请求的traceId
        String traceId = request.getParameter("traceId");

        // 如果traceId为空或者为默认值，则生成新的traceId
        if (StringUtils.isBlank(traceId) || TraceIdUtil.defaultTraceId(traceId)) {
            traceId = TraceIdUtil.genTraceId();
        }

        // 设置traceId
        TraceIdUtil.setTraceId(traceId);
    }

    private void setMDCFields(HttpServletRequest request) {
        MDC.put("app_key", appKey);
        MDC.put("env", env);
        MDC.put("host", IpUtil.getIpAddr(request) + ":" + port);
        MDC.put("thread_name", String.valueOf(Thread.currentThread()));
        MDC.put("request_uri", request.getRequestURI());
        MDC.put("request_path", request.getServletPath());
        MDC.put("request_method", request.getMethod());
        MDC.put("device_type", Global.getRemoteDeviceTypeByUserAgentToString());
        MDC.put("user_agent", Global.getUserAgent());
    }


    @Override
    public void destroy() {
        super.destroy();
        log.info("----- clean MDC -----");
        MDC.clear();
    }
}
