package com.dcfs.smartaibank.manager.monitor.core.context;

/**
 * 常量定义
 *
 * @author jiazw
 */
public interface Constants {
	/**
	 * 系统标识
	 */
	String SYSTEM = "SYSTEM";

	/**
	 * 消息标识-排队系统
	 */
	String SYSTEM_GUIDER = "GUIDER";

	/**
	 * 消息标识-填单系统
	 */
	String SYSTEM_FILLER = "FILLER";

	/**
	 * 消息标识-VTM
	 */
	String SYSTEM_VTM = "VTM";

	/**
	 * 消息标识-ATM
	 */
	String SYSTEM_ATM = "ATM";

	/**
	 * 消息标识
	 */
	String TYPE = "TYPE";
	/**
	 * 应用
	 */
	String TYPE_APP = "APP";
	/**
	 * 外设
	 */
	String TYPE_DEVICE = "DEVICE";
	/**
	 * 网络
	 */
	String TYPE_NETWORK = "NETWORK";
	/**
	 * 交易
	 */
	String TYPE_TRAN = "TRAN";
	/**
	 * 业务
	 */
	String TYPE_BUSI = "BUSI";
	/**
	 * 系统
	 */
	String TYPE_SYSTEM = "SYSTEM";
	/**
	 * 日志
	 */
	String TYPE_LOG = "LOG";

	String TYPE_FAULT = "FAULT";

	/**
	 * 消息模式（实时消息，定时消息）
	 */
	String MODE = "MODE";

	/**
	 * 实时消息
	 */
	String MODE_REAL = "REAL";

	/**
	 * 定时消息
	 */
	String MODE_TIMED = "TIMED";

	/**
	 * 操作类型
	 */
	String OPERATOR = "OPERATOR";
	String OPERATOR_ADD = "ADD";
	String OPERATOR_UPDATE = "UPDATE";
	String OPERATOR_ADDORUPDATE = "ADDORUPDATE";

	/**
	 * 创建时间
	 */
	String CREATE_TIME = "CREATE_TIME";

	/**
	 * 当前过滤规则集
	 */
	String CURRENT_FILTER_RULES = "CURRENT_FILTER_RULES";

	/**
	 * 当前预处理规则集
	 */
	String CURRENT_PREPARE_RULES = "CURRENT_PREPARE_RULES";

	/**
	 * 当前监控项
	 */
	String CURRENT_MONITOR_ITEM = "CURRENT_MONITOR_ITEM";

	/**
	 * 当前分析规则
	 */
	String CURRENT_ANALYZER_RULE = "CURRENT_ANALYZER_RULE";

	/**
	 * 当前预警规则集
	 */
	String CURRENT_ALARM_RULES = "CURRENT_ALARM_RULES";

	/**
	 * 当前正在处理的预警规则
	 */
	String CURRENT_ALARM_RULE = "CURRENT_ALARM_RULE";

	/**
	 * 当前通知规则集
	 */
	String CURRENT_NOTIFY_RULES = "CURRENT_NOTIFY_RULES";

	/**
	 * 当前通知规则
	 */
	String CURRENT_NOTIFY_RULE = "CURRENT_NOTIFY_RULE";
	/**
	 * 触发预警
	 */
	String FIRE_ALARM_RULES = "FIRE_ALARM_RULES";

	/**
	 * 触发的预警信息列表
	 */
	String FIRE_ALARM_LIST = "FIRE_ALARM_LIST";

	/**
	 * 监控预警参数
	 */
	String PARAMS = "PARAMS";

	/**
	 * 监控预警数据字典
	 */
	String DICTS = "DICTS";

	/**
	 * 外设标准
	 */
	String DEVICE_STANDARD = "STANDARD";

	/**
	 * 无标准
	 */
	String DEVICE_STANDARD_NONE = "NONE";

	/**
	 * WOSA标准
	 */
	String DEVICE_STANDARD_WOSA = "WOSA";

	/**
	 * WOSA标准设备状态字段名称
	 */
	String DEVICE_STATUS_FWDEVICE = "fwDevice";

	/**
	 * 外设状态列表
	 */
	String DEVICE_STATUS_LIST = "DEVICE_STATUS_LIST";

	/**
	 * 钞箱数组
	 */

	String CASH_UNIT_INFO = "CASH_UNIT_INFO";

	/**
	 * 监控结果
	 */
	String ANALYZER_RESULT = "ANALYZER_RESULT";

	/**
	 * 监控类型
	 */
	String M_TYPE = "M_TYPE";

	/**
	 * 预警关闭方式-登记关闭
	 */
	String CHECKIN_CLOSE = "1";
	/**
	 * 预警关闭方式-自动关闭
	 */
	String AUTO_CLOSE = "2";

	/**
	 * 公共信息, BODY
	 */
	String MONITOR_ITEM_ID = "MONITOR_ITEM_ID";
	String STATUS = "STATUS";
	String M_STATUS = "M_STATUS";
	String SEQ_NO = "SEQ_NO";
	String BRANCH_NO = "BRANCH_NO";
	String BRANCH_NAME = "BRANCH_NAME";
	String MAC = "MAC";
	String MID = "MID";
	String MNAME = "MNAME";
	String MMODELKEY = "MMODELKEY";
	String MCLASSKEY = "MCLASSKEY";
	String MCLASSNAME = "MCLASSNAME";
	String FIELD_NO = "FIELD_NO";
	String FIELD_NAME = "FIELD_NAME";
	String MANUF_ID = "MANUF_ID";
	String MANUF_NAME = "MANUF_NAME";
	String RECEIVE_TIME = "RECEIVE_TIME";
	String RECEIVE_TIME_INT = "RECEIVE_TIME_INT";
	String DEVMODELKEY = "DEVMODELKEY";
	String DEVCLASSKEY = "DEVCLASSKEY";
	String DEVCLASSNAME = "DEVCLASSNAME";
	String STATUS_CODE = "STATUS_CODE";
	String STATUS_DESC = "STATUS_DESC";
	String MTYPEKEY = "MTYPEKEY";
	String MTYPENAME = "MTYPENAME";

