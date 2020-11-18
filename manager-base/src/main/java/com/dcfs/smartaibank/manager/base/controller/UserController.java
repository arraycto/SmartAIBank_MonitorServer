/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: UserController
 * Author:   jiazw
 * Date:     2018/12/6 10:27
 * Description: 用户控制器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.dcfs.smartaibank.manager.base.controller;

import com.dcfs.smartaibank.manager.base.service.UserService;
import com.dcfs.smartaibank.manager.base.domain.User;
import com.dcfs.smartaibank.springboot.core.base.controller.BaseControllerImpl;
import com.dcfs.smartaibank.springboot.core.validation.annotation.MaxWithConfig;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.Min;


/**
 * 用户控制器
 *
 * @author jiazw
 * @since 1.0.0
 */
@Api(value = "api/v1/users", description = "用户管理")
@RestController
@RequestMapping(value = "api/v1/users")
@Validated
@Slf4j
public class UserController extends BaseControllerImpl<UserService, String, User> {
	@ApiOperation(value = "按条件分页查询", notes = "按条件分页查询")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageSize", value = "每页记录数", dataType = "int", paramType = "query"),
		@ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query"),
		@ApiImplicitParam(name = "user", value = "用户信息", dataType = "User", paramType = "body")
	})
	@PostMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
	public PageInfo<User> selectByPage(@RequestBody User user,
									   @Min(value = 0, message = "{page.pageNum.min}")
									   @RequestParam(defaultValue = "1")
										   Integer pageNum,
									   @MaxWithConfig(value = "${page.maxPageSize}", message = "{page.pageSize.max}")
									   @RequestParam(defaultValue = "${page.defaultPageSize}")
										   Integer pageSize) {
		return service.selectByPage(user, pageNum, pageSize);
	}
}
