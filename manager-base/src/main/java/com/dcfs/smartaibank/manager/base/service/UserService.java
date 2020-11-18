/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: UserService
 * Author:   jiazw
 * Date:     2018/12/6 10:27
 * Description: 用户控制器
 * History:
 * 作者姓名           修改时间           版本号              描述
 */
package com.dcfs.smartaibank.manager.base.service;

import com.dcfs.smartaibank.manager.base.domain.User;
import com.dcfs.smartaibank.springboot.core.base.service.BaseService;
import com.dcfs.smartaibank.springboot.core.base.service.SelectPageByBeanService;

/**
 * 用户管理服务
 *
 * @author jiazw
 * @since 1.0.0
 */
public interface UserService extends BaseService<String, User>,
	SelectPageByBeanService<User> {
}
