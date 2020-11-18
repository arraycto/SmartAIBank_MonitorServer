package com.dcfs.smartaibank.manager.monitor.web.service;


import com.dcfs.smartaibank.manager.monitor.web.domian.NegativeCustomerInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualityBranchSum;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualityBranchUser;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualityListInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualityNetWorkInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualityRate;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualitySumBranchInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualitySumLevelInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author tanchen1
 * @date 2019/7/3 10:46
 * @since
 */
public interface MonitorQualityService {

    /**
     * 获取监控服务质量统计以及评价率
     *
     * @param branchNo  机构编号
     * @param isManager 是否为管理机构
     * @return 统计结果对象
     */
    QualityRate queryQualityRate(String branchNo, boolean isManager);


    /**
     * 服务质量分页列表
     *
     * @param branchNo    机构编号
     * @param pageNum     当前页
     * @param pageSize    每页的大小
     * @param queryType   查询类型
     * @param orderColumn 排序规则，比如: order by id desc 或者 order by id asc 前端直接传入
     * @return 服务质量列表集合
     */
    PageInfo<QualityListInfo> qualityList(String branchNo,
                                          Integer pageNum,
                                          Integer pageSize,
                                          String orderColumn,
                                          Integer queryType);


    /**
     * 查询前二十列表查询
     *
     * @param branchNo  机构编号
     * @param queryType 查询类型
     * @return
     */
    QualityNetWorkInfo getQualityListTopInfo(String branchNo, String queryType);

    /**
     * 查询网点服务质量汇总信息
     *
     * @param branchNo 机构编号
     * @return QualityBranchSum
     */
    QualityBranchSum getBranchQualitySum(String branchNo);


    /**
     * 查询网点柜员服务质量
     *
     * @param branchNo  机构编号
     * @param queryType 查询类型
     * @return QualityBranchSum
     */
    List<QualityBranchUser> getBranchQualityUser(String branchNo, String queryType);

    /**
     * 网点服务质量  柜员差评的客户信息
     *
     * @param userId 柜员ID
     * @return 差评客户实体 集合
     */
    List<NegativeCustomerInfo> getNegativeCustomerInfo(String userId);

    /**
     * 查询首页 按整体维度的面板信息（评价，好评，差评数 及占比）
     *
     * @param branchNo  机构Id
     * @param isManager 是否为管理机构（包括营业兼管理）
     * @return 首页整体信息实体
     */
    QualitySumBranchInfo getSumQualityInfo(String branchNo, boolean isManager);

    /**
     * 查询首页 按网点分布维度的面板信息（优良中差及占比）
     *
     * @param branchNo  机构Id
     * @param isManager 是否为管理机构（包括营业兼管理）
     * @return 按网点维度评价占比信息实体
     */
    QualitySumLevelInfo getQualityLevelInfo(String branchNo, boolean isManager);

}
