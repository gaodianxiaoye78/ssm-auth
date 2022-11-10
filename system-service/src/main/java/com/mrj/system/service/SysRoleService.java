package com.mrj.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrj.model.system.SysRole;
import com.mrj.model.vo.AssginRoleVo;
import com.mrj.model.vo.SysRoleQueryVo;

import java.util.Map;


public interface SysRoleService extends IService<SysRole> {
    // 条件分页查询角色信息
    IPage<SysRole> findRolesByCondition(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo);

    // 获取用户的角色数据
    Map<String, Object> getRolesByUserId(String userId);

    // 用户分配角色
    void doAssign(AssginRoleVo assginRoleVo);
}
