package com.dcfs.smartaibank.manager.monitor.core.executor;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mvel2.MVEL;

/**
 * 执行器定义
 *
 * @author jiazw
 */
@NoArgsConstructor
@SuppressWarnings("serial")
public class ExecuteDefine implements Serializable {
    /**
     * 执行器ID
     */
    @Getter
    @Setter
    private String id;
    /**
     * 执行器名称
     */
    @Getter
    @Setter
    private String name;
    /**
     * 执行器描述
     */
    @Getter
    @Setter
    private String description;
    /**
     * 执行器类型
     */
    @Getter
    private ExecutorType type;
    /**
     * 执行器（MVEL表达式、类名称）
     */
    @Getter
    private String executor;
    /**
     * 编译后的执行器，主要针对MVEL表达式，能够加快运算速度
     */
    @Getter
    private Serializable compiledExecutor;
    /**
     * 返回类型
     */
    @Getter
    @Setter
    private String returnType;
    /**
     * 执行器键名，执行结果保存上下文的名称
     */
    @Getter
    @Setter
    private String key;
    /**
     * 创建时间
     */
    @Getter
    @Setter
    private Date createTime;
    /**
     * 更新时间
     */
    @Getter
    @Setter
    private Date updateTime;

    /**
     * 设置执行器类型
     *
     * @param type 要设置的 type
     */
    public void setType(ExecutorType type) {
        this.type = type;
        initCompiledExecutor();
    }

    /**
     * 设置执行器
     *
     * @param executor 要设置的 handler
     */
    public void setExecutor(String executor) {
        this.executor = executor;
        initCompiledExecutor();
    }

    private void initCompiledExecutor() {
        if (this.executor != null && this.type != null) {
            if (type == ExecutorType.MVEL) {
                this.compiledExecutor = MVEL.compileExpression(executor);
            } else {
                this.compiledExecutor = executor;
            }
        }
    }
}
