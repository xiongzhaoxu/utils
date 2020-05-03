

package com.oozol.support;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class Global extends AbstractConfiguration {
    public Global() {
    }

    public static HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static DeviceType getRemoteDeviceType() {
        String userAgent = getUserAgent();
        return getRemoteDeviceTypeByUserAgent(userAgent);
    }

    public static DeviceType getRemoteDeviceTypeByUserAgent(String userAgent) {
        if (StringUtils.isEmpty(userAgent)) {
            return DeviceType.PC_WEB;
        } else if (userAgent.contains("MicroMessenger")) {
            return DeviceType.OTHER_TERMINAL;
        } else {
            return !userAgent.contains("iPhone") && !userAgent.contains("iPad") && !userAgent.contains("iOS") && !userAgent.contains("Android") ? DeviceType.PC_WEB : DeviceType.MOBILE;
        }
    }

    public static String getRemoteDeviceTypeByUserAgentToString() {
        String userAgent = getUserAgent();
        if (StringUtils.isEmpty(userAgent)) {
            return DeviceType.PC_WEB.toString();
        } else if (userAgent.contains("MicroMessenger")) {
            return DeviceType.OTHER_TERMINAL.toString();
        } else {
            if (userAgent.contains("iPhone")) {
                return "iPhone";
            } else if (userAgent.contains("iPad")) {
                return "iPad";
            } else if (userAgent.contains("iOS")) {
                return "iOS";
            } else if (userAgent.contains("Android")) {
                return "Android";
            } else {
                return DeviceType.MOBILE.toString();
            }
        }
    }

    public static boolean isBrowser() {
        DeviceType remoteDeviceType = getRemoteDeviceType();
        return remoteDeviceType == DeviceType.PC_WEB;
    }

    public static boolean isAppEndpoint() {
        DeviceType remoteDeviceType = getRemoteDeviceType();
        return remoteDeviceType == DeviceType.MOBILE;
    }

    public static boolean isApplets() {
        DeviceType remoteDeviceType = getRemoteDeviceType();
        return remoteDeviceType == DeviceType.OTHER_TERMINAL;
    }

    public static String getUserAgent() {
        return getCurrentRequest().getHeader("user-agent");
    }

    public static <T> List<T> removeDuplicateCase(List<T> cases, Comparator<T> comparator) {
        Set<T> set = new TreeSet(comparator);
        set.addAll(cases);
        return new ArrayList(set);
    }
}
