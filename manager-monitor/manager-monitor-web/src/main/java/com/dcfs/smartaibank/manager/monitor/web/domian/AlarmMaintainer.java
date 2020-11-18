package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wangtingo
 * @date 2019/6/27 9:03
 * @since
 */
@ApiModel(value = "AlarmMaintainer", description = "维修人员信息")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AlarmMaintainer {


    @ApiModelProperty(value = "维修人员姓名", required = true)
    private String repairUserName;

    @ApiModelProperty(value = "维修人员电话", required = true)
    private String moblieNo;

}
