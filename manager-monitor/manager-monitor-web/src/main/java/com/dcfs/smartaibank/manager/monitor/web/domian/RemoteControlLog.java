package com.dcfs.smartaibank.manager.monitor.web.domian;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 远程调用操作日志实体类
 *
 * @author liangchenglong
 * @date 2019/7/28 9:40
 * @since 1.0.0
 */

@Data
@NoArgsConstructor
@ToString
public class RemoteControlLog {

    private String seq;

    private String orgId;

    private String mac;

    private String userId;

    private String controlMsg;

    private String beginDate;

    private String beginTime;

    private String status;
}
