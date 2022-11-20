package com.mrj.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrj.model.system.SysLoginLog;
import com.mrj.model.vo.SysLoginLogQueryVo;
import com.mrj.system.mapper.SysLoginLogMapper;
import com.mrj.system.service.SysLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {

    @Autowired
    private SysLoginLogMapper sysLoginLogMapper;

    @Override
    public void recordLogin(String username, Integer status, String ipAddr, String message) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setUsername(username);
        sysLoginLog.setStatus(status);
        sysLoginLog.setIpaddr(ipAddr);
        sysLoginLog.setMsg(message);
        sysLoginLogMapper.insert(sysLoginLog);
    }

    @Override
    public IPage<SysLoginLog> selectPage(Page<SysLoginLog> pageParam, SysLoginLogQueryVo sysLoginLogQueryVo) {
        QueryWrapper<SysLoginLog> wrapper = new QueryWrapper<>();
        String username = sysLoginLogQueryVo.getUsername();
        String createTimeBegin = sysLoginLogQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysLoginLogQueryVo.getCreateTimeEnd();
        if (!StringUtils.isEmpty(username)) {
            wrapper.like("username", username);
        }
        if (!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge("create_time", createTimeBegin);
        }
        if (!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.lt("create_time", createTimeEnd);
        }

        return sysLoginLogMapper.selectPage(pageParam, wrapper);
    }
}
