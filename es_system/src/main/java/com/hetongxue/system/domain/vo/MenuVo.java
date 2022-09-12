package com.hetongxue.system.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 菜单VO
 * @Class: MenuVo
 * @Author: hetongxue
 * @DateTime: 2022/9/12 21:32:26
 */
@Data
@Accessors(chain = true)
public class MenuVo implements Serializable {

    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单地址
     */
    private String path;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 子菜单
     */
    private List<MenuVo> children;

}