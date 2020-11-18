package com.dcfs.smartaibank.manager.monitor.web.dao;

import com.dcfs.smartaibank.manager.monitor.web.domian.TimePhasedTransCount;
import com.dcfs.smartaibank.manager.monitor.web.domian.TranCount;
import com.dcfs.smartaibank.manager.monitor.web.domian.TranStatusSum;
import com.dcfs.smartaibank.manager.monitor.web.domian.TransDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 监控交易处理访问层接口
 *
 * @author wangjzm
 * @data 2019/07/01 11:12
 * @since 1.0.0
 */
@Mapper
public interface MonitorTransDao {
    /**
     * 查询某一天的交易处理汇总信息,包括正常、异常、失败交易笔数及占比
     * 1.branchStatsType为1代表营业型机构，sql查询的是当前传入机构id的汇总信息
     * 2.branchStatsType为2代表管理型机构，sql查询的是当前传入机构id的下属营业性机构（包含自身）的汇总信息
     * STATUS：1-失败，2-异常，3-成功
     *
     * @param branchId        机构id
     * @param branchStatsType 机构数据统计类型 (1-作为营业机构进行统计,2-作为上级机构进行汇总)
     * @param tranDate        交易日期
     * @return 交易处理各状态数量及占比实体类
     */
    TranStatusSum selectEachStatusCountGatherInfo(@Param("branchId") String branchId,
                                                  @Param("branchStatsType") Integer branchStatsType,
                                                  @Param("tranDate") String tranDate);

    /**
     * 查询ATM、STM、BSR交易笔数以及总笔数
     * 1.branchStatsType为1代表营业型机构，sql查询的是当前传入机构id的汇总信息
     * 2.branchStatsType为2代表管理型机构，sql查询的是当前传入机构id的下属营业性机构（包含自身）的汇总信息
     *
     * @param branchId        机构id
     * @param branchStatsType 机构数据统计类型 (1-作为营业机构进行统计,2-作为上级机构进行汇总)
     * @param tranDate        交易日期
     * @return 交易处理分设备类型交易笔数实体类
     */
    TranCount selectTransCount(@Param("branchId") String branchId,
                               @Param("branchStatsType") Integer branchStatsType,
                               @Param("tranDate") String tranDate);

    /**
     * 查询监控交易处理详情列表
     *
     * @param branchId        机构号
     * @param tranDate        交易日期
     * @param branchStatsType 机构数据统计类型 : 1-作为营业机构进行统计,2-作为上级机构进行汇总
     * @param queryNumber     查询记录数
     * @return 监控交易处理列表详情集合
     */
    List<TransDetail> selectTransDetail(@Param("branchId") String branchId,
                                        @Param("tranDate") String tranDate,
                                        @Param("branchStatsType") Integer branchStatsType,
                                        @Param("queryNumber") Integer queryNumber);

    /**
     * 查询监控交易处理分时段交易数量
     *
     * @param branchId   机构号
     * @param interval   交易折线图统计区间时间间隔
     * @param showLength 交易折线图展示长度
     * @param endDate    结束时间点(格式 yyyy-MM-dd HH24:MI:SS)
     * @return 分时段交易数量集合
     */
    List<TimePhasedTransCount> selectTimePhasedTransCount(@Param("branchId") String branchId,
                                                          @Param("interval") Integer interval,
                                                          @Param("showLength") Integer showLength,
                                                          @Param("endDate") String endDate);
}
