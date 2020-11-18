package com.dcfs.smartaibank.manager.monitor.web.service.impl;

import com.dcfs.smartaibank.core.exception.BusinessException;
import com.dcfs.smartaibank.manager.monitor.web.constance.Constance;
import com.dcfs.smartaibank.manager.monitor.web.dao.QualityDao;
import com.dcfs.smartaibank.manager.monitor.web.domian.NegativeCustomerInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualityBranchSum;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualityBranchUser;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualityListInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualityNetWorkInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualityRate;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualitySumBranchInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualitySumLevelInfo;
import com.dcfs.smartaibank.manager.monitor.web.service.MonitorQualityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author tanchen1
 * @date 2019/7/3 10:46
 * @since
 */
@Service
public class MonitorQualityImpl implements MonitorQualityService {

    @Autowired
    private QualityDao qualityDao;

    @Override
    public QualityRate queryQualityRate(String branchNo, boolean isManager) {
        return qualityDao.queryQualityRate(branchNo, isManager);
    }

    @Override
    public PageInfo<QualityListInfo> qualityList(String branchNo,
                                                 Integer pageNum,
                                                 Integer pageSize,
                                                 String orderColumn,
                                                 Integer queryType) {
        PageHelper.startPage(pageNum, pageSize);
        List<QualityListInfo> qualityListInfo =
                qualityDao.querySimpleQualityListInfo(branchNo, orderColumn, queryType);
        if (queryType.equals(Constance.QUERY_ALL)) {
            qualityListInfo.stream().forEach((obj) -> {
                if (obj.getCustomerRate() == null && obj.getEvaluateGoodRate() == null
                        && obj.getEvaluateRate() == null) {
                    obj.setQualityLevel(null);
                }
            });
        }
        if (queryType.equals(Constance.QUERY_UN)) {
            qualityListInfo.stream().forEach(obj -> obj.setQualityLevel(null));
        }
        return new PageInfo<>(qualityListInfo);
    }


    @Override
    public QualityNetWorkInfo getQualityListTopInfo(String branchNo, String queryType) {
        try {
            List<QualityListInfo> topTwenty =
                    qualityDao.queryQualityListTopInfo(branchNo, queryType, Constance.ASC, 21);
            QualityNetWorkInfo result = constructResult(branchNo, queryType, topTwenty);
            return result;
        } catch (Exception e) {
            throw new BusinessException("data.access", e);
        }
    }

    /**
     * 组装 监控服务质量网点排名前10跟后10
     *
     * @param branchNo  机构编号
     * @param queryType 查询类型
     * @param list      查询结果
     * @return 返回结果
     */
    public QualityNetWorkInfo constructResult(String branchNo, String queryType, List<QualityListInfo> list) {
        QualityNetWorkInfo result = new QualityNetWorkInfo();
        if (null != list) {
            int listSize = list.size();
            if (listSize < Constance.TOP_20) {
                //获取的条数不到20条，则查询一次，分别组装数据返回给页面
                if (listSize <= Constance.TOP_10) {
                    result.setTopTen(list);
                } else {
                    result.setTopTen(list.subList(0, 10));
                    List<QualityListInfo> lastList = list.subList(10, listSize);
                    Collections.reverse(lastList);
                    result.setBottomTen(lastList);
                }
            } else {
                //获取的条数大于20条，查询两次，组装数据返给页面
                result.setTopTen(list.subList(0, 10));
                List<QualityListInfo> bottomTwenty =
                        qualityDao.queryQualityListTopInfo(branchNo, queryType, Constance.DESC, 10);
                result.setBottomTen(bottomTwenty);
            }
        }
        return result;
    }

    @Override
    public QualityBranchSum getBranchQualitySum(String branchNo) {
        return qualityDao.getBranchQualitySum(branchNo);
    }

    @Override
    public List<QualityBranchUser> getBranchQualityUser(String branchNo, String queryType) {
        return qualityDao.getBranchQualityUser(branchNo, queryType);
    }

    @Override
    public List<NegativeCustomerInfo> getNegativeCustomerInfo(String userId) {
        return qualityDao.getNegativeCustomerInfo(userId);
    }

    @Override
    public QualitySumBranchInfo getSumQualityInfo(String branchNo, boolean isManager) {
        return qualityDao.getSumQualityInfo(branchNo, isManager);
    }

    @Override
    public QualitySumLevelInfo getQualityLevelInfo(String branchNo, boolean isManager) {
        return qualityDao.getQualityLevelInfo(branchNo, isManager);
    }

}
