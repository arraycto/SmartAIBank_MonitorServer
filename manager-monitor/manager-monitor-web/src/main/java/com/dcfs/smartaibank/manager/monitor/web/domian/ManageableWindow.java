package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 业务可办理窗口信息
 *
 * @author wangjzm
 * @data 2019/07/16 10:01
 * @since 1.0.0
 */
@ApiModel(value = "ManageableWindow", description = "业务可办理窗口信息")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ManageableWindow {
    @ApiModelProperty(value = "窗口id")
    private String windowId;

    @ApiModelProperty(value = "窗口名称")
    private String windowName;

    @ApiModelProperty(value = "业务类型(个人业务-A、公司业务-B)")
    private String superQueueNo;

    @ApiModelProperty(value = "客户类型(VIP客户-01，普通客户-02)")
    private String customerType;
}
