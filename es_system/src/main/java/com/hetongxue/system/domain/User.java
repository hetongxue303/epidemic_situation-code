package com.hetongxue.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 用户实体类
 * @Class: User
 * @Author: hetongxue
 * @DateTime: 2022/9/12 0:56:44
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User implements Serializable {

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 用户电话
     */
    private String phone;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户性别(0:男(默认) 1:女 2:保密)
     */
    private String gender;
    /**
     * 用户简介
     */
    private String Introduction;
    /**
     * 用户头像地址
     */
    private String avatarPath;
    /**
     * 账户状态(0:不可用 1:可用(默认))
     */
    private boolean status;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}