package com.dcfs.smartaibank.manager.monitor.web.service;

import com.dcfs.smartaibank.manager.monitor.web.domian.BusinessTypeDetailGather;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusyAllCustomerTypeGatherInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusyDetails;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusyWaitTimeDistribution;
import com.dcfs.smartaibank.manager.monitor.web.domian.OrgBusinessHours;
import com.dcfs.smartaibank.manager.monitor.web.domian.TimePhasedBusinessInfo;
import com.dcfs.smartaibank.manager.monitor.web.enums.BusyDetailsSortRuleEnum;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusyGatherInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusyOrgRankInfo;
import com.dcfs.smartaibank.manager.monitor.web.enums.BusyRankQueryConditionEnum;
import com.dcfs.smartaibank.manager.monitor.web.enums.BusyStatusEnum;
import com.dcfs.smartaibank.manager.monitor.web.enums.SortOrderEnum;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 繁忙度业务层接口
 *
 * @author wangjzm
 * @data 2019/07/03 15:58
 * @since 1.0.0
 */
public interface MonitorBusyService {
    /**
     * 查询繁忙度汇总信息
     *
     * @param branchId        机构id
     * @param branchStatsType 营业兼管理机构类型时，营业=1，管理=2
     * @return 繁忙度信息汇总实体类
     */
    BusyGatherInfo selectBusyGatherInfo(String branchId, Integer branchStatsType);

    /**
     * 分页查询繁忙度详情列表信息
     *
     * @param pageNum    页号
     * @param pageSize   页大小
     * @param branchId   机构id
     * @param busyStatus 繁忙度状态
     * @param sortRule   排序规则
     * @param sortOrder  排序顺序
     * @return 设备列表页面设备详情集合
     */
    PageInfo<BusyDetails> selectBusyDetailsByBranchId(Integer pageNum,
                                                      Integer pageSize,
                                                      String branchId,
                                                      BusyStatusEnum busyStatus,
                                                      BusyDetailsSortRuleEnum sortRule,
                                                      SortOrderEnum sortOrder);

    /**
     * 获取机构繁忙度排名信息
     * a)网点数量≤10时显示Top10
     * b)10＜网点数量＜20时显示Top10和Bottom（总数-10）
     * c)网点数量≥20时显示Top10和Bottom10
     *
     * @param branchId    机构id
     * @param queryParams 查询条件：1-等待客户数，2-客户平均等待时间
     * @return 繁忙度机构排名信息（TOP、BOTTOM信息汇总）
     */
    BusyOrgRankInfo getBusynessRankInfo(String branchId,
                                        BusyRankQueryConditionEnum queryParams);

    /**
     * 查询营业机构繁忙度排队等待时长（10分钟以下，10-20分钟，20-30分钟，30分钟以上）
     *
     * @param branchId 机构id
     * @return 繁忙度排队等待时长分布比例（10分钟以下，10-20分钟，20-30分钟，30分钟以上）
     */
    BusyWaitTimeDistribution getBusynessWaitingTimeDistribution(String branchId);

    /**
     * 查询营业机构繁忙度分业务类型繁忙度详情（等待客户数，客户平均等待时间，可办理窗口）
     * 包含个人业务和公司业务
     *
     * @param branchId 机构id
     * @return 分业务类型繁忙度详情汇总信息（个人业务，公司业务）
     */
    BusinessTypeDetailGather getBusyBusinessTypeDistribution(String branchId);

    /**
     * 查询机构当天的营业时间(机构如果没有设置营业时间，则查询总行的营业时间)
     *
     * @param branchId 机构id
     * @return 机构营业时间
     */
    OrgBusinessHours getOrgBusinessHours(String branchId);


    /**
     * 查询监控分时段繁忙度信息（抽号数量、叫号数量）
     *
     * @param branchId        机构id
     * @param branchStatsType 营业兼管理机构类型时，营业=1，管理=2
     * @param busyStartTime   营业开始时间,格式：HH24:MI
     * @param busyEndTime     营业结束时间,格式：HH24:MI
     * @return 分时段繁忙度信息（抽号数量、叫号数量）
     */
    List<TimePhasedBusinessInfo> getPhasedBusyInfoBeforeCurrentTime(String branchId,
                                                                    Integer branchStatsType,
                                                                    String busyStartTime,
                                                                    String busyEndTime);

    /**
     * 分客户类型繁忙度详情
     *
     * @param branchId        机构id
     * @param branchStatsType 营业兼管理机构类型时，营业=1，管理=2
     * @param busyStartTime   营业开始时间,格式：HH24:MI
     * @param busyEndTime     营业结束时间,格式：HH24:MI
     * @return 分客户类型繁忙度详情
     */
    BusyAllCustomerTypeGatherInfo getPhasedCustomerTypeBusyInfo(String branchId,
                                                                Integer branchStatsType,
                                                                String busyStartTime,
                                                                String busyEndTime);
}
