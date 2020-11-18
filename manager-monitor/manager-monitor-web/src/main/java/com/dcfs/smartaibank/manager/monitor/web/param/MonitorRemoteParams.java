package com.dcfs.smartaibank.manager.monitor.web.param;

import com.dcfs.smartaibank.manager.monitor.web.util.remote.Target;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 *  系统类型
 * @author liangchenglong
 * @data 2019/07/25 17:03
 * @since 1.0.0
 */

@Data
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class MonitorRemoteParams {

    /**
     * 操作指令：根据对应的码表进行相应操作
     * 比如：设备重启，等等
     */
    @ApiModelProperty(value = "操作类型码表", required = true)
    private String rc;

    /**
     * 是否批量按钮
     */
    @ApiModelProperty(value = "是否批量操作", required = true)
    private Boolean isBatch;

    /**
     * 机具mac，deviceClassKey ip 集合对象
     */
    @ApiModelProperty(value = "机具核心信息,ip,deviceClassKey,ip", required = true)
    private List<Target> target;

    /**
     * 机构编号
     */
    @ApiModelProperty(value = "机构编号", required = true)
    private String orgId;

    /**
     * 用户
     */
    @ApiModelProperty(value = "操作用户", required = true)
    private String user;

    /**
     * bus类型
     */
    @ApiModelProperty(value = "bus类型")
    private String busType;

    /**
     * 服务重启等待时间，例如：5分钟，30分钟，一小时
     * 只有设备重启用
     */
    @ApiModelProperty(value = "开机等待时间，比如1分钟，5分钟...")
    private String waitTime;

    /**
     * 刷新硬件状态以及模块复位用到
     */
    @ApiModelProperty(value = "外设模块id")
    private String modelClass;

    /**
     * 日志类型，日志抓取用
     */
    @ApiModelProperty(value = "日志类型")
    private String logType;

    /**
     * 日志生成日期 日志抓取用
     */
    @ApiModelProperty(value = "日志日期")
    private String logDate;

}
