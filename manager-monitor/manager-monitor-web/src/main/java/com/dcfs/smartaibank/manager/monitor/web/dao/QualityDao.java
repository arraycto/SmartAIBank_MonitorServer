package com.dcfs.smartaibank.manager.monitor.web.dao;

import com.dcfs.smartaibank.manager.monitor.web.domian.NegativeCustomerInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualityBranchSum;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualityBranchUser;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualityListInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualityRate;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualitySumBranchInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualitySumLevelInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tanchen1
 * @date 2019/7/3 13:29
 * @since
 */
@Mapper
public interface QualityDao {

    /**
     * 查询服务质量 占比 数量
     *
     * @param branchNo 机构编号
     * @param isManager 是否为管理机构
     * @return   总的服务质量 占比 数量
     */
    QualityRate queryQualityRate(@Param("branchNo") String branchNo, @Param("isManager") boolean isManager);

    /**
     * 查询机服务质量信息列表
     *
     * @param branchNo    机构编号
     * @param queryType    查询类别
     * @param orderColumn 排序字段
     * @return
     */
    List<QualityListInfo> querySimpleQualityListInfo(@Param("branchNo") String branchNo,
                                                     @Param("orderColumn") String orderColumn,
                                                     @Param("queryType") Integer queryType);

    /**
     * 前二十列表查询
     *
     * @param branchNo  机构编号
     * @param queryType 查询类型
     * @param rowNum    查询条数
     * @param order     排序类型 desc 或者 asc
     * @return
     */
    List<QualityListInfo> queryQualityListTopInfo(@Param("branchNo") String branchNo,
                                                  @Param("queryType") String queryType,
                                                  @Param("order") String order,
                                                  @Param("rowNum") Integer rowNum);

    /**
     * 网点服务质量汇总
     *
     * @param branchNo 机构编号
     * @return QualityBranchSum 服务质量汇总实体
     */
    QualityBranchSum getBranchQualitySum(String branchNo);


    /**
     * 网点服务质量柜员
     *
     * @param branchNo  机构编号
     * @param queryType 查询类型
     * @return QualityBranchSum 服务质量柜员信息
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
    QualitySumBranchInfo getSumQualityInfo(@Param("branchNo") String branchNo, @Param("isManager") boolean isManager);

    /**
     * 查询首页 按网点分布维度的面板信息（优良中差及占比）
     * @param branchNo 机构Id
     * @param isManager 是否为管理机构（包括营业兼管理）
     * @return 按网点维度评价占比信息实体
     */
    QualitySumLevelInfo getQualityLevelInfo(@Param("branchNo") String branchNo, @Param("isManager") boolean isManager);

}
