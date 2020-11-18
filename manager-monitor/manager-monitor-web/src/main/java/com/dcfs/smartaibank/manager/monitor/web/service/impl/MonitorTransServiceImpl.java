package com.dcfs.smartaibank.manager.monitor.web.service.impl;

import com.dcfs.smartaibank.manager.monitor.web.dao.MonitorTransDao;
import com.dcfs.smartaibank.manager.monitor.web.domian.TimePhasedTransCount;
import com.dcfs.smartaibank.manager.monitor.web.domian.TranCount;
import com.dcfs.smartaibank.manager.monitor.web.domian.TranStatusSum;
import com.dcfs.smartaibank.manager.monitor.web.domian.TransDetail;
import com.dcfs.smartaibank.manager.monitor.web.service.MonitorTransService;
import com.dcfs.smartaibank.manager.monitor.web.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author wangjzm
 * @data 2019/07/01 14:26
 * @since 1.0.0
 */
@Service
@Slf4j
public class MonitorTransServiceImpl implements MonitorTransService {
    @Autowired
    private MonitorTransDao monitorTransDao;

    /**
     * 查询记录数：查询交易记录时指定
     */
    @Value("${monitor.trans.details.query-numbers:20}")
    private Integer queryNumber;
    /**
     * 交易折线图统计区间时间间隔（单位：分钟）
     */
//    private static final String TRAN_LINECHART_INTERVAL = "TRAN_LINECHART_INTERVAL";
    /**
     * 交易折线图展示长度
     */
//    private static final String TRAN_LINECHART_LENGTH = "TRAN_LINECHART_LENGTH";
    /**
     * 默认交易折线图统计区间时间间隔
     */
    private static final int DEFAULT_INTERVAL = 60;
    /**
     * 默认交易折线图展示长度
     */
    private static final int DEFAULT_LENGTH = 11;

    @Override
    public TranStatusSum selectEachStatusCountGatherInfo(String branchId, Integer branchStatsType) {
        TranStatusSum tranStatusSum =
                monitorTransDao.selectEachStatusCountGatherInfo(branchId, branchStatsType, getCurrentDate());
        if (tranStatusSum.getTotalCount() != 0) {
            tranStatusSum.setFailureRate(100 - tranStatusSum.getExceptionRate() - tranStatusSum.getSuccessRate());
        }
        return tranStatusSum;
    }

    @Override
    public TranCount selectTransCount(String branchId, Integer branchStatsType) {
        TranCount tranCount =
                monitorTransDao.selectTransCount(branchId, branchStatsType, getCurrentDate());
        if (tranCount.getTotalCount() != 0) {
            tranCount.setStmRate(100 - tranCount.getAtmRate() - tranCount.getBsrRate());
        }
        return tranCount;
    }

    @Override
    public List<TransDetail> selectTransDetail(String branchId,
                                               Integer branchStatsType) {
        return monitorTransDao.selectTransDetail(branchId, getCurrentDate(), branchStatsType, this.queryNumber);
    }

    @Override
    public List<TimePhasedTransCount> selectTimePhasedTransCount(String branchId, String currentTime) {
        // 1.从规则中心获取 统计周期时间间隔（单位：分钟）和展示长度
        // TODO

        Integer interval = DEFAULT_INTERVAL;
        Integer showLength = DEFAULT_LENGTH;
        String endDate = DateUtil.getEndTime(interval, currentTime);
        return monitorTransDao.selectTimePhasedTransCount(branchId, interval, showLength, endDate);
    }

    /**
     * 获取当前日期，格式 ：yyyyMMdd
     *
     * @return
     */
    private String getCurrentDate() {
        return DateUtil.getStrDate(new Date());
    }

}
