package com.dcfs.smartaibank.manager.base.controller;

import com.dcfs.smartaibank.manager.base.domain.Org;
import com.dcfs.smartaibank.manager.base.domain.SpareDataDomain;
import com.dcfs.smartaibank.manager.base.service.OrgService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机构信息管理控制器
 *
 * @author wangting
 * @author jiazw
 * @since 1.0.0
 */
@Api(value = "api/v1/orgs", description = "机构管理")
@RestController
@RequestMapping(value = "api/v1/orgs")
@Validated
@Slf4j
public class OrgController extends BaseControllerImpl<OrgService, String, Org> {
	@ApiOperation(value = "按条件分页查询", notes = "按条件分页查询")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageSize", value = "每页记录数", dataType = "int", paramType = "query"),
		@ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query"),
		@ApiImplicitParam(name = "org", value = "机构信息", dataType = "Org", paramType = "body")
	})
	@PostMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
	public PageInfo<Org> selectByPage(@RequestBody Org org,
									  @Min(value = 0, message = "{page.pageNum.min}")
									  @RequestParam(defaultValue = "1")
										  Integer pageNum,
									  @MaxWithConfig(value = "${page.maxPageSize}", message = "{page.pageSize.max}")
									  @RequestParam(defaultValue = "${page.defaultPageSize}")
										  Integer pageSize) {
		return service.selectByPage(org, pageNum, pageSize);
	}

	@ApiOperation(value = "查询机构级别", notes = "根据机构类型查询")
	@ApiImplicitParams({@ApiImplicitParam(name = "type", value = "机构类型", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "userOrgId", value = "登录用户所属的机构号", dataType = "String", paramType = "query")
	})
	@GetMapping(value = "/level/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SpareDataDomain> selectQuerySuperior(@NotNull @PathVariable String type,
													 @NotNull @PathVariable String userOrgId) {
		Map<String, Object> queryMap = new HashMap<>(16);
		queryMap.put("type", type);
		queryMap.put("userOrgId", userOrgId);
		List<SpareDataDomain> list = service.selectLevel(queryMap);
		return list;
	}

	@ApiOperation(value = "查询上级机构", notes = "根据机构类型和级别查询")
	@ApiImplicitParams({@ApiImplicitParam(name = "level", value = "机构级别", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "type", value = "机构类型", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "userOrgId", value = "登录用户所属的机构号", dataType = "String", paramType = "query")
	})
	@GetMapping(value = "/superior/{type}/{level}/{userOrgId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<SpareDataDomain>> selectQuerySuperior(
		@NotNull @PathVariable String type,
		@NotNull @PathVariable String level,
		@NotNull @PathVariable String userOrgId) {
		Map<String, Object> queryMap = new HashMap<>(16);
		queryMap.put("type", type);
		queryMap.put("level", level);
		queryMap.put("userOrgId", userOrgId);
		Map<String, List<SpareDataDomain>> map = service.selectQuerySuperior(queryMap);
		return map;
	}
}
