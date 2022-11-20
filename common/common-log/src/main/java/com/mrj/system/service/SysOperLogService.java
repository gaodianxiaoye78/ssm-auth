package com.mrj.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mrj.model.system.SysOperLog;
import com.mrj.model.vo.SysOperLogQueryVo;

public interface SysOperLogService {

    void saveSysLog(SysOperLog sysOperLog);

    //操作日志分页查询
    IPage<SysOperLog> selectPage(Long page, Long limit, SysOperLogQueryVo sysOperLogQueryVo);
}