package com.dcfs.smartaibank.manager.operations.web.controller;

import com.dcfs.smartaibank.manager.operations.web.domain.OrgInfo;
import com.dcfs.smartaibank.manager.operations.web.service.OperationOrgTreeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前机构及下属机构查询
 *
 * @author
 */
@Api(value = "/api/v1/", description = "当前机构及下属机构查询")
@RestController
@RequestMapping("/api/v1/")
public class OperationOrgTreeController {
	@Autowired
	private OperationOrgTreeService service;

	@ApiOperation(value = "查询当前机构及下属机构", notes = "查询当前机构及下属机构")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgid", value = "机构编号", dataType = "string", paramType = "path", required = true),
	})
	@GetMapping(value = "/orgTree/{orgid}")
	public ResponseEntity<List<OrgInfo>> getOrgTree(@PathVariable String orgid) {
		List<OrgInfo> orgTree = service.findOrgTree(orgid);
		return ResponseEntity.ok(orgTree);
	}
}