	/**
	 * 预警信息
	 */
	String ALARM_ID = "ALARM_ID";
	String ALARM_DATE = "ALARM_DATE";
	String ALARM_DESC = "ALARM_DESC";
	String ALARM_STATUS = "ALARM_STATUS";
	String ALARM_TYPE = "ALARM_TYPE";
	String ALARM_LEVEL_DESC = "ALARM_LEVEL_DESC";
	String ALARM_RULE_ID = "ALARM_RULE_ID";
	/**
	 * 预警级别列表
	 */
	String ALARM_LEVEL_LIST = "ALARM_LEVEL_LIST";

	/**
	 * 预警级别
	 */
	String ALARM_LEVEL = "ALARM_LEVEL";

	/**
	 * 预警结果列表
	 */
	String ALARM_RESULT_LIST = "ALARM_RESULT_LIST";

	/**
	 * 预警结果
	 */
	String ALARM_RESULT = "ALARM_RESULT";
	/**
	 * 预警状态-初始
	 */
	String ALARM_NEW = "1";
	/**
	 * 预警状态-处理中
	 */
	String ALARM_HANDLE = "2";
	/**
	 * 预警状态-已报修
	 */
	String ALARM_REPAIR = "3";
	/**
	 * 预警状态-已解除
	 */
	String ALARM_REMOVE = "4";
	/**
	 * 预警状态-已关闭
	 */
	String ALARM_CLOSE = "5";

	/**
	 * 交易信息
	 */
	String TRAN_CODE = "TRAN_CODE";
	String TRAN_NAME = "TRAN_NAME";
	String TRAN_TYPE = "TRAN_TYPE";
	String TRAN_TYPE_NAME = "TRAN_TYPE_NAME";
	String TRAN_AMT = "TRAN_AMT";
	String TRAN_STATUS = "TRAN_STATUS";
	String TRAN_CARD_NO = "TRAN_CARD_NO";
	String TRAN_CARD_TYPE = "TRAN_CARD_TYPE";
	String TRAN_CARD_TYPE_NAME = "TRAN_CARD_TYPE_NAME";
	String TRAN_RET_CODE = "TRAN_RET_CODE";
	String TRAN_RET_DESC = "TRAN_RET_DESC";
	String TIME_CONSUMED = "TIME_CONSUMED";
	String SERVICE_CODE = "SERVICE_CODE";
	String SERVICE_TYPE = "SERVICE_TYPE";
	String MESSAGE_TYPE = "MESSAGE_TYPE";
	String MESSAGE_CODE = "MESSAGE_CODE";
	String TRAN_CODE_PARAM = "TRAN_CODE_PARAM";
	String SERVICE_TYPE_LOCAL = "LOCAL";
	String SERVICE_TYPE_REMOTE = "REMOTE";
	String SERVICE_TYPE_COMPOSITE = "COMPOSITE";
	/**
	 * log
	 */
	String CONVERSATIONID = "CONVERSATIONID";
	String LOG_DATE = "LOG_DATE";
	String LOG_TIME = "LOG_TIME";
	String LOG_CONTENT = "LOG_CONTENT";
	String LOG_LOCATION = "LOG_LOCATION";
	String LOG_LOCATION_VIEW_LIFECYCLE = "VIEW_LIFECYCLE";
	String LOG_LOCATION_LOGIC_SERVER_PROXY = "LOGIC_SERVER_PROXY";
	String LOG_LOCATION_LOGIC_LOCAL_SERVICE = "LOGIC_LOCAL_SERVICE";
	String LOG_LOCATION_LOGIC_REMOTE_SERVICE = "LOGIC_REMOTE_SERVICE";
	String LOG_LOCATION_LOGIC_COMPOSITE_SERVICE = "LOGIC_COMPOSITE_SERVICE";
	String LOG_LOCATION_SERVICE_OVERTIME = "SERVICE_OVERTIME";

	String SOURCE_EVENT_CODE = "SOURCE_EVENT_CODE";
	String ACTION = "ACTION";
	String ACTION_TRANS_OPEN = "TRANS_OPEN";
	String ACTION_TRANS_CLOSE = "TRANS_CLOSE";
	String ACTION_SERVICE_CALL = "SERVICE_CALL";
	String ACTION_SERVICE_END = "SERVICE_END";
	String ACTION_SERVICE_OVERTIME = "SERVICE_OVERTIME";
	String ACTION_DEVICE_RESPONSE = "DEVICE_RESPONSE";
	String ACTION_DEVICE_CALL = "DEVICE_CALL";

	/**
	 * area
	 */
	String HANDLE_PROVINCE = "HANDLE_PROVINCE";
	String HANDLE_CITY = "HANDLE_CITY";
	String HANDLE_COUNTY = "HANDLE_COUNTY";

	/**
	 * device
	 */
	String DETAIL = "DETAIL";
	String DETAIL_DESC = "DETAIL_DESC";

	String PERIPHERAL_STATUS = "PERIPHERAL_STATUS";
	String PERIPHERAL_TIME = "PERIPHERAL_TIME";
	String APP_STATUS = "APP_STATUS";
	String APP_TIME = "APP_TIME";
	String NETWORK_STATUS = "NETWORK_STATUS";
	String NETWORK_TIME = "NETWORK_TIME";
}
