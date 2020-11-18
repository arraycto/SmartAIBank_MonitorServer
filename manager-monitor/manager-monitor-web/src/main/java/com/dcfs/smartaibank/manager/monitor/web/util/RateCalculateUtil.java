package com.dcfs.smartaibank.manager.monitor.web.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author wangjzm
 * @data 2019/07/19 17:03
 * @since 1.0.0
 */
public final class RateCalculateUtil {
    private RateCalculateUtil() {
    }

    /**
     * @param num1 除数
     * @param num2 被除数
     * @return 计算后的百分比，去除了'%'，，保留两位小数
     */
    public static BigDecimal division(int num1, int num2) {
        String rate = "0.00";
        //定义格式化起始位数
        StringBuilder format = new StringBuilder("0.00");
        if (num2 != 0 && num1 != 0) {
            DecimalFormat dec = new DecimalFormat(format.toString());
            rate = dec.format((double) num1 / num2 * 100);
            while (true) {
                if (rate.equals(format)) {
                    format.append("0");
                    DecimalFormat dec1 = new DecimalFormat(format.toString());
                    rate = dec1.format((double) num1 / num2 * 100);
                } else {
                    break;
                }
            }
        }
        return new BigDecimal(rate);
    }

}
