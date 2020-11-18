package com.dcfs.smartaibank.manager.monitor.web.service.impl;

import com.dcfs.smartaibank.core.exception.BusinessException;
import com.dcfs.smartaibank.manager.monitor.web.dao.MonitorBusyDao;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusinessTypeDetail;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusinessTypeDetailGather;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusyAllCustomerTypeGatherInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusyCustGatherInfoInCustType;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusyCustomerTypeGatherInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusyDetails;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusyWaitTimeDistribution;
import com.dcfs.smartaibank.manager.monitor.web.domian.CustomerTypeAndClass;
import com.dcfs.smartaibank.manager.monitor.web.domian.OrgBusinessHours;
import com.dcfs.smartaibank.manager.monitor.web.domian.TimePhasedBusinessInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.ManageableWindow;
import com.dcfs.smartaibank.manager.monitor.web.enums.BusyDetailsSortRuleEnum;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusyGatherInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusyOrgRankInfo;
import com.dcfs.smartaibank.manager.monitor.web.enums.BusyRankQueryConditionEnum;
import com.dcfs.smartaibank.manager.monitor.web.enums.BusyStatusEnum;
import com.dcfs.smartaibank.manager.monitor.web.enums.CustomerClassEnum;
import com.dcfs.smartaibank.manager.monitor.web.enums.CustomerTypeEnum;
import com.dcfs.smartaibank.manager.monitor.web.enums.SortOrderEnum;
import com.dcfs.smartaibank.manager.monitor.web.service.MonitorBusyService;
import com.dcfs.smartaibank.manager.monitor.web.util.DateUtil;
import com.dcfs.smartaibank.manager.monitor.web.util.RateCalculateUtil;
import com.dcfs.smartaibank.manager.monitor.web.util.StreamUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 繁忙度业务层实现类
 *
 * @author wangjzm
 * @data 2019/07/03 16:00
 * @since 1.0.0
 */
@Service
@Slf4j
public class MonitorBusyServiceImpl implements MonitorBusyService {

    @Autowired
    private MonitorBusyDao monitorBusyDao;

    /**
     * 个人业务类型-A
     */
    private static final String PERSONAL_BUSINESS_TYPE = "A";
    /**
     * 公司业务类型-B
     */
    private static final String COMPANY_BUSINESS_TYPE = "B";

    /**
     * 01  VIP客户
     */
    private static final String VIP = "01";

    /**
     * 02  普通客户
     */
    private static final String CUSTOM = "02";

    /**
     * 默认排名top信息查询数量10
     */
    private static final Integer DEFAULT_RANK_TOP_QUERY_NUMBERS = 10;

    /**
     * 默认排名信息查询数量20
     */
    private static final Integer DEFAULT_RANK_ALL_QUERY_NUMBERS = 20;

    /**
     * 默认繁忙度折线图统计区间时间间隔: 60分钟
     */
    private static final int DEFAULT_INTERVAL = 60;

    /**
     * 30分钟
     */
    private static final int THIRTY_MINUTE = 30;

    @Override
    public BusyGatherInfo selectBusyGatherInfo(String branchId, Integer branchStatsType) {
        return monitorBusyDao.selectBusyGatherInfo(branchId, branchStatsType);
    }

    @Override
    public PageInfo<BusyDetails> selectBusyDetailsByBranchId(Integer pageNum,
                                                             Integer pageSize,
                                                             String branchId,
                                                             BusyStatusEnum busyStatus,
                                                             BusyDetailsSortRuleEnum sortRule,
                                                             SortOrderEnum sortOrder) {
        PageHelper.startPage(pageNum, pageSize);
        List<BusyDetails> list =
                monitorBusyDao.selectBusyDetailsByBranchId(branchId, busyStatus, sortRule, sortOrder);
        return new PageInfo<>(list);
    }

