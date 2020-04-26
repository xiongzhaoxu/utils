package com.sinry.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * String工具类
 *
 */
@Slf4j
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static boolean isEmpty(Object str) {
        return str == null || str.toString().length() == 0;
    }

    public static boolean isNotBlank(String str) {
        return !StringUtils.isBlank(str);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 商品id生成
     */
    public static long genItemId() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //加上两位随机数
        Random random = new Random();
        int end2 = random.nextInt(99);
        //如果不足两位前面补0
        String str = millis + String.format("%02d", end2);
        long id = new Long(str);
        return id;
    }


    /**
     * 获取参数不为空值
     *
     * @param value defaultValue 要判断的value
     * @return value 返回值
     */
    public static <T> T nvl(T value, T defaultValue)
    {
        return value != null ? value : defaultValue;
    }

    /**
     * 截取xx接口的字符串
     *
     * @param text 字符串
     * @param tag  要去掉的字符串
     * @return
     */
    public static String trimEnd(String text, String tag) {
        if (isNullOrEmpty(text)) {
            return text;
        }

        text = text.endsWith(tag) ? text.substring(0, text.length() - tag.length()) : text;
        return text;
    }

    /**
     * 加一拼成字符串
     * 如传入 10, 4 返回 1005
     *
     * @param parentId parentId
     * @param maxId    maxId
     * @return String 加1
     */
    public static String addOne(String parentId, String maxId) {
        int ten = 10;
        if (Constant.STR_ZERO.equals(parentId)) {
            parentId = "";
        }
        if (isNullOrEmpty(maxId)) {
            return parentId + "01";
        }

        maxId = maxId.substring(maxId.length() - 2);

        int result = Integer.parseInt(maxId) + 1;

        if (result < ten) {
            return parentId + "0" + result;
        } else {
            return parentId + result + "";
        }
    }



    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object)
    {
        return object == null;
    }

    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object)
    {
        return !isNull(object);
    }

    /**
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
     *
     * @param obj obj
     * @return boolean
     */
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }

        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }

        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (Object anObject : object) {
                if (!isNullOrEmpty(anObject)) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }

    /**
     * 判断列表是否为空
     *
     * @return boolean
     */
    public static boolean listIsEmpty (Object o) {

        if (o instanceof List) {
            return !(((List) o).size() > 0);
        }

        return isNullOrEmpty(o);
    }

    /**
     * 生成key
     *
     * @param prefix     前缀
     * @param className  类名
     * @param methodName 方法名
     * @return prefix className.methodName
     */
    public static String genKey(String prefix, String className, String methodName) {
        return prefix + "userId_" +
                //ShiroUtils.getUserId() +
                "_" +
                className +
                "." +
                methodName;
    }

    public static String genKey(String className, String methodName,Object[] args){
        StringBuilder key = new StringBuilder();
        key.append(className).append("#");
        key.append(methodName).append("(");
        for (Object obj : args) {
            key.append(obj.toString()).append(",");
        }
        key.deleteCharAt(key.length() - 1);
        key.append(")");
        byte[] hashKey=null;
        String keyStr;
        try{
            hashKey = MessageDigest.getInstance("MD5").digest(key.toString().getBytes("UTF-8"));
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        log.info(hashKey.length+"");
        MD5Utils md5Encoder=new MD5Utils();
        keyStr=md5Encoder.encode(hashKey);
        return keyStr;
    }

    public static List<Long> str2Long (List<String> list) {
        List<Long> listLong = new ArrayList<>();
        if (list != null) {
            return listLong;
        }
        for (String str: list) {
            listLong.add(
                    Long.parseLong(str)
            );
        }
        return listLong;
    }


    /**
     * 下划线转驼峰
     * @param str
     * @return
     */
    public static StringBuffer camel(StringBuffer str) {
        //利用正则删除下划线，把下划线后一位改成大写
        Pattern pattern = compile("_(\\w)");
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if(matcher.find()) {
            sb = new StringBuffer();
            //将当前匹配子串替换为指定字符串，并且将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个StringBuffer对象里。
            //正则之前的字符和被替换的字符
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
            //把之后的也添加到StringBuffer对象里
            matcher.appendTail(sb);
        }else {
            return sb;
        }
        return camel(sb);
    }


    /**
     * 驼峰转下划线
     * @param str
     * @return
     */
    public static StringBuffer underline(StringBuffer str) {
        Pattern pattern = compile("[A-Z]");
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if(matcher.find()) {
            sb = new StringBuffer();
            //将当前匹配子串替换为指定字符串，并且将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个StringBuffer对象里。
            //正则之前的字符和被替换的字符
            matcher.appendReplacement(sb,"_"+matcher.group(0).toLowerCase());
            //把之后的也添加到StringBuffer对象里
            matcher.appendTail(sb);
        }else {
            return sb;
        }
        return underline(sb);
    }


    /**
     * 设置缓存过期时间随机数
     * @param origin
     * @param bound
     * @return
     */
    public static Long generateRandomCacheTime(Long origin, Long bound) {
        if (origin.equals(bound)) {
            return ThreadLocalRandom.current().nextLong(origin,(bound + 300));
        }
        return ThreadLocalRandom.current().nextLong(origin,bound);
    }


    /**
     * 字符串转集合
     *
     * @param str
     * @return
     */
    public static List<String> stringToList(String str){
        if (str == null) {
            return new ArrayList<>();
        }
        String[] strs=str.split(",");
        List list=Arrays.asList(strs);
        return list;
    }


    public static  String formatUrlMap(Map<String, Object> paraMap, boolean urlEncode, boolean keyToLower) {
        String buff;
        try {
            List<Map.Entry<String, Object>> infoIds = new ArrayList<>(paraMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {

                @Override
                public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                    return (o1.getKey()).compareTo(o2.getKey());
                }
            });

            // 构造URL 键值对的格式
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, Object> item : infoIds) {
                if (!"".equals(item.getKey())) {
                    String key = item.getKey();
                    Object val = item.getValue();
                    buf.append(val);
                }

            }
            buff = buf.toString();
        } catch (Exception e) {
            return null;
        }
        return buff;
    }

    public static String MD5(String key) {

        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }



}
