package com.dcfs.smartaibank.manager.monitor.web.service;

import com.dcfs.smartaibank.manager.monitor.web.domian.TimePhasedTransCount;
import com.dcfs.smartaibank.manager.monitor.web.domian.TranCount;
import com.dcfs.smartaibank.manager.monitor.web.domian.TranStatusSum;
import com.dcfs.smartaibank.manager.monitor.web.domian.TransDetail;

import java.util.List;

/**
 * 监控交易处理业务层接口
 *
 * @author wangjzm
 * @data 2019/07/01 14:23
 * @since 1.0.0
 */
public interface MonitorTransService {
    /**
     * 查询当天交易处理汇总信息,包括正常、异常、失败交易笔数及占比
     * 1.branchStatsType为1代表营业型机构，sql查询的是当前传入机构id的汇总信息
     * 2.branchStatsType为2代表管理型机构，sql查询的是当前传入机构id的下属营业性机构（包含自身）的汇总信息
     *
     * @param branchId        机构id
     * @param branchStatsType 机构数据统计类型 (1-作为营业机构进行统计,2-作为上级机构进行汇总)
     * @return 交易处理各状态数量及占比实体类
     */
    TranStatusSum selectEachStatusCountGatherInfo(String branchId,
                                                  Integer branchStatsType);

    /**
     * 查询当天ATM、STM、BSR交易笔数以及总笔数
     * 1.branchStatsType为1代表营业型机构，sql查询的是当前传入机构id的汇总信息
     * 2.branchStatsType为2代表管理型机构，sql查询的是当前传入机构id的下属营业性机构（包含自身）的汇总信息
     *
     * @param branchId        机构id
     * @param branchStatsType 机构数据统计类型 (1-作为营业机构进行统计,2-作为上级机构进行汇总)
     * @return 交易处理分设备类型交易笔数实体类
     */
    TranCount selectTransCount(String branchId,
                               Integer branchStatsType);

    /**
     * 查询当天监控交易处理前20条记录，按照时间排序
     *
     * @param branchId        机构号
     * @param branchStatsType 机构数据统计类型 : 1-作为营业机构进行统计,2-作为上级机构进行汇总
     * @return 监控交易处理列表详情集合
     */
    List<TransDetail> selectTransDetail(String branchId,
                                        Integer branchStatsType);

    /**
     * 查询监控交易处理分时段交易数量
     *
     * @param branchId    机构号
     * @param currentTime 当前时间,格式：HH24:MI:SS
     * @return 分时段交易数量集合
     */
    List<TimePhasedTransCount> selectTimePhasedTransCount(String branchId, String currentTime);

}
