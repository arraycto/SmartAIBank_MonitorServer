package com.dcfs.smartaibank.manager.monitor.web.dao;

import com.dcfs.smartaibank.manager.monitor.web.domian.BusinessTypeDetail;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusyCustGatherInfoInCustType;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusyDetails;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusyGatherInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusyWaitTimeDistribution;
import com.dcfs.smartaibank.manager.monitor.web.domian.CustomerTypeAndClass;
import com.dcfs.smartaibank.manager.monitor.web.domian.OrgBusinessHours;
import com.dcfs.smartaibank.manager.monitor.web.domian.TimePhasedBusinessInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.ManageableWindow;
import com.dcfs.smartaibank.manager.monitor.web.enums.BusyDetailsSortRuleEnum;
import com.dcfs.smartaibank.manager.monitor.web.enums.BusyRankQueryConditionEnum;
import com.dcfs.smartaibank.manager.monitor.web.enums.BusyStatusEnum;
import com.dcfs.smartaibank.manager.monitor.web.enums.SortOrderEnum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 监控繁忙度访问层接口
 *
 * @author wangjzm
 * @data 2019/07/03 10:27
 * @since 1.0.0
 */
public interface MonitorBusyDao {
    /**
     * 查询繁忙度汇总信息
     *
     * @param branchId        机构id
     * @param branchStatsType 营业兼管理机构类型时，营业=1，管理=2
     * @return 繁忙度信息汇总实体类
     */
    BusyGatherInfo selectBusyGatherInfo(@Param("branchId") String branchId,
                                        @Param("branchStatsType") Integer branchStatsType);

    /**
     * 查询繁忙度详情列表信息
     *
     * @param branchId   机构id
     * @param busyStatus 繁忙度状态：所有，未营业，空闲，正常，忙碌，饱和
     * @param sortRule   排序规则：繁忙度状态、等待客户数排序、窗口平均等待客户数排序、
     *                   客户平均等待时间排序、客户平均等待时间排序
     * @param sortOrder  排序顺序：升降序
     * @return 设备列表页面设备详情集合
     */
    List<BusyDetails> selectBusyDetailsByBranchId(@Param("branchId") String branchId,
                                                  @Param("busyStatus") BusyStatusEnum busyStatus,
                                                  @Param("sortRule") BusyDetailsSortRuleEnum sortRule,
                                                  @Param("sortOrder") SortOrderEnum sortOrder);

    /**
     * 根据机构号查询下属繁忙度机构数量（不包含未开门即状态为0的机构）
     *
     * @param branchId 机构id
     * @return 下属繁忙度机构数量
     */
    int selectBusyOrgCount(@Param("branchId") String branchId);

    /**
     * 查询繁忙度排名信息（排序规则：等待客户数、客户平均等待时间；排序顺序：升序、降序）
     *
     * @param branchId     机构id
     * @param sortRule     排序规则：等待客户数、客户平均等待时间
     * @param sortOrder    排序顺序：升降序
     * @param queryNumbers 查询记录数
     * @return 按照指定排序规则和排序顺序查询指定记录数的繁忙度排名信息
     */
    List<BusyDetails> selectBusynessRankList(@Param("branchId") String branchId,
                                             @Param("sortRule") BusyRankQueryConditionEnum sortRule,
                                             @Param("sortOrder") SortOrderEnum sortOrder,
                                             @Param("queryNumbers") Integer queryNumbers);

    /**
     * 查询营业机构繁忙度排队等待时长（10分钟以下，10-20分钟，20-30分钟，30分钟以上）
     *
     * @param branchId     机构id
     * @param customerInfo 客户类型、客户级别实体类
     * @return 繁忙度排队等待时长分布比例（10分钟以下，10-20分钟，20-30分钟，30分钟以上）
     */
    BusyWaitTimeDistribution selectBusynessWaitingTimeDistribution(
            @Param("branchId") String branchId,
            @Param("customerInfo") CustomerTypeAndClass customerInfo);


    /**
     * 查询营业机构繁忙度分业务类型繁忙度详情（等待客户数，客户平均等待时间，可办理窗口）
     *
     * @param branchId 机构id
     * @return 业务类型详情实体集合（等待客户数，客户平均等待时间，可办理窗口）
     */
    List<BusinessTypeDetail> selectBusyBusinessTypeDistribution(@Param("branchId") String branchId);

    /**
     * 查询机构当天的营业时间(机构如果没有设置营业时间，则查询总行的营业时间)
     *
     * @param branchId     机构id
     * @param currentYear  当前年（如：2019）
     * @param currentMonth 当前月（格式：两位，如：01）
     * @param currentDay   当前月下的当前日（格式：两位，如：01）
     * @return 机构营业时间
     */
    OrgBusinessHours selectOrgBusinessHoursByBranchId(@Param("branchId") String branchId,
                                                      @Param("currentYear") String currentYear,
                                                      @Param("currentMonth") String currentMonth,
                                                      @Param("currentDay") String currentDay);

    /**
     * 查询监控分时段繁忙度信息（等待客户数、客户平均等待时间）
     *
     * @param branchId        机构id
     * @param branchStatsType 营业兼管理机构类型时，营业=1，管理=2
     * @param interval        繁忙度折线图统计区间时间间隔
     * @param showLength      繁忙度折线图展示长度
     * @param endDateTime     结束时间点(格式 yyyy-MM-dd HH24:MI:SS)
     * @param customerInfo    客户类型、客户级别实体类
     * @return 分时段繁忙度信息（等待客户数、客户平均等待时间）
     */
    List<TimePhasedBusinessInfo> selectPhasedBusyInfoBeforeCurrentTime(
            @Param("branchId") String branchId,
            @Param("branchStatsType") Integer branchStatsType,
            @Param("interval") Integer interval,
            @Param("showLength") Integer showLength,
            @Param("endDateTime") String endDateTime,
            @Param("customerInfo") CustomerTypeAndClass customerInfo);

    /**
     * 查询当前时间段内的繁忙度信息
     *
     * @param branchId        机构id
     * @param branchStatsType 营业兼管理机构类型时，营业=1，管理=2
     * @param beginDateTime   开始时间(格式 yyyy-MM-dd HH24:MI:SS)
     * @param endDateTime     结束时间(格式 yyyy-MM-dd HH24:MI:SS)
     * @param customerInfo    客户类型、客户级别实体类
     * @return 当前时段繁忙度信息（等待客户数、客户平均等待时间）
     */
    TimePhasedBusinessInfo selectCurrentTimePhasedBusyInfo(@Param("branchId") String branchId,
                                                           @Param("branchStatsType") Integer branchStatsType,
                                                           @Param("beginDateTime") String beginDateTime,
                                                           @Param("endDateTime") String endDateTime,
                                                           @Param("customerInfo") CustomerTypeAndClass customerInfo);

    /**
     * 查询支行繁忙度按客户类型统计的等待客户数、客户平均等待时间
     *
     * @param branchId     机构id
     * @param customerInfo 客户类型、客户级别实体类
     * @return 客户类型看板分客户类型级别的汇总信息（包含等待客户数、等待客户数占比、客户平均等待时长）
     */
    BusyCustGatherInfoInCustType selectBusyWaitCustomersAndTimeByCustType(
            @Param("branchId") String branchId,
            @Param("customerInfo") CustomerTypeAndClass customerInfo);

    /**
     * 查询支行按客户类型统计的可办理窗口信息
     *
     * @param branchId 机构id
     * @return 支行按客户类型统计的可办理窗口信息
     */
    List<ManageableWindow> selectTransactableWindows(@Param("branchId") String branchId);
}
