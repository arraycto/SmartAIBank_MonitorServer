package com.dcfs.smartaibank.manager.base.controller;

import com.dcfs.smartaibank.manager.base.domain.Resource;
import com.dcfs.smartaibank.manager.base.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 资源控制器
 *
 * @author qiuch wangjzm
 * @since 1.0.0
 */
@Api(value = "api/v1/resources", description = "资源管理")
@RestController
@RequestMapping(value = "api/v1/resources")
@Validated
@Slf4j
public class ResourceController {

	@Autowired
	private ResourceService service;

	@ApiOperation(value = "根据用户id和系统id查询该用户可访问资源信息", notes = "根据用户id和系统id查询该用户可访问资源信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "用户id", dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "systemId", value = "系统ID", dataType = "string", paramType = "path", required = true)
	})
	@GetMapping(value = "/{userId}/{systemId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Resource> selectAccessResourceByUserIdAndSystemId(@NotNull @PathVariable("userId") String userId,
																  @NotNull @PathVariable("systemId") String systemId) {
		return service.selectAccessResourceByUserIdAndSystemId(userId, systemId);
	}

}
