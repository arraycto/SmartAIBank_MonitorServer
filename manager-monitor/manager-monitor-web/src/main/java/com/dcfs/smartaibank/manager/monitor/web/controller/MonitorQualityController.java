package com.dcfs.smartaibank.manager.monitor.web.controller;

import com.dcfs.smartaibank.manager.monitor.web.domian.NegativeCustomerInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualityBranchSum;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualityBranchUser;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualityListInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualityNetWorkInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualityRate;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualitySumBranchInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.QualitySumLevelInfo;
import com.dcfs.smartaibank.manager.monitor.web.service.MonitorQualityService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author tanchen1
 * @date 2019/7/3 10:44
 * @since
 */
@Api(value = "/api/v1/monitor/quality", description = "监控-服务质量")
@RestController
@RequestMapping(value = "/api/v1/monitor/quality")
@Slf4j
public class MonitorQualityController {

    @Autowired
    private MonitorQualityService qualityService;


    @ApiOperation(value = "监控服务质量统计以及评价率", notes = "监控服务质量统计以及评价率")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchNo", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "isManager", value = "是否为管理机构，true-是 、false-否",
                    dataType = "string", paramType = "query", required = true)
    })
    @GetMapping("/statistics/{branchNo}")
    public QualityRate qualityRate(
            @PathVariable(value = "branchNo", required = true) String branchNo,
            @RequestParam(value = "isManager", required = true) boolean isManager
    ) {
        return qualityService.queryQualityRate(branchNo, isManager);
    }

    @ApiOperation(value = "首页监控质量信息查询", notes = "首页监控质量按整体维度统计信息查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchNo", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "isManager", value = "是否为管理机构，true-是 、false-否",
                    dataType = "string", paramType = "query", required = true)
    })
    @GetMapping("/sum/entirety/{branchNo}")
    public QualitySumBranchInfo getSumBranchQualityInfo(
            @PathVariable(value = "branchNo", required = true) String branchNo,
            @RequestParam(value = "isManager", required = true) boolean isManager) {
        return qualityService.getSumQualityInfo(branchNo, isManager);
    }

    @ApiOperation(value = "首页监控质量信息查询", notes = "首页监控质量按网点维度统计信息查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchNo", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "isManager", value = "是否为管理机构，true-是 、false-否",
                    dataType = "string", paramType = "query", required = true)
    })
    @GetMapping("/sum/branch/{branchNo}")
    public QualitySumLevelInfo getSumBranchLevelQualityInfo(
            @PathVariable(value = "branchNo", required = true) String branchNo,
            @RequestParam(value = "isManager", required = true) boolean isManager) {
        return qualityService.getQualityLevelInfo(branchNo, isManager);
    }

    @ApiOperation(value = "监控服务质量列表", notes = "监控服务质量列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchNo", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "orderColumn", value = "排序栏位",
                    dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "queryType", value = "查询类别(全部 5 - 优 4 - 良3 - 中2 - 差1)",
                    dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageNum", value = "当前页数",
                    dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数",
                    dataType = "int", paramType = "query", required = true)
    })
    @GetMapping(value = "/page/{branchNo}")
    public PageInfo<QualityListInfo> qualityList(
            @NotNull @PathVariable(value = "branchNo", required = true) String branchNo,
            @RequestParam(value = "pageNum", required = true) Integer pageNum,
            @RequestParam(value = "pageSize", required = true) Integer pageSize,
            @RequestParam(value = "queryType", required = true) Integer queryType,
            @RequestParam(value = "orderColumn", required = false) String orderColumn) {
        return qualityService.qualityList(branchNo, pageNum, pageSize, orderColumn, queryType);
    }

    @ApiOperation(value = "监控服务质量-网点排名", notes = "监控服务质量-网点排名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchNo", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "queryType", value = "查询类型(eva-评价率,good-好评率,bad-差评率,customer-客户身份,)",
                    dataType = "string", paramType = "query", required = true),
    })
    @GetMapping(value = "/network-rank/{branchNo}")
    public QualityNetWorkInfo getQualityRate(@PathVariable(value = "branchNo", required = true) String branchNo,
                                             @RequestParam(value = "queryType", required = true) String queryType) {
        return qualityService.getQualityListTopInfo(branchNo, queryType);
    }

    @ApiOperation(value = "查询营业机构服务质量汇总信息", notes = "查询营业机构服务质量汇总信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchNo", value = "机构编号",
                    dataType = "string", paramType = "path", required = true)
    })
    @GetMapping(value = "/branch/sum/{branchNo}")
    public QualityBranchSum getBranchQualitySum(@PathVariable(value = "branchNo", required = true) String branchNo) {
        return qualityService.getBranchQualitySum(branchNo);
    }

    @ApiOperation(value = "查询营业机构服务质量柜员信息", notes = "查询营业机构服务质量柜员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchNo", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "queryType", value = "查询类型(eva-评价率,good-好评率,bad-差评率)",
                    dataType = "string", paramType = "path", required = true)
    })
    @GetMapping(value = "/branch/sum/{branchNo}/{queryType}")
    public List<QualityBranchUser> getBranchQualityUser(
            @PathVariable(value = "branchNo", required = true) String branchNo,
            @PathVariable(value = "queryType", required = true) String queryType) {
        return qualityService.getBranchQualityUser(branchNo, queryType);
    }

    @ApiOperation(value = "查询营业机构服务质量柜员差评的客户信息", notes = "查询营业机构服务质量柜员差评的客户信息")
    @ApiImplicitParam(name = "userId", value = "柜员编号",
            dataType = "string", paramType = "path", required = true)
    @GetMapping(value = "/branch/negative/{userId}")
    public List<NegativeCustomerInfo> getNegativeCustomerIno(
            @PathVariable(value = "userId", required = true) String userId) {
        return qualityService.getNegativeCustomerInfo(userId);
    }
}
