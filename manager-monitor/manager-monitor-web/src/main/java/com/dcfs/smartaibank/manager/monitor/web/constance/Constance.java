package com.dcfs.smartaibank.manager.monitor.web.constance;

/**
 * 常量类
 *
 * @author tanchen1
 * @date 2019/7/4 9:06
 * @since
 */
public final class Constance {

    private Constance() {

    }
    /**
     * ALARMQUERYSUM  预警信息查询 查询全部
     */
    public static final String ALARMQUERYSUM = "sum";
    /**
     * ALARMQUERYUSER  预警信息查询 查询我处理的
     */
    public static final String ALARMQUERYUSER = "user";

    /**
     * 服务质量网点排名，升序
     */
    public static final String DESC = "DESC";

    /**
     * 服务质量网点排名，降序
     */
    public static final String ASC = "ASC";

    /**
     * 服务质量网点排名，前20
     */
    public static final Integer TOP_20 = 20;

    /**
     * 服务质量网点排名，前10
     */
    public static final Integer TOP_10 = 10;

    /**
     * 导出excel标题行
     */
    public static final Integer EXCEL_TITLE_INDEX = 1;

    /**
     * 导出excel表头行
     */
    public static final Integer EXCEL_HEAD_INDEX = 6;

    /**
     * 远程操作-日志操作类型 app
     */
    public static final String APP_LOG = "app";

    /**
     * 远程操作-日志操作类型 device
     */
    public static final String APP_DEVICE = "device";

    /**
     * 远程操作-线程获取返回结果超时设置 单位:分钟
     */
    public static final int FUTURE_TIME = 2;

    /**
     * 远程操作-调用管理端返回正确的状态码
     */
    public static final String CLIENT_CODE = "000000";

    /**
     * 报表查询时候，是否导出
     */
    public static final Boolean IS_EXPORT = true;

    /**
     * 处理时间-1
     */
    public static final int RESOLVE_TIME = 1;

    /**
     * 服务质量列表查询全部
     */
    public static final Integer QUERY_ALL = 5;

    /**
     * 服务质量列表查询未服务
     */
    public static final Integer QUERY_UN = 6;

    /**
     * 报表导出固定展示-统计时段
     */
    public static final String STATISTICAL_PERIOD = "统计时段";

    /**
     * 报表导出固定展示-统计时间
     */
    public static final String STATISTICAL_TIME = "统计时间";

    /**
     * 设备凭条耗材统计-报表title
     */
    public static final String EQUIPMENT_SUPPLIES_TITLE = "设备凭条耗材报表统计";

    /**
     * 设备管理人员故障处理速度报表统计-报表title
     */
    public static final String RESOLVE_TIME_TITLE = "设备管理人员故障处理速度报表统计";

    /**
     * 设备管理人员故障处理速度报表统计-报表title
     */
    public static final String RESPONSE_TIME_TITLE = "设备管理人员故障响应速度报表统计";

    /**
     * 外设详情报表统计查询--title
     */
    public static final String PERIPHERAL_DETAILS_TITLE = "外设详情报表统计";

    /**
     * 交易质量报表统计的title
     */
    public static final String TRANS_QUALITY_TITLE = "交易质量报表统计";

    /**
     * 交易质量报表详情统计的title
     */
    public static final String TRANS_QUALITY_DETAIL_TITLE = "交易质量详情报表统计";

    /**
     * 错账速度考核报表统计的title
     */
    public static final String WRONG_ACCOUNT_TITLE = "错账速度考核报表统计";

    /**
     * 柜员满意度评价报表统计的title
     */
    public static final String SATISFACTION_TELLER_TITLE = "柜员满意度评价报表统计";

    /**
     * 总行视角各机构满意度评价报表统计的title
     */
    public static final String SATISFACTION_ORG_TITLE = "总行视角各机构满意度评价报表";
    /**
     * 设备故障情况报表统计的title
     */

