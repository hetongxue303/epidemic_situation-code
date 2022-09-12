package com.hetongxue.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 权限实体类
 * @Class: Permission
 * @Author: hetongxue
 * @DateTime: 2022/9/12 1:01:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_permission")
public class Permission implements Serializable {

    /**
     * 权限ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 菜单标题
     */
    private String title;
    /**
     * 父ID[默认0]
     */
    private Long parentId;
    /**
     * 类型(0:菜单[默认] 1:菜单项 2:按钮)
     */
    private Integer type;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 图标
     */
    private String icon;
    /**
     * 权限码
     */
    private String permissionCode;
    /**
     * 路由名称
     */
    private String name;
    /**
     * 路由地址
     */
    private String path;
    /**
     * 是否缓存
     */
    private Boolean cache;
    /**
     * 组件地址
     */
    private String components;
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