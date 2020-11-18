package com.dcfs.smartaibank.manager.monitor.web.util.remote;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *  客户端请求封装体
 * @author liangchenglong
 * @data 2019/07/25 17:03
 * @since 1.0.0
 */


@Data
@ToString
@NoArgsConstructor
public class MonitorRemoteClientRequest {

    private ClientHeader header;

    private ClientBody body;

    public MonitorRemoteClientRequest(ClientHeader header, ClientBody body) {
        this.header = header;
        this.body = body;
    }
}
