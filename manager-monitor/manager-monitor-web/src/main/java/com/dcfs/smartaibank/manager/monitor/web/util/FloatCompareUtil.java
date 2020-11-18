package com.dcfs.smartaibank.manager.monitor.web.util;

/**
 * 比较两个数大小,比较结果(高于 、 低于 、 持平)
 *
 * @author wangjzm
 * @data 2019/07/29 10:07
 * @since 1.0.0
 */
public final class FloatCompareUtil {
    private FloatCompareUtil() {
    }

    private static final String MORE_THAN = "高于";
    private static final String LESS_THAN = "低于";
    private static final String EQUALS = "持平";

    /**
     * 比较两个数大小
     * 1. f1>f2 高于
     * 2. f1<f2 低于
     * 3. f1=f2 持平
     *
     * @param f1 比较数
     * @param f2 平均值
     * @return 比较结果(高于 、 低于 、 持平)
     */
    public static String getCompareResult(float f1, float f2) {
        int compare = Float.compare(f1, f2);
        if (compare == 1) {
            return MORE_THAN;
        } else if (compare == -1) {
            return LESS_THAN;
        } else {
            return EQUALS;
        }
    }
}
