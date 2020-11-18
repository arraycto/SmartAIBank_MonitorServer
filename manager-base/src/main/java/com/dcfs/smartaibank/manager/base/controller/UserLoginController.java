package com.dcfs.smartaibank.manager.base.controller;

import com.dcfs.smartaibank.manager.base.domain.UserInfo;
import com.dcfs.smartaibank.manager.base.service.UserLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author tanchena
 * @date 2019/8/20 9:57
 */
@Api(value = "api/v1/users", description = "用户登录资源查询信息")
@RestController
@RequestMapping(value = "api/v1/users")
public class UserLoginController {

	@Autowired
	private UserLoginService userLoginService;

	@ApiOperation(value = "登录用户资源数据查询", notes = "用户登录之后的资源查询")
	@ApiImplicitParam(name = "userId", value = "用户ID", dataType = "string", paramType = "path", required = true)
	@PostMapping(value = "/login/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserInfo select(@NotNull @PathVariable(value = "userId", required = true) String userId) {
		return userLoginService.userLogin(userId);
	}
}
