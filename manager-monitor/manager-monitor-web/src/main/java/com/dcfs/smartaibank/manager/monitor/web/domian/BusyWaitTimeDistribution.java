package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 繁忙度排队等待时长分布比例（10分钟以下，10-20分钟，20-30分钟，30分钟以上）
 *
 * @author wangjzm
 * @data 2019/07/15 13:25
 * @since 1.0.0
 */
@ApiModel(value = "BusyWaitTimeDistribution",
        description = "繁忙度排队等待时长分布比例（10分钟以下，10-20分钟，20-30分钟，30分钟以上）")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BusyWaitTimeDistribution {
    @ApiModelProperty(value = "客户总数")
    private Integer totalCount;

    @ApiModelProperty(value = "10分钟以下客户数量")
    private Integer lessThanTenMinCount;

    @ApiModelProperty(value = "10-20分钟客户数量")
    private Integer betweenTenAndTwentyMinCount;

    @ApiModelProperty(value = "20-30分钟客户数量")
    private Integer betweenTwentyAndThirtyMinCount;

    @ApiModelProperty(value = "30分钟以上客户数量")
    private Integer moreThanThirtyMinCount;

    @ApiModelProperty(value = "10分钟以下占比")
    private float lessThanTenMinRate;

    @ApiModelProperty(value = "10-20分钟占比")
    private float betweenTenAndTwentyMinRate;

    @ApiModelProperty(value = "20-30分钟占比")
    private float betweenTwentyAndThirtyMinRate;

    @ApiModelProperty(value = "30分钟以上占比")
    private float moreThanThirtyMinRate;

}
