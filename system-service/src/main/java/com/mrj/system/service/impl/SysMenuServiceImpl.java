package com.mrj.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrj.common.result.ResultCodeEnum;
import com.mrj.model.system.SysMenu;
import com.mrj.model.system.SysRoleMenu;
import com.mrj.model.vo.AssginMenuVo;
import com.mrj.model.vo.RouterVo;
import com.mrj.system.exception.OperationException;
import com.mrj.system.mapper.SysMenuMapper;
import com.mrj.system.mapper.SysRoleMenuMapper;
import com.mrj.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrj.system.utils.RouterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author mrj
 * @since 2022-11-12
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> findNodes() {
        List<SysMenu> sysMenuList = baseMapper.selectList(null);
        return buildTree(sysMenuList);
    }

    @Override
    public void removeMenuById(String id) {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new OperationException(ResultCodeEnum.FAIL.getCode(), "子菜单不为空，无法删除");
        }
        baseMapper.deleteById(id);
    }

    @Override
    public List<SysMenu> findMenuByRoleId(String roleId) {
        //获取所有菜单 status=1
        QueryWrapper<SysMenu> wrapperMenu = new QueryWrapper<>();
        wrapperMenu.eq("status", 1);
        List<SysMenu> menuList = baseMapper.selectList(wrapperMenu);

        //根据角色id查询 角色分配过的菜单列表
        QueryWrapper<SysRoleMenu> wrapperRoleMenu = new QueryWrapper<>();
        wrapperRoleMenu.eq("role_id", roleId);
        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(wrapperRoleMenu);

        //从第二步查询列表中，获取角色分配所有菜单id
        List<String> roleMenuIds = new ArrayList<>();
        for (SysRoleMenu sysRoleMenu : roleMenus) {
            String menuId = sysRoleMenu.getMenuId();
            roleMenuIds.add(menuId);
        }

        // 数据处理：isSelect 如果菜单选中 true，否则false
        // 拿着分配菜单id 和 所有菜单比对，有相同的，让isSelect值true
        for (SysMenu sysMenu : menuList) {
            if (roleMenuIds.contains(sysMenu.getId())) {
                sysMenu.setSelect(true);
            } else {
                sysMenu.setSelect(false);
            }
        }
        return buildTree(menuList);
    }

    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        String roleId = assginMenuVo.getRoleId();
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        sysRoleMenuMapper.delete(wrapper);
        List<String> menuIdList = assginMenuVo.getMenuIdList();
        menuIdList.forEach(menuId -> {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenuMapper.insert(sysRoleMenu);
        });
    }

    @Override
    public List<RouterVo> getUserMenuList(String userId, String username) {
        List<SysMenu> menus;
        // 判断账号是否为超级管理员，如果是则获得所有菜单权限
        if ("admin".equals(username)) {
            QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
            wrapper.eq("status", 1);
            wrapper.orderByAsc("sort_value");
            menus = baseMapper.selectList(wrapper);
        } else {
            // 根据userId查询用户所拥有的菜单权限
            menus = baseMapper.findMenuListUserId(userId);
        }
        List<SysMenu> menuTree = buildTree(menus);
        return RouterHelper.buildRouters(menuTree);
    }

    @Override
    public List<String> getUserButtonList(String userId, String username) {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        List<SysMenu> menus = "admin".equals(username) ? baseMapper.selectList(wrapper) : baseMapper.findMenuListUserId(userId);
        List<String> result = new ArrayList<>();
        menus.forEach(menu -> {
            if (menu.getType() == 2) {
                result.add(menu.getPerms());
            }
        });
        return result;
    }

    private List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        List<SysMenu> result = new ArrayList<>();
        sysMenuList.forEach(sysMenu -> {
            if (sysMenu.getParentId() == 0) {
                findChildren(sysMenu, sysMenuList);
                result.add(sysMenu);
            }
        });
        return result;
    }

    private void findChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {
        sysMenu.setChildren(new ArrayList<>());
        String id = sysMenu.getId();
        sysMenuList.forEach(menu -> {
            if (menu.getParentId().toString().equals(id)) {
                findChildren(menu, sysMenuList);
                sysMenu.getChildren().add(menu);
            }
        });
    }
}
