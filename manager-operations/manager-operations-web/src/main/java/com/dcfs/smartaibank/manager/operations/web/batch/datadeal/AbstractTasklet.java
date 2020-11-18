package com.dcfs.smartaibank.manager.operations.web.batch.datadeal;

import com.dcfs.smartaibank.manager.operations.web.utils.DateUtils;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

/**
 * @author wangjzm
 * @since 1.0.0
 */
public abstract class AbstractTasklet implements Tasklet {
	@Value("#{jobParameters['inputDate']}")
	protected Date date;

	public Date getDate() {
		return date;
	}

	public String getMonthStr() {
		return DateUtils.getLastMonth(this.date).substring(0, 6);
	}

	public String getQuarterStr() {
		return DateUtils.getLastQuarter(this.date).substring(0, 6);
	}

	public String getYearStr() {
		return DateUtils.getLastYear(this.date).substring(0, 4);
	}
}
