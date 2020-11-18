package com.dcfs.smartaibank.manager.monitor.web.controller;

import com.dcfs.smartaibank.manager.monitor.web.domian.HistoryTransInfo;
import com.dcfs.smartaibank.manager.monitor.web.param.HistoryTransParams;
import com.dcfs.smartaibank.manager.monitor.web.service.HistoryTransService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author liangchenglong
 * @author wangtingo
 * @date 2019/7/10 9:48
 * @since
 */
@Api(value = "/api/v1/history/transaction", description = "历史交易")
@RestController
@RequestMapping(value = "/api/v1/history/transaction")
public class HistoryTransController {

    @Autowired
    private HistoryTransService historyTransService;

    @ApiOperation(value = "按条件分页查询历史交易信息", notes = "按条件分页查询历史交易信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "historyTransParams", value = "查询参数",
                    dataType = "HistoryTransParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/page/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<HistoryTransInfo> getAlarmInfo(@NotNull @PathVariable(value = "branchId", required = true)
                                                           String branchId,
                                                   @Validated @RequestBody HistoryTransParams historyTransParams) {
        return historyTransService.getHistroyTransInfo(branchId, historyTransParams);
    }
}