    @Override
    public BusyOrgRankInfo getBusynessRankInfo(String branchId,
                                               BusyRankQueryConditionEnum queryParams) {
        /**
         * a)网点数量≤10时显示Top10；
         * b)10＜网点数量＜20时显示Top10和Bottom（总数-10）；
         * c)网点数量≥20时显示Top10和Bottom10；
         */
        List<BusyDetails> topList = new ArrayList<>();
        List<BusyDetails> bottomList = new ArrayList<>();
        int subOrgCount = 0;
        try {
            // 1.查询下属机构数量（包含自身）
            subOrgCount = monitorBusyDao.selectBusyOrgCount(branchId);
            if (subOrgCount > 0) {
                // 2.如果查询机构数量小于默认值10，只返回Top10
                topList = monitorBusyDao.selectBusynessRankList(branchId, queryParams,
                        SortOrderEnum.ASC, DEFAULT_RANK_TOP_QUERY_NUMBERS);
                // 设置top排名信息
                for (int i = 0; i < topList.size(); i++) {
                    topList.get(i).setRankNo(i + 1);
                }
                // 3.如果查询:10＜网点数量＜20时显示Top10和Bottom（总数-10）
                if (subOrgCount > DEFAULT_RANK_TOP_QUERY_NUMBERS && subOrgCount <= DEFAULT_RANK_ALL_QUERY_NUMBERS) {
                    bottomList = monitorBusyDao.selectBusynessRankList(branchId, queryParams,
                            SortOrderEnum.DESC, subOrgCount - DEFAULT_RANK_TOP_QUERY_NUMBERS);
                } else if (subOrgCount > DEFAULT_RANK_ALL_QUERY_NUMBERS) {
                    // 4.网点数量≥20时显示Top10和Bottom10；
                    bottomList = monitorBusyDao.selectBusynessRankList(branchId, queryParams,
                            SortOrderEnum.DESC, DEFAULT_RANK_TOP_QUERY_NUMBERS);
                }
                // 设置bottom排名信息
                for (int i = 0; i < bottomList.size(); i++) {
                    bottomList.get(i).setRankNo(subOrgCount - i);
                }
            }
        } catch (Exception e) {
            throw new BusinessException("data.access", e);
        }
        return BusyOrgRankInfo.builder().topList(topList).bottomList(bottomList).build();
    }

    @Override
    public BusyWaitTimeDistribution getBusynessWaitingTimeDistribution(String branchId) {
        BusyWaitTimeDistribution busyWaitTimeDistribution =
                monitorBusyDao.selectBusynessWaitingTimeDistribution(branchId, CustomerTypeAndClass.builder().build());
        if (busyWaitTimeDistribution.getTotalCount() != 0) {
            busyWaitTimeDistribution.setMoreThanThirtyMinRate(100
                    - busyWaitTimeDistribution.getLessThanTenMinRate()
                    - busyWaitTimeDistribution.getBetweenTenAndTwentyMinRate()
                    - busyWaitTimeDistribution.getBetweenTwentyAndThirtyMinRate());
        }
        return busyWaitTimeDistribution;
    }

    @Override
    public BusinessTypeDetailGather getBusyBusinessTypeDistribution(String branchId) {
        List<BusinessTypeDetail> detailList = null;
        // 1.查询所有的业务类型详情
        try {
            detailList = monitorBusyDao.selectBusyBusinessTypeDistribution(branchId);
        } catch (Exception e) {
            throw new BusinessException("data.access", e);
        }
        // 2.筛选个人业务详情信息
        List<BusinessTypeDetail> personalBusinessList =
                detailList.parallelStream().filter(businessTypeDetail ->
                        PERSONAL_BUSINESS_TYPE.equals(businessTypeDetail.getParentBusinessTypeId())
                ).collect(Collectors.toList());
        // 2.筛选公司业务详情信息
        List<BusinessTypeDetail> companyBusinessList =
                detailList.parallelStream().filter(businessTypeDetail ->
                        COMPANY_BUSINESS_TYPE.equals(businessTypeDetail.getParentBusinessTypeId())
                ).collect(Collectors.toList());

        return BusinessTypeDetailGather.builder()
                .personalBusinessList(personalBusinessList)
                .companyBusinessList(companyBusinessList)
                .build();
    }

    @Override
    public OrgBusinessHours getOrgBusinessHours(String branchId) {
        // 1.获取当前时间转化为字符串，格式yyyy-MM-dd
        String now = LocalDate.now().toString();
        // 2.单独取出年月日
        String[] split = now.split("-");
        String year = split[0];
        String month = split[1];
        String day = split[2];
        return monitorBusyDao.selectOrgBusinessHoursByBranchId(branchId, year, month, day);
    }

