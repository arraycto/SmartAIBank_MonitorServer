package com.dcfs.smartaibank.manager.monitor.web.job.task;

/**
 * 报表任务接口
 * @date 2016年8月30日 上午9:59:28
 * @author wangtingo
 */
public interface IReportTask {
	/**
	 * 交易日期，具体逻辑有子类实现
	 * @author ligg
	 * @date 2016年8月30日 上午9:58:15
	 * @return
	 */
	boolean dateCheck();

	/**
	 * 调用报表生成任务
	 * @param  reportType
	 * @date 2016年8月30日 上午9:58:37
	 */
	void callTask(String reportType);

}
