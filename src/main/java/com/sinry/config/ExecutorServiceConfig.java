package com.sinry.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Classname:
 * @Description:
 * @Date: 2019-05-30 18:04
 * @Author: Allen Lei
 */
@Configuration
@Component
public class ExecutorServiceConfig {
    private ExecutorService threadPoolExecutor = Executors.newCachedThreadPool();
    @Bean
    public ExecutorService executorService(){
        return this.threadPoolExecutor;
    }
}
