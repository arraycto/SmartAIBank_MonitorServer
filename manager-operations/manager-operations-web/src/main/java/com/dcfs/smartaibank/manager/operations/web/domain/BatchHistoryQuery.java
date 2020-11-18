package com.dcfs.smartaibank.manager.operations.web.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 批处理执行历史记录查询条件
 *
 * @author wangjzm
 * @since 1.0.0
 */
@ApiModel(value = "BatchHistoryQuery", description = "批处理执行历史记录查询条件")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BatchHistoryQuery implements Serializable {
	@ApiModelProperty(value = "开始时间，格式：yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startTime;

	@ApiModelProperty(value = "结束时间，格式：yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endTime;

	@ApiModelProperty(value = "batch执行状态")
	private String batchExecutionStatus;

	@ApiModelProperty(value = "数据日期")
	private String dataDate;
}
