/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Role
 * Author:   jiazw
 * Date:     2019/3/21 14:00
 * Description: 角色Bean
 * History:
 * 作者姓名           修改时间           版本号              描述
 */
package com.dcfs.smartaibank.manager.base.domain;

import lombok.Data;

/**
 * 角色Bean
 *
 * @author jiazw
 * @since 1.0.0
 */
@Data
public class RoleInfo {
    /**
     * 角色ID
     */
    private String id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String desc;
}
