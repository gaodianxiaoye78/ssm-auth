package com.mrj.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrj.model.system.SysLoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {
}
