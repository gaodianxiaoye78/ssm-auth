package com.mrj.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrj.common.result.Result;
import com.mrj.model.system.SysRole;
import com.mrj.model.vo.AssginRoleVo;
import com.mrj.model.vo.SysRoleQueryVo;
import com.mrj.system.anno.Log;
import com.mrj.system.enums.BusinessType;
import com.mrj.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;


    @ApiOperation("用户分配角色")
    @PostMapping("doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo) {
        sysRoleService.doAssign(assginRoleVo);
        return Result.ok();
    }


    @ApiOperation("获取用户的角色数据")
    @GetMapping("toAssign/{userId}")
    public Result toAssign(@PathVariable String userId) {
        Map<String,Object> roleMap = sysRoleService.getRolesByUserId(userId);
        return Result.ok(roleMap);
    }


    @ApiOperation("批量删除角色信息")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids) {
        sysRoleService.removeByIds(ids);
        return Result.ok();
    }


    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("逻辑删除角色信息")
    @DeleteMapping("deleteRole/{id}")
    public Result deleteRole(@PathVariable("id") Long id) {
        return sysRoleService.removeById(id) ? Result.ok() : Result.fail();
    }


    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    @ApiOperation("修改角色信息")
    @PutMapping("modifyRole")
    public Result modifyRole(@RequestBody SysRole sysRole) {
        return sysRoleService.updateById(sysRole) ? Result.ok() : Result.fail();
    }


    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    @ApiOperation("新增角色信息")
    @PostMapping("saveRole")
    public Result saveRole(@RequestBody SysRole sysRole) {

        return sysRoleService.save(sysRole) ? Result.ok() : Result.fail();
    }


    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("条件分页查询角色信息")
    @GetMapping("{page}/{limit}")
    public Result findPageRolesByCondition(@PathVariable("page") int page,
                                           @PathVariable("limit") int limit,
                                           SysRoleQueryVo sysRoleQueryVo) {
        Page<SysRole> pageParam = new Page<>(page, limit);
        IPage<SysRole> pageModel = sysRoleService.findRolesByCondition(pageParam, sysRoleQueryVo);
        return Result.ok(pageModel);
    }


    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("根据id查询")
    @PostMapping("findRoleById/{id}")
    public Result findRoleById(@PathVariable Long id) {
        SysRole sysRole = sysRoleService.getById(id);
        return Result.ok(sysRole);
    }


    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("查询所有角色信息")
    @GetMapping("findAll")
    public Result findAllRoles() {
        List<SysRole> roles = sysRoleService.list();
        return Result.ok(roles);
    }


}
