package com.mrj.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mrj.model.system.SysMenu;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author mrj
 * @since 2022-11-12
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> findNodes();

    void removeMenuById(String id);
}
