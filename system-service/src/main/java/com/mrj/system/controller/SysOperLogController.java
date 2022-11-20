package com.mrj.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mrj.common.result.Result;
import com.mrj.model.system.SysOperLog;
import com.mrj.model.vo.SysOperLogQueryVo;
import com.mrj.system.service.SysOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "操作日志管理", tags = "操作日志管理")
@RestController
@RequestMapping(value = "/admin/system/log/operation")
public class SysOperLogController {

    @Autowired
    private SysOperLogService sysOperLogService;

    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(
            @ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
            @ApiParam(name = "sysOperLogVo", value = "查询对象") SysOperLogQueryVo sysOperLogQueryVo) {
        IPage<SysOperLog> pageModel = sysOperLogService.selectPage(page, limit, sysOperLogQueryVo);
        return Result.ok(pageModel);
    }
}
