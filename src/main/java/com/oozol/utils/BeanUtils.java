package com.oozol.utils;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class BeanUtils extends  org.springframework.beans.BeanUtils{

    public static void copyProperties(Object source, Object target) {
        if (source != null) {
            try {
                org.apache.commons.beanutils.BeanUtils.copyProperties(target,source);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 将对象装换为 map,对象转成 map，key肯定是字符串
     *
     * @param bean 转换对象
     * @return 返回转换后的 map 对象
     */
    public static Map<String, Object> beanToMap(Object bean) {
        return JSON.parseObject(JSON.toJSONString(bean));

//        return com.baomidou.mybatisplus.core.toolkit.BeanUtils.beanToMap(bean);
    }

    /**
     * map 装换为 java bean 对象
     *
     * @param map   转换 MAP
     * @param clazz 对象 Class
     * @return 返回 bean 对象
     */
//    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
//        return com.baomidou.mybatisplus.core.toolkit.BeanUtils.mapToBean(map, clazz);
//    }
}