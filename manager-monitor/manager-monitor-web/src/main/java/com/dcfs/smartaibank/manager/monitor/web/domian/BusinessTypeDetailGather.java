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
 * 分业务类型繁忙度详情汇总信息（个人业务，公司业务）
 *
 * @author wangjzm
 * @data 2019/07/16 10:06
 * @since 1.0.0
 */
@ApiModel(value = "BusinessTypeDetailGather", description = "分业务类型繁忙度详情汇总信息（个人业务，公司业务）")
@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessTypeDetailGather {
    @ApiModelProperty(value = "个人业务-业务类型详情实体")
    private List<BusinessTypeDetail> personalBusinessList;

    @ApiModelProperty(value = "公司业务-业务类型详情实体")
    private List<BusinessTypeDetail> companyBusinessList;
}

