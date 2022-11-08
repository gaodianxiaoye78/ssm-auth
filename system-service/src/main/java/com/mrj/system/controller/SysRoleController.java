package com.mrj.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrj.common.result.Result;
import com.mrj.model.system.SysRole;
import com.mrj.model.vo.SysRoleQueryVo;
import com.mrj.system.service.SysRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;


    @ApiOperation("批量删除角色信息")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids) {
        sysRoleService.removeByIds(ids);
        return Result.ok();
    }


    @ApiOperation("逻辑删除角色信息")
    @DeleteMapping("deleteRole/{id}")
    public Result deleteRole(@PathVariable("id") Long id) {
        return sysRoleService.removeById(id) ? Result.ok() : Result.fail();
    }


    @ApiOperation("修改角色信息")
    @PutMapping("modifyRole")
    public Result modifyRole(@RequestBody SysRole sysRole) {
        return sysRoleService.updateById(sysRole) ? Result.ok() : Result.fail();
    }


    @ApiOperation("新增角色信息")
    @PostMapping("saveRole")
    public Result saveRole(@RequestBody SysRole sysRole) {

        return sysRoleService.save(sysRole) ? Result.ok() : Result.fail();
    }


    @ApiOperation("条件分页查询角色信息")
    @GetMapping("{page}/{limit}")
    public Result findPageRolesByCondition(@PathVariable("page") int page,
                                           @PathVariable("limit") int limit,
                                           SysRoleQueryVo sysRoleQueryVo) {
        Page<SysRole> pageParam = new Page<>(page, limit);
        IPage<SysRole> pageModel = sysRoleService.findRolesByCondition(pageParam, sysRoleQueryVo);
        return Result.ok(pageModel);
    }


    @ApiOperation("根据id查询")
    @PostMapping("findRoleById/{id}")
    public Result findRoleById(@PathVariable Long id) {
        SysRole sysRole = sysRoleService.getById(id);
        return Result.ok(sysRole);
    }

    @ApiOperation("查询所有角色信息")
    @GetMapping("findAll")
    public Result findAllRoles() {
        List<SysRole> roles = sysRoleService.list();
        return Result.ok(roles);
    }


}
