package com.dcfs.smartaibank.manager.monitor.web.util.remote;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 客户端请求体
 *
 * @author liangchenglong
 * @date 2019/7/29 10:46
 * @since 1.0.0
 */

@NoArgsConstructor
@Data
@ToString
public class ClientBody {
    private ClientReq req;

    private ClientRes res;

    public ClientBody(ClientReq req, ClientRes res) {
        this.req = req;
        this.res = res;
    }
}
