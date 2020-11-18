package com.dcfs.smartaibank.manager.monitor.web.util.remote;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *  客户端返回接收体
 *
 * @author liangchenglong
 * @date 2019/7/29 10:46
 * @since 1.0.0
 */

@NoArgsConstructor
@ToString
@Data
public class ClientRet {

    private String code;

    private String message;

    private String date;
}
