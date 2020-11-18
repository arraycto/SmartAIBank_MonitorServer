package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wangtingo
 * @date 2019/7/11 13:38
 * @since
 */
@ApiModel(value = "TradeTimeLeaderInfo", description = "支行主管视角柜员交易代码处理时长（展示前100高频交易）")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TradeTimeLeaderInfo {

    @ApiModelProperty(value = "机构号")
    private String branchNo;

    @ApiModelProperty(value = "机构名称")
    private String branchName;

    @ApiModelProperty(value = "工号")
    private Float userNo;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "高频交易时长")
    private double handleTimeTotal;

    @ApiModelProperty(value = "高频交易全行均值")
    private double  handleTimeTAvg;

    @ApiModelProperty(value = "高频交易代码时长与全行均值比值")
    private double  handleTimeTotalAndTAvg;


}
