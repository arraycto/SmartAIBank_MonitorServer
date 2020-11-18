 package com.dcfs.smartaibank.manager.base.controller;

import com.dcfs.smartaibank.manager.base.domain.AccessSystem;
import com.dcfs.smartaibank.manager.base.domain.Role;
import com.dcfs.smartaibank.manager.base.domain.RoleKey;
import com.dcfs.smartaibank.manager.base.service.RoleService;
import com.dcfs.smartaibank.springboot.core.validation.annotation.MaxWithConfig;
import com.dcfs.smartaibank.springboot.core.validation.group.InsertOrUpdate;
import com.github.pagehelper.PageInfo;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 角色控制器
 *
 * @author wangjzm
 * @since 1.0.0
 */
@Api(value = "api/v1/roles", description = "角色管理")
@RestController
@RequestMapping(value = "api/v1/roles")
@Validated
@Slf4j
public class RoleController {

	@Autowired
	RoleService roleService;

	@ApiOperation(value = "新增", notes = "新增角色信息")
	@ApiImplicitParam(name = "role", value = "角色信息", dataType = "Role", paramType = "body", required = true)
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public void add(@Validated({InsertOrUpdate.class}) @RequestBody Role role) {
		roleService.insert(role);
	}

	@ApiOperation(value = "根据角色id和系统id更新角色信息", notes = "根据角色id和系统id更新角色信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "systemId", value = "系统id", dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "roleId", value = "角色id", dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "role", value = "角色信息", dataType = "Role", paramType = "body", required = true)
	})
	@PutMapping(value = "/{roleId}/{systemId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void update(@NotNull @PathVariable("roleId") String roleId,
					   @NotNull @PathVariable("systemId") String systemId,
					   @Validated({InsertOrUpdate.class}) @RequestBody Role role) {
		roleService.updateById(role);
	}

	@ApiOperation(value = "根据角色id和系统id删除角色信息", notes = "根据角色id和系统id删除角色信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "systemId", value = "系统id", dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "roleId", value = "角色id", dataType = "string", paramType = "path", required = true)
	})
	@DeleteMapping(value = "/{roleId}/{systemId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteById(@NotNull @PathVariable("roleId") String roleId,
						   @NotNull @PathVariable("systemId") String systemId) {
		roleService.deleteById(new RoleKey(roleId, systemId));
	}

	@ApiOperation(value = "按条件分页查询角色信息", notes = "按条件分页查询")
	@ApiImplicitParams({
		@ApiImplicitParam(
			name = "pageSize", value = "每页记录数", dataType = "int", paramType = "query", required = true
		),
		@ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query", required = true),
		@ApiImplicitParam(name = "role", value = "角色信息", dataType = "Role", paramType = "body")
	})
	@PostMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
	public PageInfo<Role> selectByPage(@RequestBody Role role,
									   @Min(value = 0, message = "{page.pageNum.min}")
									   @RequestParam(defaultValue = "1")
										   Integer pageNum,
									   @MaxWithConfig(value = "${page.maxPageSize}", message = "{page.pageSize.max}")
									   @RequestParam(defaultValue = "${page.defaultPageSize}")
										   Integer pageSize) {
		return roleService.selectByPage(role, pageNum, pageSize);
	}

	@ApiOperation(value = "查询单笔", notes = "根据系统ID和系统ID查询单笔记录")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "systemId", value = "系统ID", dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "roleId", value = "角色ID", dataType = "string", paramType = "path", required = true)
	})
	@GetMapping(value = "/{systemId}/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Role get(@NotNull @PathVariable("roleId") String roleId,
					@NotNull @PathVariable("systemId") String systemId) {
		return roleService.selectById(new RoleKey(roleId, systemId));
	}

	@ApiOperation(value = "根据用户id查询该用户可管理的所有系统", notes = "根据用户id查询该用户可管理的所有系统")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "用户id", dataType = "string", paramType = "path", required = true)
	})
	@GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public AccessSystem selectAccessSystemByUserId(@NotNull @PathVariable("userId") String userId) {
		return roleService.selectAccessSystemByUserId(userId);
	}

}


