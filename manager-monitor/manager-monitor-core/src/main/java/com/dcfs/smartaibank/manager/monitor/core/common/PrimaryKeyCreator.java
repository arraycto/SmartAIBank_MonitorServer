package com.dcfs.smartaibank.manager.monitor.core.common;

/**
 * 主键生成器
 *
 * @author jiazw
 */
public interface PrimaryKeyCreator {
    /**
     * 主键生成器
     *
     * @param type 主键类型
     * @return 主键值
     */
    String create(PrimaryKeyType type);
}
