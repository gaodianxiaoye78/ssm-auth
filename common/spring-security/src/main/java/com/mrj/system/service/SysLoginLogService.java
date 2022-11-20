package com.mrj.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrj.model.system.SysLoginLog;
import com.mrj.model.vo.SysLoginLogQueryVo;

public interface SysLoginLogService {
    void recordLogin(String username, Integer status, String ipAddr, String message);

    IPage<SysLoginLog> selectPage(Page<SysLoginLog> pageParam, SysLoginLogQueryVo sysLoginLogQueryVo);
}
