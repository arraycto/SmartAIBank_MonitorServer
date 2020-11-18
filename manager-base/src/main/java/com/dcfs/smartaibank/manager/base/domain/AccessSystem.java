package com.dcfs.smartaibank.manager.base.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 可选系统备选数据
 *
 * @author wangjzm
 */
@ApiModel(value = "AccessSystem", description = "可选系统备选数据组合实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AccessSystem {
	@ApiModelProperty(value = "可选系统备选数据")
	List<SpareDataDomain> systems;

}
