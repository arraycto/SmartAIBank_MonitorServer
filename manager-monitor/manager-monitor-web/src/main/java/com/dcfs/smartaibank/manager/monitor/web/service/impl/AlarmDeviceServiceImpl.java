package com.dcfs.smartaibank.manager.monitor.web.service.impl;

import com.dcfs.smartaibank.core.exception.BusinessException;
import com.dcfs.smartaibank.manager.monitor.web.constance.Constance;
import com.dcfs.smartaibank.manager.monitor.web.dao.AlarmQueryDao;
import com.dcfs.smartaibank.manager.monitor.web.enums.AlarmCloseType;
import com.dcfs.smartaibank.manager.monitor.web.domian.AlarmDealInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.AlarmDealStatus;
import com.dcfs.smartaibank.manager.monitor.web.domian.AlarmInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.AlarmLevel;
import com.dcfs.smartaibank.manager.monitor.web.domian.AlarmMaintainer;
import com.dcfs.smartaibank.manager.monitor.web.param.AlarmParams;
import com.dcfs.smartaibank.manager.monitor.web.param.AlarmRepairInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.AlarmSimpleResult;
import com.dcfs.smartaibank.manager.monitor.web.param.AlarmWorkInfo;
import com.dcfs.smartaibank.manager.monitor.web.service.AlarmDeviceService;
import com.dcfs.smartaibank.manager.monitor.web.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author tanchen1
 * @author wangtingo
 * @date 2019/6/20 10:58
 * @since
 */
@Service
public class AlarmDeviceServiceImpl implements AlarmDeviceService {

    @Autowired
    private AlarmQueryDao alarmQueryDao;

    @Override
    public PageInfo<AlarmInfo> getAlarmInfo(String queryType, AlarmParams alarmParams, String branchNo) {
        try {
            PageHelper.startPage(alarmParams.getPageNum(), alarmParams.getPageSize());
            List<AlarmInfo> alarmInfo = new ArrayList<>();
            if (Constance.ALARMQUERYSUM.equals(queryType)) {
                alarmParams.setBranchNo(branchNo);
                alarmInfo = alarmQueryDao.queryDevAlarmInfo(alarmParams);
            } else if (Constance.ALARMQUERYUSER.equals(queryType)) {
                String[] time = DateUtil.getLocalDateTimeOfThree();
                alarmParams.setStartDateTime(time[1]);
                alarmParams.setEndDateTime(time[0]);
                alarmInfo = alarmQueryDao.queryDevAlarmInfoByUser(alarmParams);
            }
            return new PageInfo<>(alarmInfo);
        } catch (Exception e) {
            throw new BusinessException("data.access", e);
        }
    }

    @Override
    public PageInfo<AlarmInfo>
            getAlarmInfobyLevel(String queryType, AlarmParams alarmParams, String branchNo, String index) {
        try {

            PageHelper.startPage(alarmParams.getPageNum(), alarmParams.getPageSize());
            List<AlarmInfo> alarmInfo = new ArrayList<>();
            if (Constance.ALARMQUERYSUM.equals(queryType)) {
                alarmParams.setBranchNo(branchNo);


                if (index.equals(Constance.RED_COLOR)) {
                    alarmInfo =
                            alarmQueryDao.queryDevAlarmInfobyLevel(alarmParams.getBranchNo(), AlarmLevel.RED.getCode());
                } else if (index.equals(Constance.YELLOW_COLOR)) {
                    alarmInfo =
                    alarmQueryDao.queryDevAlarmInfobyLevel(alarmParams.getBranchNo(), AlarmLevel.ORANGE.getCode());
                } else if (index.equals(Constance.BLUE_COLOR)) {
                    alarmInfo =
                            alarmQueryDao.
                                    queryDevAlarmInfobyLevel(alarmParams.getBranchNo(), AlarmLevel.YELLOW.getCode());
                }
            } else if (Constance.ALARMQUERYUSER.equals(queryType)) {
                String[] time = DateUtil.getLocalDateTimeOfThree();
                alarmParams.setStartDateTime(time[1]);
                alarmParams.setEndDateTime(time[0]);
                if (index.equals(Constance.RED_COLOR)) {
                    alarmInfo =
                            alarmQueryDao.
                                    queryDevAlarmInfoByUserbyLevel(alarmParams.getBranchNo(), AlarmLevel.RED.getCode());
                } else if (index.equals(Constance.YELLOW_COLOR)) {
                    alarmInfo =
                            alarmQueryDao.
                                    queryDevAlarmInfoByUserbyLevel(alarmParams.getBranchNo(),
                                            AlarmLevel.ORANGE.getCode());
                } else if (index.equals(Constance.BLUE_COLOR)) {
                    alarmInfo =
                            alarmQueryDao.
                                    queryDevAlarmInfoByUserbyLevel(alarmParams.getBranchNo(),
                                            AlarmLevel.YELLOW.getCode());
                }
            }
            return new PageInfo<>(alarmInfo);
        } catch (Exception e) {
            throw new BusinessException("data.access", e);
        }

    }

