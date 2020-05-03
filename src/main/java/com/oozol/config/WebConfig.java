package com.oozol.config;

import com.oozol.base.BaseInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Classname:
 * @Description:
 * @Date: 2019-05-27 16:47
 * @Author: Allen Lei
 */
//@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    BaseInterceptor baseInterceptor;
//    @Value("${erp.filter-map}")
    private String anonPaths;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] split = anonPaths.split(",");
        List<String> excludePaths = new ArrayList<>(Arrays.asList(split));
        //放行
//        excludePaths.add("/employees/checkBinding");
//        excludePaths.add("/vrOrder/receivePackageNotice");

        registry.addInterceptor(baseInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePaths);

    }
}