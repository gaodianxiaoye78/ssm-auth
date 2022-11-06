package com.mrj.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrj.model.system.SysRole;
import com.mrj.model.vo.SysRoleQueryVo;


public interface SysRoleService extends IService<SysRole> {
    // 条件分页查询角色信息
    IPage<SysRole> findRolesByCondition(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo);
}
