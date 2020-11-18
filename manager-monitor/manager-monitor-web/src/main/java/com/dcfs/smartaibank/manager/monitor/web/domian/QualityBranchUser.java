package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * @author wangtingo
 * @date 2019/7/16 9:06
 * @since
 */
@ApiModel(value = "QualityBranchUser", description = "营业机构服务质量柜员排名")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class QualityBranchUser {

    @ApiModelProperty(value = "柜员id")
    private String userId;

    @ApiModelProperty(value = "柜员性别，F-男，M-女")
    private String gender;

    @ApiModelProperty(value = "柜员名称")
    private String userName;

    @ApiModelProperty(value = "用户状态")
    private String userStatus;

    @ApiModelProperty(value = "评价率")
    private Float evaluateRate;

    @ApiModelProperty(value = "评价排名")
    private Integer evaluateBank;

    @ApiModelProperty(value = "好评率")
    private Float positiveRate;

    @ApiModelProperty(value = "好评排名")
    private Integer positiveBank;

    @ApiModelProperty(value = "中评率")
    private Float evaluateMidRate;

    @ApiModelProperty(value = "中评排名")
    private Integer evaluateMidBank;

    @ApiModelProperty(value = "差评率")
    private Float negativeRate;

    @ApiModelProperty(value = "差评排名")
    private Integer negativeBank;

    @ApiModelProperty(value = "差评数")
    private Integer negativeCount;

}