    public static final String EQUIPMENT_FAILURE_TITLE = "机具故障情况报表统计";

    /**
     * 网络通讯故障率报表统计的title
     */
    public static final String NETWORK_FAILURE_RATE_TITLE = "网络通讯故障率报表统计";

    /**
     * 机具开机情况报表统计的title
     */
    public static final String DEVICE_START_INFO_TITLE = "机具开机情况报表统计";

    /**
     * 总行视角机构交易处理时长的title
     */
    public static final String ZH_VIEW_INFO_TITLE = "总行视角机构交易处理时长";

    /**
     * 行主管视角柜员交易代码处理时长的title
     */
    public static final String LEADER_VIEW_INFO_TITLE = "支行视角机构交易处理时长";
    /**
     * 总行视角等候时间数据报表的title
     */
    public static final String QU_WAITTIMEINFO_TITLE = "总行视角等候时间数据报表";

    /**
     * 支行视角客户等候时间的title
     */
    public static final String WAITTINGTIME_BRANCH_TITLE = "支行视角客户等候时间数据报表";
    /**
     * 支行视角柜员服务客户时间的title
     */
    public static final String TIME_SERVICE_TITLE = "支行视角柜员服务客户时间数据报表";

    /**
     * 支行柜员视角柜员交易代码处理时长的title
     */
    public static final String LEADER_VIEW_DEAL_TITLE = "支行柜员视角柜员交易代码处理时长数据报表";

    /**
     * 支行柜员视角柜员交易代码处理时长的title
     */
    public static final String TRADETIME_BRANCH_TITLE = "支行网点视角交易代码时长数据数据报表";


    /**
     * 和均值进行比较的记过--高于
     */
    public static final String OVERTOP_AVG = "高于";

    /**
     * 和均值进行比较的记过--持平
     */
    public static final String EQUAL_AVG = "持平";

    /**
     * 和均值进行比较的记过--低于
     */
    public static final String UNDER_AVG = "低于";

    /**
     * 终端外设故障汇总报表统计-报表title
     */
    public static final String PERIPHERAL_GATHER_TITLE = "终端外设故障汇总报表统计";

    /**
     * 外设故障率信息汇总一级表头长度
     */
    public static final int PERIPHERAL_GATHER_FIRST_HEADER_SIZE = 4;

    public static final String DEVICE_CLASS_KEY = "设备类型";
    public static final String DEVICE_MODEL_KEY = "设备型号";
    public static final String MANUFACTURER = "设备厂商";
    public static final String DEVICE_MODEL_ID = "终端号";
    public static final String NORMAL_TIME_INT = "计划开机时间（分钟）";
    public static final String FAULT_TIME_INT = "故障时间（分钟）";
    public static final String RATE_NUM = "设备故障率（%）";

    /**
     * 机具开机情况标识
     */
    public static final String NORMAL = "NORMAL";

    /**
     * 机具故障率标识
     */
    public static final String TOOL = "TOOL";

    /**
     * 网络通讯故障率报表
     */
    public static final String NETWORK = "NETWORK";

    /**
     * 图片类型
     */
    public static final String PICTURE_TYPE = "image/jpeg";

    /**
     * zip类型
     */
    public static final String ZIP_TYPE = "application/gzip";

    /**
     * 图片类型后缀
     */
    public static final String PICTURE_TYPE_SUFFIX = ".jpg";

    /**
     * gz类型后缀
     */
    public static final String ZIP_TYPE_SUFFIX = ".gz";
    /**
     * zip类型后缀
     */
    public static final String ZIP2_TYPE_SUFFIX = ".zip";



    /**
     * 红颜色
     */
    public static final String RED_COLOR = "red";
    /**
     * 黄颜色
     */
    public static final String YELLOW_COLOR = "yellow";
    /**
     * 蓝颜色
     */
    public static final String BLUE_COLOR = "blue";
}
