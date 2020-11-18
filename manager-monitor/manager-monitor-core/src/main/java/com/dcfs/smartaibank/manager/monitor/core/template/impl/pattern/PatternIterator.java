package com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern;

/**
 * 匹配迭代器
 *
 * @author jiazw
 */
public interface PatternIterator {
    /**
     * 是否存在下一个
     *
     * @return true:存在, false:不存在
     */
    boolean hasNext();

    /**
     * 匹配的下一个值
     *
     * @return 下一个值
     */
    String next();

    /**
     * 开始位置
     *
     * @return 开始位置
     */
    int start();

    /**
     * 结束位置
     *
     * @return 结束位置
     */
    int end();
}
