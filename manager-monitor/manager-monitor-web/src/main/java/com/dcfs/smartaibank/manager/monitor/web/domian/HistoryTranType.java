package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tanchena
 * @date 2019/8/5 10:37
 */
@ApiModel(value = "HistoryTranType", description = "交易质量统计的业务类型")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class HistoryTranType {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

}
