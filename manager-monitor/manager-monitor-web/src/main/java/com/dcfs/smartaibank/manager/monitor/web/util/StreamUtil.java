package com.dcfs.smartaibank.manager.monitor.web.util;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Stream工具类
 *
 * @author wangjzm
 * @data 2019/07/22 10:39
 * @since 1.0.0
 */
public final class StreamUtil {
    private StreamUtil() {

    }

    /**
     * 按照关键元素进行去重
     *
     * @param keyExtractor 元素
     * @param <T>
     * @return Predicate实例
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
