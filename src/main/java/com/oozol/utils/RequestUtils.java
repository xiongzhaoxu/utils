package com.oozol.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * HTTP请求处理工具类
 */
public class RequestUtils {
    private static final Logger LOGGER = Logger.getLogger(RequestUtils.class.getName());

    /**
     * 将request查询参数封装至Map
     *
     * @param request  请求
     * @param printLog 是否打印日志
     * @return 参数Map
     */
    public static Map<String, Object> getParameters(HttpServletRequest request,
                                                    boolean printLog) {
        Enumeration<String> enume = request.getParameterNames();
        //这里一般参数都在5个以内，所以使用默认的容量16
        Map<String, Object> map = new HashMap<>(16);
        while (enume.hasMoreElements()) {
            String key = enume.nextElement();
            String value = request.getParameter(key);
            map.put(key, value);
            if (printLog) {
                LOGGER.info(key + "==>" + value);
            }
        }
        return map;
    }

    /**
     * 将request查询参数封装至Map
     *
     * @param request 请求
     * @return 参数Map
     */
    public static Map<String, Object> getParameters(HttpServletRequest request) {

        return getParameters(request, false);
    }


    public static String getTokenAtr(String key, String token) throws Exception {
        if (StringUtils.isEmpty(token)) {
            return null;
        } else {
            Map<String, Object> claims = JwtUtils.getJWTClaimsSet(token).getClaims();
            Object value = claims.get(key);
            return value == null ? null : value.toString();
        }
    }
}
