

package com.sinry.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

public class AbstractConfiguration implements ApplicationContextAware, EnvironmentAware {
    protected static ApplicationContext applicationContext;
    protected static Environment environment;

    public AbstractConfiguration() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AbstractConfiguration.applicationContext = applicationContext;
    }

    public void setEnvironment(Environment environment) {
        AbstractConfiguration.environment = environment;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }

    public static <T> T getBean(Class<T> tClass, String name) {
        return applicationContext.getBean(name, tClass);
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getAttribute(String attrName, Class<T> tClass) {
        return environment.getProperty(attrName, tClass);
    }

    public static String getAttribute(String attrName) {
        return environment.getProperty(attrName);
    }
}
