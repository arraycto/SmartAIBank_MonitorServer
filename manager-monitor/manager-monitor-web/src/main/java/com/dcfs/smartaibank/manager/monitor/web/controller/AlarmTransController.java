package com.dcfs.smartaibank.manager.monitor.web.controller;


import com.dcfs.smartaibank.manager.monitor.web.domian.AlarmTranInfo;
import com.dcfs.smartaibank.manager.monitor.web.param.AlarmTransParams;
import com.dcfs.smartaibank.manager.monitor.web.service.AlarmTransService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author liangchenglong
 * @date 2019/7/9 9:48
 * @since 1.0.0
 */

@Api(value = "/api/v1/alarm/transaction", description = "预警交易处理")
@RestController
@RequestMapping(value = "/api/v1/alarm/transaction")
public class AlarmTransController {

    @Autowired
    private AlarmTransService alarmTransService;

    @ApiOperation(value = "按条件分页查询预警交易信息", notes = "按条件分页查询预警交易信息")
    @ApiImplicitParam(name = "alarmTransParams", value = "查询参数", dataType = "AlarmTransParams",
            paramType = "body", required = false)
    @PostMapping(value = "/page/{branchNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<AlarmTranInfo> getAlarmTranInfo(@Validated @RequestBody AlarmTransParams alarmTransParams,
                                                    @NotNull @PathVariable(value = "branchNo") String branchNo) {
        alarmTransParams.setBranchNo(branchNo);
        return alarmTransService.getAlarmTranInfo(alarmTransParams);
    }


    @ApiOperation(value = "按条件分页查询预警交易信息", notes = "按条件分页查询预警交易信息")
    @ApiImplicitParam(name = "alarmTransParams", value = "查询参数", dataType = "AlarmTransParams",
            paramType = "body", required = false)
    @PostMapping(value = "/page/{branchNo}/{index}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<AlarmTranInfo> getAlarmTranInfobyLevel(@Validated @RequestBody AlarmTransParams alarmTransParams,
                                                    @NotNull @PathVariable(value = "branchNo") String branchNo,
                                                     @NotNull @PathVariable(value = "index") String index
    ) {
        alarmTransParams.setBranchNo(branchNo);
        return alarmTransService.getAlarmTranInfobyLevel(index, alarmTransParams);
    }



    @ApiOperation(value = "解除预警交易", notes = "解除预警交易")
    @ApiImplicitParam(name = "transId", value = "查询参数", dataType = "string",
            paramType = "path", required = true)
    @PutMapping("/relieve/{transId}")
    public void removeTrans(@NotNull @PathVariable(value = "transId", required = true) String transId) {
       alarmTransService.removeTrans(transId);
    }

}
