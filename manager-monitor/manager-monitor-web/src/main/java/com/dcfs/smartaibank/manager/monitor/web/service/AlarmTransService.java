package com.dcfs.smartaibank.manager.monitor.web.service;


import com.dcfs.smartaibank.manager.monitor.web.domian.AlarmTranInfo;
import com.dcfs.smartaibank.manager.monitor.web.param.AlarmTransParams;
import com.github.pagehelper.PageInfo;

/**
 * 预警交易service
 *
 * @author liangchenglong
 * @date 2019/6/20 10:56
 * @since 1.0.0
 */
public interface AlarmTransService {

    /**
     * 查询预警交易信息
     *
     * @param alarmTransParams 查询参数
     * @return 预警交易结果对象
     */
    PageInfo<AlarmTranInfo> getAlarmTranInfo(AlarmTransParams alarmTransParams);

    /**
     * 预警解除操作
     *
     * @param transId
     */
    void removeTrans(String transId);
    /**
     * 查询预警交易信息
     *
     * @param index 查询参数
     * @param alarmTransParams 查询参数
     * @return 预警交易结果对象
     */
    PageInfo<AlarmTranInfo> getAlarmTranInfobyLevel(String index, AlarmTransParams alarmTransParams);
}
