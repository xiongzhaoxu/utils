package com.sinry.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinry.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.getPropertyDescriptors;

/**
 * @version v1.0
 * @CreationTime: - 2019/3/11 6:43 PM
 * @Description:
 */
@Slf4j
public class BeansUtils {

    public BeansUtils() {
    }

    public static void copyProperties(Object source, Object target) {
        if (source != null) {
            copyProperties(source, target, (Class)null, (String[])null);
        }
    }

    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        } else if (isPrimitive(targetClass)) {
            return (T) source;
        } else {
            T target = BeanUtils.instantiateClass(targetClass);
            copyProperties(source, target);
            return target;
        }
    }

    public static <T, S> List<T> copyProperties(List<S> sources, Class<T> target, boolean containNull) {
        if (!containNull && sources != null) {
            sources = (List)sources.stream().filter((item) -> {
                return item != null;
            }).collect(Collectors.toList());
        }

        return copyProperties(sources, target);
    }

    public static <T, S> List<T> copyProperties(List<S> sources, Class<T> target) {
        if (sources == null) {
            return null;
        } else if (sources.size() > 0 && sources.get(0) instanceof Map) {
            return map2Obj(sources, target);
        } else {
            List<T> result = new ArrayList();
            Iterator var3 = sources.iterator();

            while(var3.hasNext()) {
                Object source = var3.next();
                if (isPrimitive(target)) {
                    result.add((T) source);
                } else {
                    T targetBean = BeanUtils.instantiateClass(target);
                    copyProperties(source, targetBean);
                    result.add(targetBean);
                }
            }

            return result;
        }
    }

    public static <T> T of(Class<T> t) {
        return BeanUtils.instantiateClass(t);
    }

    public static <T> List<T> as(T... ts) {
        return Arrays.asList(ts);
    }

    private static void copyProperties(Object source, Object target, @Nullable Class<?> editable, @Nullable String... ignoreProperties) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() + "] not assignable to Editable class [" + editable.getName() + "]");
            }

            actualEditable = editable;
        }

        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
        List<String> ignoreList = ignoreProperties != null ? Arrays.asList(ignoreProperties) : null;
        PropertyDescriptor[] propertyDescriptors = targetPds;
        int tarLen = targetPds.length;

        for(int i = 0; i < tarLen; ++i) {
            PropertyDescriptor targetPd = propertyDescriptors[i];
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }

                            Object value = readMethod.invoke(source);
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }

                            if (ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                                if (writeMethod.getParameterTypes()[0].isAssignableFrom(List.class) && readMethod.getReturnType().isAssignableFrom(List.class) && value instanceof List) {
                                    List coList = (List)value;
                                    Object propValue = null;
                                    if (!CollectionUtils.isEmpty(coList)) {
                                        Class targetType = null;

                                        try {
                                            targetType = (Class) getListActualType(target, sourcePd);
                                        } catch (Exception e) {
                                            targetType = null;
                                            log.info("error:{}", e.getMessage());
                                        }

                                        if (targetType != null) {
                                            if (coList.get(0) instanceof Map) {
                                                propValue = map2Obj(coList, targetType);
                                            } else {
                                                propValue = copyProperties(coList, targetType);
                                            }
                                        } else {
                                            propValue = value;
                                        }

                                        writeMethod.invoke(target, propValue);
                                    }

                                    if (coList != null && coList.isEmpty()) {
                                        writeMethod.invoke(target, new ArrayList());
                                    }
                                } else {
                                    writeMethod.invoke(target, value);
                                }
                            } else if (!writeMethod.getParameterTypes()[0].isPrimitive() || !readMethod.getReturnType().isPrimitive()) {
                                Object propValue = copyProperties(value, writeMethod.getParameterTypes()[0]);
                                writeMethod.invoke(target, propValue);
                            }
                        } catch (Throwable var20) {
                            throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target", var20);
                        }
                    }
                }
            }
        }

    }

    private static Type getListActualType(Object target, PropertyDescriptor sourcePd) throws NoSuchFieldException {
        Field tp = target.getClass().getDeclaredField(sourcePd.getName());
        ParameterizedType parameterizedType = (ParameterizedType)tp.getGenericType();
        Type[] genericTypes = parameterizedType.getActualTypeArguments();
        return genericTypes != null && genericTypes.length > 0 ? genericTypes[0] : null;
    }


    /**
     * 此方法实现JDBCTemplate
     * 返回的Map集合对数据的自动
     * 封装功能
     * List集合存储着一系列的MAP
     * 对象，obj为一个javaBean
     * @param list Map集合
     * @param clz javaBean对象
     * @return
     */
    public static <T> List<T> map2Obj(List list, Class<T> clz) {
        String jsonStr = JSON.toJSONString(list);
        return JSONObject.parseArray(jsonStr, clz);
    }

    public static <T> T map2Obj(Map map, Class<T> clz) {
        String jsonStr = JSON.toJSONString(map);
        return JSONObject.parseObject(jsonStr, clz);
    }

    public static Map<String, Object> obj2map(Object obj) throws  BusinessException {
        try {
            return convertBean(obj);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    private static Map<String,Object> convertBean(Object bean) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map<String,Object> returnMap = new HashMap<>(10);
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            Method readMethod = descriptor.getReadMethod();

            if ("class".equals(propertyName)) {
                continue;
            }
            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                readMethod.setAccessible(true);
            }

            Object value = readMethod.invoke(bean);
            if (value != null) {
                if (!isPrimitive(descriptor.getPropertyType())
                        && !readMethod.getReturnType().isAssignableFrom(List.class)){
                    //循环输出值
                    PropertyDescriptor[] targetPds = getPropertyDescriptors(readMethod.getReturnType());
                    for (int j = 0; j < targetPds.length; j ++) {
                        Object propValue = targetPds[j].getReadMethod().invoke(value);
                        if ("class".equals(targetPds[j].getName())) {
                            continue;
                        }
                        if (propValue != null) {
                            returnMap.put(String.format("%s.%s", descriptor.getName(), targetPds[j].getName()), propValue);
                        }
                    }

                } else if(!isPrimitive(descriptor.getPropertyType())
                        && readMethod.getReturnType().isAssignableFrom(List.class)) {
                    if (value instanceof HashMap) {
                        returnMap.put(propertyName, value);
                    } else {
                        collectionReadProp(returnMap, descriptor, (List) value);
                    }
                } else {
                    Object result = readMethod.invoke(bean, new Object[0]);
                    if (result != null) {
                        returnMap.put(propertyName, result);
                    }
                }
            }

        }
        return returnMap;
    }

    private static void collectionReadProp(Map<String, Object> returnMap,
                                           PropertyDescriptor descriptor, Collection value)
            throws IllegalAccessException, InvocationTargetException {
        Object[] list = value.toArray();
        for (int k = 0; k < list.length; k ++) {
            PropertyDescriptor[] targetPds = getPropertyDescriptors(list[k].getClass());
            for (int j = 0; j < targetPds.length; j ++) {
                if ("class".equals(targetPds[j].getName()) || "empty".equals(targetPds[j].getName())) {
                    continue;
                }
                Object propValue = targetPds[j].getReadMethod().invoke(list[k], new Object[0]);
                if ("bytes".equals(targetPds[j].getName())) {
                    returnMap.put(String.format("%s[%s]",
                            descriptor.getName(), k), new String((byte[]) propValue));
                    continue;
                }

                if (propValue != null) {
                    returnMap.put(String.format("%s[%s].%s",
                            descriptor.getName(), k, targetPds[j].getName()), propValue);
                }
            }
        }
    }

    public static boolean isPrimitive(Class clz) {
        if (clz.isPrimitive() || clz == Byte.class || clz == Character.class
                || clz == Boolean.class || clz == Short.class
                || clz == Integer.class || clz == Long.class || clz == String.class
                || clz == Float.class || clz == Double.class || clz == BigDecimal.class) {
            return true;
        }
        return false;
    }

    /**
     * 功能：将一个以2的整数次方为和加在一起的一个整数进行分拆，分拆成一个个2的整数次方形式
     *
     * @param n 要拆分的数
     * @return
     */
    public static List<Long> splitPower(Long n) {
        List<Long> list = new ArrayList<Long>();
        if (n == null) {
            return  list;
        }

        String s = Long.toBinaryString(n);
        int len = s.length();
        for (int i = 0; i < len; i++) {
            Long cur = Long.parseLong(s.substring(len - i - 1, len - i));
            if (cur > 0) {
                list.add((long) Math.pow(2, i));
            }
        }
        return list;
    }


    /**
     * 字符串转字符串集合
     *
     * @param str
     * @return
     */
    public static List<String> strToList(String str) {
        List<String> strList = new ArrayList<>();

        if (str == null) {
            return strList;
        }
        String[] strArr = str.split(",");
        for (String s : strArr) {
            strList.add(s);
        }
        return strList;
    }


    public static <T> T copyDepthProperties(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        return JSONObject.parseObject(JSONObject.toJSONString(source), targetClass);
    }


    public static <T, S> List<T> copyDepthProperties(List<S> sources, Class<T> targetClass) {
        if (sources == null) {
            return null;
        }
        return JSONObject.parseArray(JSONObject.toJSONString(sources), targetClass);
    }

}
