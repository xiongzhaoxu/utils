

package com.oozol.utils;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TreeUtils {
    private static final Logger log = LoggerFactory.getLogger(TreeUtils.class);

    public TreeUtils() {
    }

    public static List<Long> asList(long[] arrs) {
        List<Long> newStrList = new ArrayList();
        if (arrs == null) {
            return null;
        } else {
            long[] var2 = arrs;
            int var3 = arrs.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Long obj = var2[var4];
                newStrList.add(obj);
            }

            return newStrList;
        }
    }

    public static <T> List<T> toTreeList(List<T> dataList) {
        List<T> treeList = new ArrayList();
        if (!CollectionUtils.isEmpty(dataList)) {
            treeList = (List)dataList.stream().filter((item) -> {
                if (item == null) {
                    return false;
                } else {
                    try {
                        return "0".equals(String.valueOf(PropertyUtils.getSimpleProperty(item, "parentId")));
                    } catch (Exception var2) {
                        log.warn("获取{}对象{}属性失败", item, "parentId");
                        return false;
                    }
                }
            }).collect(Collectors.toList());
            treeSetChildrens(dataList, (List)treeList);
        }

        return (List)treeList;
    }

    private static <T> List<T> getTreeList(List<T> dataList, Long parentId) {
        List<T> treeList = new ArrayList();
        if (!CollectionUtils.isEmpty(dataList) && parentId != null) {
            treeList = (List)dataList.stream().filter((item) -> {
                if (item == null) {
                    return false;
                } else {
                    try {
                        return String.valueOf(parentId).equals(String.valueOf(PropertyUtils.getSimpleProperty(item, "parentId")));
                    } catch (Exception var3) {
                        log.warn("获取{}对象{}属性失败", item, "id");
                        return false;
                    }
                }
            }).collect(Collectors.toList());
            treeSetChildrens(dataList, (List)treeList);
        }

        return (List)treeList;
    }

    private static <T> void treeSetChildrens(List<T> dataList, List<T> treeList) {
        Collections.sort(treeList, new Comparator<T>() {
            public int compare(T o1, T o2) {
                int sort1 = 0;
                int sort2 = 0;

                try {
                    sort1 = (Integer)PropertyUtils.getSimpleProperty(o1, "sortNo");
                    sort2 = (Integer)PropertyUtils.getSimpleProperty(o2, "sortNo");
                } catch (Exception var6) {
                    TreeUtils.log.warn("转树结构时获取属性异常", var6);
                }

                return sort1 - sort2;
            }
        });
        Iterator var2 = treeList.iterator();

        while(var2.hasNext()) {
            T tree = (T) var2.next();
            Long treeId = null;

            try {
                treeId = (Long)PropertyUtils.getSimpleProperty(tree, "id");
                if (treeId != null) {
                    PropertyUtils.setSimpleProperty(tree, "children", getTreeList(dataList, treeId));
                }
            } catch (Exception var6) {
                log.warn("转树结构时获取属性异常", var6);
            }
        }

    }

    public static <K, V> List<K> getKeyByValue(Map<K, V> map, V v) {
        List<K> keys = Lists.newArrayList();
        Iterator var3 = map.entrySet().iterator();

        while(var3.hasNext()) {
            Entry<K, V> m = (Entry)var3.next();
            if (m != null && m.getValue() != null && v != null && String.valueOf(m.getValue()).equals(String.valueOf(v))) {
                keys.add(m.getKey());
            }
        }

        return keys;
    }
}
