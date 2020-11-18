package com.dcfs.smartaibank.manager.monitor.alarmurge;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 预警催促
 *
 * @author jiazw
 */
public class AlarmUrge implements Delayed {
	/**
	 * 预警编号
	 */
	private String alarmid;

	/**
	 * 通知类型
	 * 1-未处理超时分行通知，2-未处理超时总行通知，3-已处理超时分行通知，4-已处理超时总行通知
	 */
	private String urgetype;


	/**
	 * 延迟时间
	 */
	private long delay;

	/**
	 * 过期时间，单位毫微秒
	 */
	private long expirationTime;

	public AlarmUrge(String alarmid, long delay, String urgetype) {
		this.alarmid = alarmid;
		this.delay = delay;
		this.urgetype = urgetype;
		this.expirationTime = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delay, TimeUnit.SECONDS);
	}

	/**
	 * 比较
	 *
	 * @return
	 */
	@Override
	public int compareTo(Delayed o) {
		if (o == null || !(o instanceof AlarmUrge)) {
			return 1;
		}
		if (o == this) {
			return 0;
		}
		AlarmUrge s = (AlarmUrge) o;
		if (this.delay > s.delay) {
			return 1;
		} else if (this.delay == s.delay) {
			return 0;
		} else {
			return -1;
		}
	}

	/**
	 * 获取剩余催促时间
	 *
	 * @param unit 时间单位
	 * @return
	 */
	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(expirationTime - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

	/**
	 * 获取催促时间
	 *
	 * @return 催促时间
	 */
	public long getDelay() {
		return delay;
	}

	/**
	 * 获取预警ID
	 *
	 * @return 预警ID
	 */
	public String getAlarmid() {
		return alarmid;
	}

	/**
	 * 获取催促类型
	 *
	 * @return 催促类型
	 */
	public String getUrgetype() {
		return urgetype;
	}
}
