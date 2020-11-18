package com.dcfs.smartaibank.manager.operations.web.domain;

import com.dcfs.smartaibank.manager.operations.web.enums.BatchExecutionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * Job执行信息
 * <p>
 * 每次Job运行obExecution，都会生成一个新的Job执行实例
 * </p>
 *
 * @author wangjzm
 * @since 1.0.0
 */
@ApiModel(value = "BatchJobExecution", description = "Job执行信息")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BatchJobExecution {
	@ApiModelProperty(value = "唯一标识此job执行的主键")
	private Integer executionId;

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

	@ApiModelProperty(value = "数据日期")
	@JsonFormat(pattern = "yyyyMMdd", timezone = "GMT+8")
	private Date dataDate;

	@ApiModelProperty(value = "数据临时日期")
	@JsonIgnore
	@JsonFormat(pattern = "yyyyMMdd", timezone = "GMT+8")
	private Date tempDate;
}
