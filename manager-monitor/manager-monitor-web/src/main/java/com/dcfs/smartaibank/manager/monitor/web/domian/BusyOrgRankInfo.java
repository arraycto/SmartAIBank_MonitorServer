package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 繁忙度机构排名信息（TOP、BOTTOM信息汇总）
 *
 * @author wangjzm
 * @data 2019/07/08 16:26
 * @since 1.0.0
 */
@ApiModel(value = "BusyOrgRankInfo", description = "繁忙度机构排名信息（TOP、BOTTOM信息汇总）")
@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusyOrgRankInfo {
    @ApiModelProperty(value = "TOP机构信息")
    private List<BusyDetails> topList;

    @ApiModelProperty(value = "BOTTOM机构信息")
    private List<BusyDetails> bottomList;

}