    @Override
    public List<TimePhasedBusinessInfo> getPhasedBusyInfoBeforeCurrentTime(String branchId,
                                                                           Integer branchStatsType,
                                                                           String busyStartTime,
                                                                           String busyEndTime) {
        return this.commonBusyInfoBeforeCurrentTime(branchId,
                branchStatsType, busyStartTime, busyEndTime, null, null);
    }


    @Override
    public BusyAllCustomerTypeGatherInfo getPhasedCustomerTypeBusyInfo(String branchId,
                                                                       Integer branchStatsType,
                                                                       String busyStartTime,
                                                                       String busyEndTime) {
        // 1.获取客户类型为对私普通的繁忙度信息
        BusyCustomerTypeGatherInfo personalCustomGatherInfo =
                this.getSingleBusyCustomerTypeGatherInfo(branchId, branchStatsType,
                        busyStartTime, busyEndTime, CustomerTypeEnum.CUSTOM, CustomerClassEnum.PERSONAL);

        // 2.获取客户类型为对私VIP的繁忙度信息
        BusyCustomerTypeGatherInfo personalVIPGatherInfo =
                this.getSingleBusyCustomerTypeGatherInfo(branchId, branchStatsType,
                        busyStartTime, busyEndTime, CustomerTypeEnum.VIP, CustomerClassEnum.PERSONAL);

        // 3.获取客户类型为对公的繁忙度信息
        BusyCustomerTypeGatherInfo companyGatherInfo =
                this.getSingleBusyCustomerTypeGatherInfo(branchId, branchStatsType,
                        busyStartTime, busyEndTime, null, CustomerClassEnum.COMPANY);

        // 4.计算各客户类型客户类别的当前等待客户数占比
        // 计算当前等待客户数总数
        int personalCustomWaitCount = personalCustomGatherInfo.getBusyCustGatherInfoInCustType().getWaitingCustomers();
        int personalVIPWaitCount = personalVIPGatherInfo.getBusyCustGatherInfoInCustType().getWaitingCustomers();
        int companyWaitCount = companyGatherInfo.getBusyCustGatherInfoInCustType().getWaitingCustomers();
        int totalWaitCount = personalCustomWaitCount + personalVIPWaitCount + companyWaitCount;
        BigDecimal personalCustomWaitRate = RateCalculateUtil.division(personalCustomWaitCount, totalWaitCount);
        BigDecimal personalVIPWaitRate = RateCalculateUtil.division(personalVIPWaitCount, totalWaitCount);
        personalCustomGatherInfo.getBusyCustGatherInfoInCustType()
                .setWaitingCustomersRate(personalCustomWaitRate.floatValue());
        personalVIPGatherInfo.getBusyCustGatherInfoInCustType()
                .setWaitingCustomersRate(personalVIPWaitRate.floatValue());
        float temp =
                new BigDecimal("100").subtract(personalCustomWaitRate).subtract(personalVIPWaitRate).floatValue();
        companyGatherInfo.getBusyCustGatherInfoInCustType().setWaitingCustomersRate(temp);

        // 5.获取各客户级别类别的可办理窗口信息
        List<ManageableWindow> manageableWindowList = monitorBusyDao.selectTransactableWindows(branchId);
        /**
         * 根据业务类型和客户类型进行分类统计,统计规则：
         * 1.业务类型-A，客户类型-02 ---> 个人普通客户
         * 2.业务类型-A，客户类型-01 ---> 个人VIP客户
         * 3.业务类型-B ---> 公司客户
         */

        List<ManageableWindow> personalCustomWindows = manageableWindowList.stream().filter(manageableWindow ->
                PERSONAL_BUSINESS_TYPE.equals(manageableWindow.getSuperQueueNo())
                        && CUSTOM.equals(manageableWindow.getCustomerType()))
                .collect(Collectors.toList());
        List<ManageableWindow> personalVIPWindows = manageableWindowList.stream().filter(manageableWindow ->
                PERSONAL_BUSINESS_TYPE.equals(manageableWindow.getSuperQueueNo())
                        && VIP.equals(manageableWindow.getCustomerType()))
                .collect(Collectors.toList());
        List<ManageableWindow> companyWindows = manageableWindowList.stream().filter(manageableWindow ->
                COMPANY_BUSINESS_TYPE.equals(manageableWindow.getSuperQueueNo()))
                .filter(StreamUtil.distinctByKey(t -> t.getWindowId()))
                .collect(Collectors.toList());
        personalCustomGatherInfo.setWindows(personalCustomWindows);
        personalVIPGatherInfo.setWindows(personalVIPWindows);
        companyGatherInfo.setWindows(companyWindows);

        return BusyAllCustomerTypeGatherInfo.builder()
                .personalCustomGatherInfo(personalCustomGatherInfo)
                .personalVIPGatherInfo(personalVIPGatherInfo)
                .companyGatherInfo(companyGatherInfo).build();
    }

