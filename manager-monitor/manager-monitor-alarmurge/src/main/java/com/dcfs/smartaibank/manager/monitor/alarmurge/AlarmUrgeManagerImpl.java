package com.dcfs.smartaibank.manager.monitor.alarmurge;

import com.dcfs.smartaibank.core.factory.DefaultThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 预警催促管理
 *
 * @author jiazw
 */
@Component("alarmUrgeManager")
public class AlarmUrgeManagerImpl implements AlarmUrgeManager {
	private static final String THREAD_POOL_GROUP_NAME = "alarmUrge";
	private Map<String, AlarmUrge> ms = new ConcurrentHashMap<>();
	private DelayQueue<AlarmUrge> queue = new DelayQueue<>();
	private ExecutorService executorService;
	@Autowired
	private AutoAlarmUrgeBatchExecutor executor;

	public AlarmUrgeManagerImpl() {
		executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS,
			new LinkedBlockingQueue<>(1024), new DefaultThreadFactory(THREAD_POOL_GROUP_NAME));
		executorService.submit(new Worker());
	}

	@Override
	public DelayQueue<AlarmUrge> getQueue() {
		return queue;
	}

	@Override
	public void addAlarmUrge(AlarmUrge urge) {
		AlarmUrge s = ms.get(urge.getAlarmid());
		if (s == null) {
			ms.put(urge.getAlarmid(), urge);
			queue.add(urge);
		} else {
			queue.remove(s);
			queue.add(urge);
			ms.put(urge.getAlarmid(), urge);
		}
	}

	/**
	 * 工作线程
	 *
	 * @author jiazw
	 */
	private class Worker implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					//获取一个应用状态对象，该对象为超时时间最长的
					//超时定义为：当收到一个机具应用状态后，一定时间之内应该再次收到应用状态，如果没有收到则为超时
					AlarmUrge s = queue.take();
					if (s != null) {
						String alarmid = s.getAlarmid();
						String urgetype = s.getUrgetype();
						long delay = s.getDelay();
						executor.send(alarmid, delay, urgetype);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
