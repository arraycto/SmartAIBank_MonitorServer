/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: User
 * Author:   jiazw
 * Date:     2018/12/6 09:59
 * Description: 用户实体
 * History:
 * 作者姓名           修改时间           版本号              描述
 */
package com.dcfs.smartaibank.manager.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户实体
 *
 * @author jiazw
 * @since 1.0.0
 */
@Data
public class UserInfo implements Serializable {
    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 机构ID
     */
    private String orgId;

    /**
     * 机构类型
     */
    private String orgTypeId;

    /**
     * 机构级别
     */
    private String orgLevel;
    /**
     * 机构名称
     */
    private String orgName;
    /**
     * 角色列表
     */
    private List<Role> roles;

    /**
     * 用户密码
     */
    @JsonIgnore
    private String password;

    /**
     * 用户状态
     */
    @JsonIgnore
    private String status;

    /**
     * 启用状态
     */
    @JsonIgnore
    private UseFlag useFlag;

    /**
     * 生效日期
     */
    @JsonIgnore
    private Date effectDate;

    /**
     * 密码状态,0-正常，1-失效，2-锁定
     */
    @JsonIgnore
    private PwdType passwordStatus;

    /**
     * 密码锁定时间
     */
    @JsonIgnore
    private Date passwordLockDate;

    /**
     * 密码错误次数
     */
    @JsonIgnore
    private Integer passwordErrorNum;

    /**
     * 密码更新时间
     */
    @JsonIgnore
    private Date passwordUpdateDate;
}