    /**
     * 获取单个客户类别及客户类型的繁忙度
     *
     * @param branchId          机构id
     * @param branchStatsType   营业兼管理机构类型时，营业=1，管理=2
     * @param busyStartTime     营业开始时间(格式 yyyy-MM-dd HH24:MI:SS)
     * @param busyEndTime       营业结束时间(格式 yyyy-MM-dd HH24:MI:SS)
     * @param customerTypeEnum  客户类型（01-VIP,02-普通）
     * @param customerClassEnum 客户类别（0-公司客户,1-个人客户）
     * @return 当前客户类型的分时段繁忙情况、汇总信息、排队等待时长分布
     */
    private BusyCustomerTypeGatherInfo getSingleBusyCustomerTypeGatherInfo(String branchId,
                                                                           Integer branchStatsType,
                                                                           String busyStartTime,
                                                                           String busyEndTime,
                                                                           CustomerTypeEnum customerTypeEnum,
                                                                           CustomerClassEnum customerClassEnum) {
        // 1.获取分时段繁忙度信息（抽号数量、叫号数量）
        List<TimePhasedBusinessInfo> timePhasedBusinessInfo = this.commonBusyInfoBeforeCurrentTime(branchId,
                branchStatsType, busyStartTime, busyEndTime, customerTypeEnum, customerClassEnum);
        // 2.获取繁忙度排队等待时长分布比例（10分钟以下，10-20分钟，20-30分钟，30分钟以上）
        BusyWaitTimeDistribution busyWaitTimeDistribution =
                monitorBusyDao.selectBusynessWaitingTimeDistribution(branchId, CustomerTypeAndClass.builder().
                        customerClassEnum(customerClassEnum).customerTypeEnum(customerTypeEnum).build());
        // 3.获取客户类型看板分客户类型级别的汇总信息（包含等待客户数、等待客户数占比、客户平均等待时长）
        BusyCustGatherInfoInCustType busyCustGatherInfoInCustType =
                monitorBusyDao.selectBusyWaitCustomersAndTimeByCustType(branchId, CustomerTypeAndClass.builder().
                        customerClassEnum(customerClassEnum).customerTypeEnum(customerTypeEnum).build());
        BusyCustomerTypeGatherInfo busyCustomerTypeGatherInfo =
                BusyCustomerTypeGatherInfo.builder()
                        .busyCustGatherInfoInCustType(busyCustGatherInfoInCustType)
                        .busyWaitTimeDistribution(busyWaitTimeDistribution)
                        .timePhasedBusinessInfoList(timePhasedBusinessInfo)
                        .build();
        return busyCustomerTypeGatherInfo;
    }

