package com.oozol.utils;

import com.google.common.collect.Maps;
import com.oozol.annotation.CompareFiled;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * CompareUtils
 */
@Slf4j
public class CompareUtils {
    /**
     * 将要比较的字段<"字段描述":"字段值">放入到Map中；
     * @param obj
     */
    public static Map<String,String> setValueToMap(@NotNull Object obj){
        Map<String,String> iMap = Maps.newHashMap();
        Field[] fields = obj.getClass().getDeclaredFields();
        Arrays.asList(fields).forEach(i -> {
            String filedName = i.getName();
            if (PropertyUtils.isReadable(obj, filedName) && PropertyUtils.isWriteable(obj, filedName)) {
                if (i.isAnnotationPresent(CompareFiled.class)) {
                    CompareFiled compareFiled = i.getAnnotation(CompareFiled.class);
                    iMap.put(compareFiled.name(), getFieldByClasss(filedName, obj));
                }
            }
        });
        return iMap;
    }


    /**
     * 根据属性名获取属性元素
     *
     * @param fieldName
     * @param object
     * @return
     */
    private static String getFieldByClasss(String fieldName, Object object) {
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                String filedType=field.getGenericType().toString();
                if("class java.lang.Double".equals(filedType)){
                    DecimalFormat df   = new DecimalFormat("######0.00");
                    Double num = (Double) Optional.ofNullable(field.get(object)).orElseGet(()->0.00);
                    return df.format(num);
                }
                if("class java.math.BigDecimal".equals(filedType)){
                    BigDecimal bd = new BigDecimal(Optional.ofNullable(field.get(object)).orElseGet(()->0.00).toString());
                    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                    return bd.toString();
                }
                return Optional.ofNullable(field.get(object)).orElseGet(String::new).toString();
            } catch (Exception e) {
                log.info(fieldName+": "+e.getMessage());
            }
        }
        return null;
    }

    /**
     * 比较对象属性值
     * @param newMap
     * @param oldMap
     */
    public static String mapDiff(Map<String,String> newMap,Map<String,String> oldMap){
        if(newMap==null||oldMap==null){
            return null;
        }
        Set<Map.Entry<String, String>> setNewMap = newMap.entrySet();
        Set<Map.Entry<String, String>> setOldMap = oldMap.entrySet();
        StringBuilder newSb= new StringBuilder();
        StringBuilder oldSb= new StringBuilder();
        setNewMap.stream().flatMap(x->setOldMap.stream().
                filter(y->{
                    String newValue = Optional.ofNullable(x.getValue()).orElseGet(String::new).trim();
                    String oldValue = Optional.ofNullable(y.getValue()).orElseGet(String::new).trim();
                    if(x.getKey().equals(y.getKey())&& !newValue.equals(oldValue)){
                        newSb.append(x.getKey()).append(":").append(newValue).append("；");
                        oldSb.append(y.getKey()).append(":").append(oldValue).append("；");
                    }
                    return true;
                })
        ).collect(Collectors.toList());
        return String.format("【修改前】：%s \n 【修改后】：%s",oldSb.toString(),newSb.toString());
    }
}
