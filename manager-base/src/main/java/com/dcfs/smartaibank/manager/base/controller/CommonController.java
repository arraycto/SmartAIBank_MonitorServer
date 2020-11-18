package com.dcfs.smartaibank.manager.base.controller;

import com.dcfs.smartaibank.manager.base.domain.CombinedSpareDomain;
import com.dcfs.smartaibank.manager.base.service.SpareDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公共备选数据
 *
 * @author wangtingo
 */
@Api(value = "/api/v1/", description = "公共备选数据查询")
@RestController
@RequestMapping("/api/v1/")
public class CommonController {

    @Autowired
    private SpareDataService spareDataService;

    @ApiOperation(value = "查询公共备选数据", notes = "查询机构名称、部门名称、机构类型、机构级别、角色名称、用户名称、介质类型、"
            + "系统名称等备选数据")
    @GetMapping("/spare-datas")
    public CombinedSpareDomain getSpareData() {
        return spareDataService.getCombinedSpareData();
    }
}