    /**
     * 获取分时段（已过去的时间段+当前时间段）繁忙度信息（抽号数量、叫号数量）
     *
     * @param branchId          机构id
     * @param branchStatsType   营业兼管理机构类型时，营业=1，管理=2
     * @param busyStartTime     营业开始时间(格式 yyyy-MM-dd HH24:MI:SS)
     * @param busyEndTime       营业结束时间(格式 yyyy-MM-dd HH24:MI:SS)
     * @param customerTypeEnum  客户类型（01-VIP,02-普通）
     * @param customerClassEnum 客户类别（0-公司客户,1-个人客户）
     * @return 当前时段繁忙度信息（抽号数量、叫号数量）
     */
    private List<TimePhasedBusinessInfo> commonBusyInfoBeforeCurrentTime(String branchId,
                                                                         Integer branchStatsType,
                                                                         String busyStartTime,
                                                                         String busyEndTime,
                                                                         CustomerTypeEnum customerTypeEnum,
                                                                         CustomerClassEnum customerClassEnum) {
        List<TimePhasedBusinessInfo> list = new ArrayList<>();
        // 获取当前时间，获取当前时间的上一时间段
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalTime startTime, endTime, currentTime = LocalTime.now();
        try {
            startTime = LocalTime.parse(busyStartTime);
            endTime = LocalTime.parse(busyEndTime);
        } catch (Exception e) {
            throw new BusinessException("org.worktime.parse.error");
        }
        // 校验营业时间是否符合规范
        checkBusyTime(startTime, endTime);
        LocalDateTime endDateTime = LocalDateTime.of(LocalDate.now(), endTime);
        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.now(), startTime);
        // 最后的时间段结束值
        LocalDateTime terminalEndDateTime;
        int showLength;
        if (currentDateTime.isBefore(startDateTime)) {
            return list;
        }
        // 如果当前时间超过营业结束时间
        if (currentDateTime.isAfter(endDateTime)) {
            // 1. 如果营业结束时间和开始时间的分钟相同（营业时间只能是分钟以00或30结尾），那么最后的时间段结束值是营业结束时间
            // 2. 如果不相同，那么最后的时间段结束值是营业时间+30分钟
            terminalEndDateTime =
                    endTime.getMinute() == startTime.getMinute() ? endDateTime : endDateTime.plusMinutes(THIRTY_MINUTE);
        } else {
            //1. 如果营业开始时间的分钟数为0（营业时间只能是分钟以00或30结尾），
            //   那么最后的时间段结束值是距离当前时间最近的整点值+1，比如说当前10:05，那么结束值为11:00
            //2. 如果不为0,也就是分钟为30，那么最后的时间段结束值可能有两种情况：
            //     1) 当前时间的分钟数小于30分钟,那么结束值为当前小时值+30分钟
            //     2) 当前时间的分钟数大于等于30分钟,那么结束值为该时刻小时数+1再加上30分钟
            if (startTime.getMinute() == 0) {
                terminalEndDateTime = LocalDateTime.parse(
                        DateUtil.getEndTime(DEFAULT_INTERVAL,
                                currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).plusHours(1);
            } else {
                if (currentTime.getMinute() < THIRTY_MINUTE) {
                    terminalEndDateTime =
                            LocalDateTime.of(LocalDate.now(), LocalTime.of(currentTime.getHour(), THIRTY_MINUTE));
                } else {
                    terminalEndDateTime =
                            LocalDateTime.of(LocalDate.now(), LocalTime.of(currentTime.getHour() + 1, THIRTY_MINUTE));
                }
            }
        }
        try {
            showLength = terminalEndDateTime.getHour() - startTime.getHour();
            if (showLength > 0) {
                list = monitorBusyDao.selectPhasedBusyInfoBeforeCurrentTime(branchId, branchStatsType, DEFAULT_INTERVAL,
                        showLength, terminalEndDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        CustomerTypeAndClass.builder().
                                customerClassEnum(customerClassEnum).customerTypeEnum(customerTypeEnum).build());
            }
        } catch (Exception e) {
            throw new BusinessException("data.access", e);
        }
        return list;
    }

    /**
     * 检查营业开始时间和结束时间是否符合规范
     * 1.开始时间 < 结束时间
     * 2.开始时间和结束时间的分钟必须以00或30结尾
     *
     * @param startTime 营业开始时间
     * @param endTime   营业结束时间
     */
    private void checkBusyTime(LocalTime startTime, LocalTime endTime) {
        if (!startTime.isBefore(endTime)) {
            throw new BusinessException("org.workTime.startlessend.error");
        }
        if (startTime.getMinute() != 0 && startTime.getMinute() != THIRTY_MINUTE) {
            throw new BusinessException("org.startworktime.error");
        }
        if (endTime.getMinute() != 0 && endTime.getMinute() != THIRTY_MINUTE) {
            throw new BusinessException("org.endworktime.error");
        }
    }


}
