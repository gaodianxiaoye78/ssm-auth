package com.mrj.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrj.common.result.ResultCodeEnum;
import com.mrj.model.system.SysMenu;
import com.mrj.system.exception.OperationException;
import com.mrj.system.mapper.SysMenuMapper;
import com.mrj.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


import javax.management.OperationsException;
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

    @Override
    public List<SysMenu> findNodes() {
        List<SysMenu> sysMenuList = baseMapper.selectList(null);
        List<SysMenu> result = new ArrayList<>();
        sysMenuList.forEach(sysMenu -> {
            if (sysMenu.getParentId() == 0) {
                findChildren(sysMenu, sysMenuList);
                result.add(sysMenu);
            }
        });
        return result;
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
