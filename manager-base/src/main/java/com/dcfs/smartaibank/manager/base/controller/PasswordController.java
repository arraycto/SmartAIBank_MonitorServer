package com.dcfs.smartaibank.manager.base.controller;

import com.dcfs.smartaibank.manager.base.domain.MediumInfo;
import com.dcfs.smartaibank.manager.base.service.PwdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * 密码管理    包括（密码重置和秘密维护）
 *
 * @author tanchen
 */
@Api(value = "api/v1/mediumInfo", description = "密码管理")
@RestController
@RequestMapping(value = "api/v1/mediumInfo")
@Validated
@Slf4j
public class PasswordController {

	@Autowired
	private PwdService pwdService;

	@ApiOperation(value = "查找密码", notes = "根据ID修改查找密码")
	@ApiImplicitParam(name = "id", value = "主键ID", dataType = "String", paramType = "path", required = true)
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MediumInfo getPwd(@PathVariable(value = "id", required = true) String id) {
		return pwdService.selectById(id);
	}

	@ApiOperation(value = "查找重置用户信息", notes = "根据ID查找权限内用户信息")
	@ApiImplicitParam(name = "id", value = "主键ID", dataType = "String", paramType = "path", required = true)
	@GetMapping(value = "/reset/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> getRestPwd(@PathVariable(value = "id", required = true) String id) {
		Map map = new HashMap<String, String>(16);
		map.put("userName", pwdService.getUserName(id));
		return map;
	}

	@ApiOperation(value = "更新密码", notes = "根据ID修改用户密码")
	@ApiImplicitParam(name = "id", value = "主键ID", dataType = "String", paramType = "path", required = true)
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void updatePwd(@PathVariable(value = "id", required = true) String id,
						  @RequestBody MediumInfo mediumInfo) {
		pwdService.updateById(mediumInfo);
	}

	@ApiOperation(value = "锁定密码", notes = "根据ID重置用户密码")
	@ApiImplicitParam(name = "id", value = "主键ID", dataType = "String", paramType = "path", required = true)
	@PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void lockPwd(@PathVariable(value = "id", required = true) String id) {
		pwdService.lockById(id);
	}

	@ApiOperation(value = "重置密码", notes = "根据ID重置用户密码")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "resetId", value = "重置用户的ID",
			dataType = "String", paramType = "path", required = true),
		@ApiImplicitParam(name = "id", value = "当前用户ID",
			dataType = "String", paramType = "path", required = true),
		@ApiImplicitParam(name = "orgId", value = "当前用户机构ID",
			dataType = "String", paramType = "path", required = true)
	})
	@DeleteMapping(value = "/{id}/{orgId}/{resetId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> resetPwd(@PathVariable(value = "id", required = true) String id,
										@PathVariable(value = "resetId", required = true) String resetId,
										@PathVariable(value = "orgId", required = true) String orgId) {
		return pwdService.resetById(id, resetId, orgId);
	}
}
