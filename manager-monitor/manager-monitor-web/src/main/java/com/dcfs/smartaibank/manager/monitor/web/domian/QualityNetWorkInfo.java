package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author liangchenglong
 * @date 2019/7/4 16:16
 * @since 1.0.0
 */
@ApiModel(value = "QualityNetWorkInfo", description = "监控服务质量-网点排名结果")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class QualityNetWorkInfo {

    @ApiModelProperty(value = "网点排名前十")
    List<QualityListInfo> topTen;

    @ApiModelProperty(value = "网点排名后十")
    List<QualityListInfo> bottomTen;
}
