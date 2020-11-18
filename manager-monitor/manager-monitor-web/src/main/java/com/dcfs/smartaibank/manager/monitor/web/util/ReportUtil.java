package com.dcfs.smartaibank.manager.monitor.web.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 工具类
 *
 * @author wangtingo
 */

public final class ReportUtil {

    private ReportUtil() {
    }

    /**
     * 故障类型——单个外设
     */
    public static final String DEV_PERIPHERAL = "PERIPHERAL";

    /**
     * 故障类型——所有设备
     */
    public static final String DEV_DEVICE = "DEVICE";

    /**
     * 故障类型——设备
     */
    public static final String DEV_EQUIPMENT = "EQUIPMENT";

    /**
     * 故障类型——机具
     */
    public static final String DEV_TOOL = "TOOL";

    /**
     * 故障类型——APP
     */
    public static final String DEV_APP = "APP";

    /**
     * 故障类型——网络
     */
    public static final String DEV_NETWORK = "NETWORK";

    /**
     * 故障类型—工作时间
     */
    public static final String WORK_TIME = "WORK_TIME";

    /**
     * 故障类型—工作总时间
     */
    public static final String TOTAL_TIME_INT = "TOTAL_TIME_INT";

    /**
     * 批量保存条数
     */
    public static final int BATCH_SAVE_SIZE = 1000;

    /**
     * 字符串分隔符
     */
    public static final String STR_SPLIT = "_";

    /**
     * 错账类型—处理时间
     */
    public static final String ACCOUNT_DEAL = "ACCOUNTDEAL";

    /**
     * 设备费用
     */
    public static final String DEV_COST = "DEVCOST";

    /**
     * 设备管理员响应速度
     */
    public static final String MANAGER_ANSWER = "MANAGERANSWER";

    /**
     * 设备管理员处理速度
     */
    public static final String MANAGER_DEAL = "MANAGERDEAL";

    /**
     * 设备耗材
     */
    public static final String DEV_SUPPLY = "DEVSUPPLY";

    /**
     * 自助机具交易质量
     */
    public static final String DEV_TRAN = "DEVTRAN";

    /**
     * 总时间
     */
    public static final String DIFFER_TIME_INT = "DIFFER_TIME_INT";

    /**
     * 石嘴山报表默认字段
     */
    public static final String[] COLUMN_VALUE = {
            "MAC", "DEVMODELKEY", "DEVCLASSKEY",
            "DEVCLASSNAME", "BRANCH_NO", "BRANCH_NAME", "MID", "MMODELKEY", "MCLASSKEY",
            "MCLASSNAME", "MANUF_ID", "MANUF_NAME", "M_TYPE", "DIFFER_TIME_INT",
            "OCCUR_COUNT", "CREATE_TIME", "ALARM_LEVEL", "CYCLE_ID",
            "RESOURCE_CODE", "RESOURCE_NAME", "RESOURCE_CHANEL", "TRAN_STATUS", "TELLER_NO"
    };


    /**
     * @return
     * @desc 获得报表结束时间
     * @author ligg
     * @date 2016年8月23日 下午3:47:23
     */
    public static Date getReportDate() {
        Calendar cl = Calendar.getInstance();
        // 获取前一天日期
        Calendar berforCalendar = getBeforeDay(cl);
        Date date = berforCalendar.getTime();
        return date;
    }

    /**
     * @param record
     * @param key
     * @return
     * @desc 根据Key从容器中获取值
     * @author ligg
     * @date 2016年8月30日 上午10:15:45
     */
    @SuppressWarnings("unchecked")
    public static <T> T getValue(Map<String, Object> record, String key) {
        return (T) record.get(key);
    }

    /**
     * @param args
     * @return
     * @desc 获得组合键
     * @author ligg
     * @date 2016年8月30日 上午10:15:28
     */
    public static String getKey(String... args) {
        StringBuffer strbuffer = new StringBuffer();
        for (String str : args) {
            strbuffer.append(str);
            strbuffer.append(STR_SPLIT);
        }
        strbuffer.deleteCharAt(strbuffer.length() - 1);
        return strbuffer.toString();
    }

    /**
     * @param cl 当前日期
     * @return 当前日期的前一天日期
     * @desc 获得前一天日期
     * @author ligg
     * @date 2016年8月19日 上午10:27:23
     */
    private static Calendar getBeforeDay(Calendar cl) {
        int day = cl.get(Calendar.DATE);
        cl.set(Calendar.DATE, day - 1);
        cl.set(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH),
                cl.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        return cl;
    }

    /**
     * 获取字符串数组的拼接
     *
     * @param args
     * @return
     */
    public static String getArrayKey(String[] args) {
        StringBuffer strbuffer = new StringBuffer();
        for (int i = 0; i < args.length - 1; i++) {
            String str = args[i];
            strbuffer.append(str);
            strbuffer.append(STR_SPLIT);
        }
        strbuffer.deleteCharAt(strbuffer.length() - 1);
        return strbuffer.toString();
    }

}
