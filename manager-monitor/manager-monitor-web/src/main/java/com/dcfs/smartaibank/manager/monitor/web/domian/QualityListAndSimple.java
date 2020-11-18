package com.dcfs.smartaibank.manager.monitor.web.domian;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * @author tanchen1
 * @date 2019/7/4 16:16
 * @since
 */
@ApiModel(value = "QualityListInfo", description = "分行-列表展示服务质量信息和数量值")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class QualityListAndSimple {

    @ApiModelProperty("列表展示信息")
    private PageInfo<QualityListInfo> qualityListInfo;

    @ApiModelProperty("面板数量信息")
    private QualityRate qualityRate;


}