    @Override
    public AlarmSimpleResult getSimpleAlarmInfo(String branchNo) {
        try {
            AlarmSimpleResult as = new AlarmSimpleResult();
            List<AlarmInfo> alarmInfo = alarmQueryDao.querySimpleAlarmInfo(branchNo);
            as.setAlarmInfo(alarmInfo);
            as.setAlarmOne(alarmQueryDao.queryAlarmNum(branchNo, AlarmLevel.YELLOW.getCode()));
            as.setAlarmTwo(alarmQueryDao.queryAlarmNum(branchNo, AlarmLevel.ORANGE.getCode()));
            as.setAlarmThree(alarmQueryDao.queryAlarmNum(branchNo, AlarmLevel.RED.getCode()));
            return as;
        } catch (Exception e) {
            throw new BusinessException("data.access", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void beginDeal(String userId, String alarmId) {
        try {
            alarmQueryDao.updateAlarmInfoStatus(alarmId, AlarmDealStatus.DEAL.getCode());
            AlarmDealInfo alarmDealInfo = new AlarmDealInfo();
            alarmDealInfo.setAlarmId(alarmId);
            alarmDealInfo.setBeginTime(new Date());
            alarmDealInfo.setUserId(userId);
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
            StringBuilder str = new StringBuilder(alarmId);
            str.append(sf.format(new Date()));
            alarmDealInfo.setSeqId(str.toString());
            alarmQueryDao.insertAlarmBeginDeal(alarmDealInfo);
        } catch (Exception e) {
            throw new BusinessException("data.access", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handUp(String alarmId) {
        try {
            alarmQueryDao.updateHandUpStatus(alarmId, AlarmDealStatus.SUBMIT.getCode());
            alarmQueryDao.updateDealHandUp(alarmId, new Date());
        } catch (Exception e) {
            throw new BusinessException("data.access", e);
        }
    }

    @Override
    public Integer getUnDealInfo(String branchNo) {
        return alarmQueryDao.queryAlarmCount(branchNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertRepair(AlarmRepairInfo alarmRepairInfo) {
        //自动生成报修id
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String repairId = alarmRepairInfo.getAlarmId() + format.format(new Date());
        alarmRepairInfo.setRepairId(repairId);
        //新增预警维修记录
        alarmQueryDao.insertRepairInfo(alarmRepairInfo);

        //修改预警处理记录表里面的报修时间
        alarmQueryDao.updateAlarmRepairInfo(alarmRepairInfo.getAlarmId(), new Date());

        String status = AlarmDealStatus.REPAIRS.getCode();
        //修改预警状态
        alarmQueryDao.updateAlarmStatus(alarmRepairInfo.getAlarmId(), status);


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRepair(AlarmWorkInfo alarmWorkInfo) {
        alarmWorkInfo.setEndTime(new Date());
        //修改预警中报修登记信息
        alarmQueryDao.updateAlarmRepairRecord(alarmWorkInfo);

        alarmWorkInfo.setCloseTime(new Date());
        alarmWorkInfo.setCloseType(AlarmCloseType.REGISTRATION.getCode());
        //修改预警处理记录表里面的关闭信息
        alarmQueryDao.updateAlarmCloseStatus(alarmWorkInfo);

        String status = AlarmDealStatus.CLOSED.getCode();
        //修改预警状态
        alarmQueryDao.updateAlarmStatus(alarmWorkInfo.getAlarmId(), status);

    }

    @Override
    public AlarmMaintainer getMaintainer(String alarmId) {
        return alarmQueryDao.getMaintainer(alarmId);
    }

    @Override
    public AlarmWorkInfo getAlarmInfo(String id) {
        return alarmQueryDao.getAlarmInfo(id);
    }



}
