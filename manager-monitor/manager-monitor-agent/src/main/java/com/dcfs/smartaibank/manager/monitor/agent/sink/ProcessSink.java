package com.dcfs.smartaibank.manager.monitor.agent.sink;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import com.dcfs.smartaibank.handler.exception.HandlerException;
import com.dcfs.smartaibank.handler.Handler;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * sink具体实现类
 *
 * @author jiazw
 */
@Slf4j
public class ProcessSink extends AbstractProcessSink {
    private static final AtomicReference<Handler> HANDLER = new AtomicReference<>();

    @Override
    protected void process(Map<String, String> header, Map<String, Object> body) {
        MonitorContext context = new MonitorContext(header, body);
        //设置为实时消息
        context.put("MODE", "REAL");
        try {
            Handler handler = HANDLER.get();
            if (handler == null) {
                log.warn("分析引擎为空！");
            }
            handler.handle(context);
        } catch (HandlerException e) {
            log.error("分析监控数据失败。", e);
        }
    }

    /**
     * @param handler 要设置的 handler
     */
    public static void setHandler(Handler handler) {
        HANDLER.set(handler);
    }
}
