package com.mrj.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrj.model.system.SysRole;
import com.mrj.model.vo.SysRoleQueryVo;
import com.mrj.system.mapper.SysRoleMapper;
import com.mrj.system.service.SysRoleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {


    // 条件分页查询角色信息
    @Override
    public IPage<SysRole> findRolesByCondition(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo) {
        IPage<SysRole> pageModel = baseMapper.selectRoleList(pageParam, sysRoleQueryVo);
        return pageModel;
    }
}
