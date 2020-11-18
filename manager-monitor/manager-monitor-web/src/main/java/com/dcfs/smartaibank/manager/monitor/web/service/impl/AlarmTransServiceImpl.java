package com.dcfs.smartaibank.manager.monitor.web.service.impl;

import com.dcfs.smartaibank.core.exception.BusinessException;
import com.dcfs.smartaibank.manager.monitor.web.constance.Constance;
import com.dcfs.smartaibank.manager.monitor.web.dao.AlarmQueryDao;
import com.dcfs.smartaibank.manager.monitor.web.domian.AlarmLevel;
import com.dcfs.smartaibank.manager.monitor.web.domian.AlarmTranInfo;
import com.dcfs.smartaibank.manager.monitor.web.param.AlarmTransParams;
import com.dcfs.smartaibank.manager.monitor.web.service.AlarmTransService;
import com.dcfs.smartaibank.manager.monitor.web.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liangchenglong
 * @date 2019/6/20 10:58
 * @since 1.0.0
 */

@Service
public class AlarmTransServiceImpl implements AlarmTransService {

    @Autowired
    private AlarmQueryDao alarmQueryDao;

    @Override
    public PageInfo<AlarmTranInfo> getAlarmTranInfo(AlarmTransParams alarmTransParams) {
        try {
            String[] time = DateUtil.getLocalDateTimeOfThree();
            alarmTransParams.setStartDateTime(time[1]);
            alarmTransParams.setEndDateTime(time[0]);
            PageHelper.startPage(alarmTransParams.getPageNum(), alarmTransParams.getPageSize());
            List<AlarmTranInfo> alarmTranInfo = alarmQueryDao.queryAlarmTranInfo(alarmTransParams);
            return new PageInfo<>(alarmTranInfo);
        } catch (Exception e) {
            throw new BusinessException("data.access", e);
        }
    }

    @Override
    public void removeTrans(String transId) {
        alarmQueryDao.removeTran(transId);
    }

    @Override
    public PageInfo<AlarmTranInfo> getAlarmTranInfobyLevel(String index, AlarmTransParams alarmTransParams) {
        try {
            String[] time = DateUtil.getLocalDateTimeOfThree();
            alarmTransParams.setStartDateTime(time[1]);
            alarmTransParams.setEndDateTime(time[0]);
            PageHelper.startPage(alarmTransParams.getPageNum(), alarmTransParams.getPageSize());
            List<AlarmTranInfo> alarmTranInfo = null;
            if (index.equals(Constance.RED_COLOR)) {
                alarmTranInfo =
                        alarmQueryDao.queryAlarmTranInfobyLevel(alarmTransParams.getBranchNo(),
                                AlarmLevel.RED.getCode());
            } else if (index.equals(Constance.YELLOW_COLOR)) {
                alarmTranInfo =
                        alarmQueryDao.queryAlarmTranInfobyLevel(alarmTransParams.getBranchNo(),
                                AlarmLevel.ORANGE.getCode());
            } else if (index.equals(Constance.BLUE_COLOR)) {
                alarmTranInfo =
                        alarmQueryDao.queryAlarmTranInfobyLevel(alarmTransParams.getBranchNo(),
                                AlarmLevel.YELLOW.getCode());
            }
            return new PageInfo<>(alarmTranInfo);
        } catch (Exception e) {
            throw new BusinessException("data.access", e);
        }

    }
}
