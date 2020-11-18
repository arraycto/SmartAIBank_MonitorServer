package com.dcfs.smartaibank.manager.monitor.web.domian;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * 设备列表页面设备详情实体
 *
 * @author wangjzm
 * @data 2019/06/19 15.01
 * @since 1.0.0
 */
@ApiModel(value = "DeviceDetail", description = "设备列表页面设备详情实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DeviceDetail {
    @ApiModelProperty(value = "设备编号")
    private String id;

    @ApiModelProperty(value = "设备mac地址")
    private String mac;

    @ApiModelProperty(value = "设备IP地址")
    private String ip;

    @ApiModelProperty(value = "设备总状态")
    private Integer totalStatus;

    @ApiModelProperty(value = "设备总状态描述")
    private String totalStatusDesc;

    @ApiModelProperty(value = "网络通讯状态")
    private Integer commStatus;

    @ApiModelProperty(value = "网络通讯状态描述")
    private String commStatusDesc;

    @ApiModelProperty(value = "网络通讯更新时间")
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private String commStatusTime;

    @ApiModelProperty(value = "应用运行状态")
    private Integer appStatus;

    @ApiModelProperty(value = "应用运行状态描述")
    private String appStatusDesc;

    @ApiModelProperty(value = "应用运行更新时间")
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private String appStatusTime;

    @ApiModelProperty(value = "外设运行状态")
    private Integer peripheralStatus;

    @ApiModelProperty(value = "外设运行状态描述")
    private String peripheralStatusDesc;

    @ApiModelProperty(value = "外设运行更新时间")
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private String peripheralStatusTime;

    @ApiModelProperty(value = "设备类型")
    private String devClassKey;

    @ApiModelProperty(value = "设备类型名称")
    private String deviceTypeName;

    @ApiModelProperty(value = "所属机构")
    private String branchNo;

    @ApiModelProperty(value = "所属机构名称")
    private String branchName;

    @ApiModelProperty(value = "设备厂商")
    private String manufacturerId;

    @ApiModelProperty(value = "设备厂商名称")
    private String manufacturerName;

    @ApiModelProperty(value = "时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date receiveTime;

    @ApiModelProperty(value = "设备位置id")
    private String fieldId;

    @ApiModelProperty(value = "设备位置名称")
    private String fieldName;

    @ApiModelProperty(value = "外设信息详情")
    private List<PeripheralStatus> devicePeripheral;

    @ApiModelProperty(value = "设备模块")
    private String devModelKey;

    @ApiModelProperty(value = "设备模块名称")
    private String devModelName;
}
