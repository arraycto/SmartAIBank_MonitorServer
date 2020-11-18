package com.dcfs.smartaibank.manager.operations.web.domain;

import com.dcfs.smartaibank.manager.operations.web.enums.BatchExecutionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


/**
 * Step执行信息
 *
 * @author wangjzm
 * @since 1.0.0
 */
@ApiModel(value = "BatchStepExecution", description = "Step执行信息")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BatchStepExecution {
	@ApiModelProperty(value = "唯一标识此step执行的主键")
	private Integer executionId;

	@ApiModelProperty(value = "步骤id")
	private String stepId;

	@ApiModelProperty(value = "步骤描述")
	private String stepDesc;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@ApiModelProperty(value = "执行开始时间")
	private Date startTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@ApiModelProperty(value = "执行结束时间")
	private Date endTime;

	@ApiModelProperty(value = "batch执行状态")
	private BatchExecutionStatus batchExecutionStatus;

	@ApiModelProperty(value = "batch执行状态描述")
	private String batchExecutionStatusDesc;

	@ApiModelProperty(value = "job是否允许从断点处重新执行，true-允许，false-不允许")
	private Boolean allowRestartJobInBreakPoint = false;
}
